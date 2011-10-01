/*******************************************************************************
 * This file is part of hommkmessage.
 * 
 * hommkmessage is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * hommkmessage is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with hommkmessage.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package com.appspot.hommkmessage.shared;

import java.io.Serializable;
import java.util.Date;

import com.appspot.hommkmessage.client.LocalStorage;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.ui.HTML;

public class MessageMetadata implements Serializable {

	private static final long serialVersionUID = 2806408406061969453L;

	private Date creationDate;
	private String id;
	private MessageType messageType;
	private String subjectText;
	private String messageDateText;
	private String receiverText;
	private boolean allowedToBeDeleted;

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

	public MessageType getMessageType() {
		return messageType;
	}

	public void setMessageType(MessageType messageType) {
		this.messageType = messageType;
	}

	public HTML getInfoLine(DateFormatter dateFormatter,
			LocalStorage localStorage) {
		String creationDatePart = "<span class=\"messageListEntryUploadDate\">"
				+ dateFormatter.formatDateTime(creationDate) + "</span>";
		String iconHtml = getMessageType().getIconHtml();
		SafeHtmlBuilder safeHtmlBuilder = new SafeHtmlBuilder();
		String subjectClass = "";
		if (localStorage.isMessageUnread(id)) {
			subjectClass = "messageListEntrySubjectUnread";
		}
		safeHtmlBuilder.appendHtmlConstant("<span class=\"" + subjectClass
				+ "\">");
		safeHtmlBuilder.appendEscaped(subjectText);
		safeHtmlBuilder
				.appendHtmlConstant("</span><br/><span class=\"messageListEntrySecondLine\">");
		safeHtmlBuilder.appendEscaped(messageDateText);
		safeHtmlBuilder.appendEscaped(receiverText);
		safeHtmlBuilder.appendHtmlConstant("</span>");
		return new HTML(creationDatePart + " -- " + iconHtml
				+ safeHtmlBuilder.toSafeHtml().asString());
	}

	public void setSubjectText(String subjectText) {
		this.subjectText = subjectText;
	}

	public void setMessageDateText(String messageDateText) {
		this.messageDateText = messageDateText;
	}

	public void setReceiverText(String receiverText) {
		this.receiverText = receiverText;
	}

	public boolean isAllowedToBeDeleted() {
		return this.allowedToBeDeleted;
	}

	public void setAllowedToBeDeleted(boolean allowedToBeDeleted) {
		this.allowedToBeDeleted = allowedToBeDeleted;
	}

}
