package com.appspot.hommkmessage.repository;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

public class Message {

	private final String htmlSource;
	private final String id;
	private final Date creationDate;

	public Message(String htmlSource) {
		this.htmlSource = htmlSource;
		id = UUID.randomUUID().toString();
		creationDate = new Date();
	}

	public String getHtmlSource() {
		return htmlSource;
	}

	public String getId() {
		return id;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public String getCreationDateFormatted(Locale locale) {
		final DateFormat dateFormat = DateFormat.getDateTimeInstance(
				DateFormat.FULL, DateFormat.FULL, locale);
		String creationDateFormatted = dateFormat.format(getCreationDate());
		return creationDateFormatted;
	}

}
