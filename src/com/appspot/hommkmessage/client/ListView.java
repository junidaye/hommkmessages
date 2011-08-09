package com.appspot.hommkmessage.client;

import java.util.List;

import com.appspot.hommkmessage.shared.DateFormatter;
import com.appspot.hommkmessage.shared.MessageMetadata;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.OpenEvent;
import com.google.gwt.event.logical.shared.OpenHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ListView extends VerticalPanel {
	private final MessagesServiceAsync messagesService = GWT
			.create(MessagesService.class);

	private final DateFormatter dateFormatter;
	private String searchString = "";

	public ListView(DateFormatter dateFormatter) {
		this.dateFormatter = dateFormatter;
	}

	public void refresh() {
		clear();
		messagesService
				.getMessageMetadata(getSearchString(), refreshCallback());
	}

	private AsyncCallback<List<MessageMetadata>> refreshCallback() {
		return new AsyncCallback<List<MessageMetadata>>() {

			@Override
			public void onSuccess(List<MessageMetadata> messageMetadataList) {
				if (messageMetadataList.isEmpty()) {
					add(new HTML("kein Ergebnis gefunden"));
				}
				int index = 0;
				for (MessageMetadata messageMetadata : messageMetadataList) {
					index++;
					createListEntry(index, messageMetadata);
				}
			}

			@Override
			public void onFailure(Throwable caught) {
				HTML textBox = new HTML();
				textBox.setText("Server error");
				textBox.addStyleName("serverResponseError");
				add(textBox);
			}
		};
	}

	private void createListEntry(final int index,
			final MessageMetadata messageMetadata) {
		String header = messageMetadata.getInfoLine(dateFormatter);
		final DisclosurePanel entryPanel = new DisclosurePanel(header);
		add(entryPanel);

		entryPanel.addOpenHandler(new OpenHandler<DisclosurePanel>() {

			@Override
			public void onOpen(OpenEvent<DisclosurePanel> event) {
				entryPanel.clear();
				final MessageFrame messageFrame = new MessageFrame(
						"messageFrame" + index);
				messageFrame.addStyleName("messageInListView");
				entryPanel.add(messageFrame);
				messageFrame.showMessage(messageMetadata.getId());
			}
		});
	}

	public String getSearchString() {
		return searchString;
	}

	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}
}
