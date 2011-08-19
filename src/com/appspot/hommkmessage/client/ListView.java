package com.appspot.hommkmessage.client;

import java.util.List;

import com.appspot.hommkmessage.shared.DateFormatter;
import com.appspot.hommkmessage.shared.MessageMetadata;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.event.logical.shared.OpenEvent;
import com.google.gwt.event.logical.shared.OpenHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.DisclosurePanelImages;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ListView extends VerticalPanel {
	private final MessagesServiceAsync messagesService = GWT
			.create(MessagesService.class);
	private final DisclosurePanelImages images = (DisclosurePanelImages) GWT
			.create(DisclosurePanelImages.class);

	private final DateFormatter dateFormatter;
	private String searchString = "";
	private final LocalStorage localStorage;
	private final String password;

	public ListView(DateFormatter dateFormatter, LocalStorage localStorage,
			String password) {
		this.dateFormatter = dateFormatter;
		this.localStorage = localStorage;
		this.password = password;
	}

	public void refresh() {
		clear();
		messagesService.getMessageMetadata(getSearchString(), password,
				refreshCallback());
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
		final DisclosurePanel entryPanel = new DisclosurePanel();
		setEntryHeader(messageMetadata, entryPanel, true);
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
				localStorage.markAsRead(messageMetadata.getId());

				setEntryHeader(messageMetadata, entryPanel, false);
			}

		});
		entryPanel.addCloseHandler(new CloseHandler<DisclosurePanel>() {

			@Override
			public void onClose(CloseEvent<DisclosurePanel> event) {
				setEntryHeader(messageMetadata, entryPanel, true);
			}
		});
	}

	private void setEntryHeader(final MessageMetadata messageMetadata,
			final DisclosurePanel entryPanel, boolean entryClosed) {
		HTML header = messageMetadata.getInfoLine(dateFormatter, localStorage);
		Panel entryHeaderPanel = new HorizontalPanel();
		AbstractImagePrototype imagePrototype = entryClosed ? images
				.disclosurePanelClosed() : images.disclosurePanelOpen();
		Image image = imagePrototype.createImage();
		entryHeaderPanel.add(image);
		entryHeaderPanel.add(header);
		entryPanel.setHeader(entryHeaderPanel);
	}

	public String getSearchString() {
		return searchString;
	}

	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}
}
