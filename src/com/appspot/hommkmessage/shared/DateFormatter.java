package com.appspot.hommkmessage.shared;

import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;

public class DateFormatter {
	private final DateTimeFormat format = DateTimeFormat
			.getFormat("yyyy-MM-dd HH:mm");

	public String formatDateTime(Date creationDate) {
		return format.format(creationDate);
	}

}
