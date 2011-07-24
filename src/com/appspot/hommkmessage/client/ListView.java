package com.appspot.hommkmessage.client;

import com.google.gwt.user.client.ui.VerticalPanel;

public class ListView extends VerticalPanel {

	public void refresh() {
		// Date creationDate = new
		// RepositoryAccess().loadAny().getCreationDate();
		// String messageDescription = creationDate.toString();
		final MessageFrame messageFrame = new MessageFrame();
		add(messageFrame);
		messageFrame.showMessage("2");

	}
}
