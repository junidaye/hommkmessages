package com.appspot.hommkmessage.repository;

import java.nio.charset.Charset;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import org.dellroad.lzma.client.LZMAByteArrayCompressor;
import org.dellroad.lzma.client.LZMAByteArrayDecompressor;

import com.appspot.hommkmessage.shared.MessageMetadata;
import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.datastore.Text;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Message {

	private static final Charset charset = Charset.forName("UTF-8");

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private String id;
	private Blob htmlSource;
	private Date creationDate;
	private String password;
	private Text headerText;
	private Text contentText;

	private Message() {
		// used by JDO
	}

	public Message(String htmlSource, String key, String subjectText,
			String dateText, String receiverText, String contentText) {
		this.headerText = new Text(subjectText + dateText + receiverText);
		this.contentText = new Text(contentText);
		this.setPassword(password);
		this.htmlSource = compress(htmlSource);
		id = UUID.randomUUID().toString();
		creationDate = new Date();
	}

	private Blob compress(String htmlSource) {
		byte[] uncompressedBytes = htmlSource.getBytes(charset);
		LZMAByteArrayCompressor compressor = new LZMAByteArrayCompressor(
				uncompressedBytes);
		while (compressor.execute()) {
		}
		byte[] compressedBytes = compressor.getCompressedData();
		return new Blob(compressedBytes);
	}

	public Blob getHtmlSource() {
		return htmlSource;
	}

	public String getUncompressedHtmlSource() {
		LZMAByteArrayDecompressor decompressor = new LZMAByteArrayDecompressor(
				htmlSource.getBytes());
		while (decompressor.execute()) {
		}
		byte[] uncompressedBytes = decompressor.getUncompressedData();
		return new String(uncompressedBytes, charset);
	}

	public String getId() {
		return id;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCreationDateFormatted(Locale locale) {
		final DateFormat dateFormat = DateFormat.getDateTimeInstance(
				DateFormat.FULL, DateFormat.FULL, locale);
		String creationDateFormatted = dateFormat.format(getCreationDate());
		return creationDateFormatted;
	}

	public MessageMetadata getMetadata() {
		MessageMetadata messageMetadata = new MessageMetadata();
		messageMetadata.setId(getId());
		messageMetadata.setCreationDate(getCreationDate());
		messageMetadata.setMessageHeaderText(getType() + headerText.getValue());
		return messageMetadata;
	}

	private String getType() {
		String html = getUncompressedHtmlSource();
		if (html.contains("BattleResultDetail")) {
			return "[Kampf]";
		}
		if (html.contains("ScoutingResultDetail")) {
			return "[Spionage]";
		}
		// TODO PROBLEM::: es werden alte nachrichten gecacht und IN NEUEN
		// gespeichert --> meherere nachrichten in einer !!!
		return "";
	}

	public boolean matchesSearchText(String searchStringLowerCase) {
		return match(headerText, searchStringLowerCase)
				|| match(contentText, searchStringLowerCase);
	}

	private boolean match(Text text, String searchStringLowerCase) {
		return text.getValue().toLowerCase().contains(searchStringLowerCase);
	}

}
