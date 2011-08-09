package com.appspot.hommkmessage.shared;

import java.io.Serializable;
import java.util.Date;

public class MessageMetadata implements Serializable {

	private static final long serialVersionUID = 2806408406061969453L;

	private Date creationDate;
	private String id;

	private String messageHeaderText;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public String getInfoLine(DateFormatter dateFormatter) {
		String creationDatePart = dateFormatter.formatDateTime(creationDate);
		return creationDatePart + " -- " + messageHeaderText + " -- id: " + id;
	}

	public void setMessageHeaderText(String headerText) {
		this.messageHeaderText = headerText;
	}

}
