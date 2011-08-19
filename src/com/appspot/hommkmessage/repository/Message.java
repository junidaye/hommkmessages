package com.appspot.hommkmessage.repository;

import java.nio.charset.Charset;
import java.text.DateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
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
import com.appspot.hommkmessage.shared.MessageType;
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
	private Text contentText;
	private String subject;
	private String dateText;
	private Text receiverText;
	private String userId;

	private Message() {
		// used by JDO
	}

	public Message(String htmlSource, String key, String subjectText,
			String dateText, String receiverText, String contentText,
			String userId) {
		this.subject = subjectText;
		this.dateText = dateText;
		this.userId = userId;
		this.receiverText = new Text(receiverText);
		this.contentText = new Text(contentText);
		this.password = key;
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
		MessageType messageType = MessageType
				.readTypeOfMessageHtml(getUncompressedHtmlSource());
		messageMetadata.setMessageType(messageType);
		messageMetadata.setSubjectText(subject);
		messageMetadata.setMessageDateText(dateText);
		messageMetadata.setReceiverText(receiverText.getValue());
		return messageMetadata;
	}

	public boolean matchesSearchText(String searchStringLowerCase) {
		List<String> interestingTexts = Arrays.asList(receiverText.getValue(),
				subject, dateText, contentText.getValue());
		for (String text : interestingTexts) {
			if (matches(text, searchStringLowerCase)) {
				return true;
			}
		}
		return false;
	}

	private boolean matches(String text, String searchStringLowerCase) {
		return text.toLowerCase().contains(searchStringLowerCase);
	}

}
