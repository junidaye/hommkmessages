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
package com.appspot.hommkmessage.client.view;

import java.util.List;

import com.appspot.hommkmessage.client.LocalStorage;
import com.appspot.hommkmessage.client.service.MessagesService;
import com.appspot.hommkmessage.client.service.MessagesServiceAsync;
import com.appspot.hommkmessage.shared.DateFormatter;
import com.appspot.hommkmessage.shared.MessageMetadata;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.event.logical.shared.OpenEvent;
import com.google.gwt.event.logical.shared.OpenHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.DisclosurePanelImages;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SubmitButton;
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
	private final String userId;

	public ListView(DateFormatter dateFormatter, LocalStorage localStorage,
			String password) {
		this.dateFormatter = dateFormatter;
		this.localStorage = localStorage;
		this.password = password;
		this.userId = localStorage.getUserId();
	}

	public void refresh() {
		clear();
		messagesService.getMessageMetadata(getSearchString(), password, userId,
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
		final HorizontalPanel entryLinePanel = new HorizontalPanel();
		final DisclosurePanel entryPanel = new DisclosurePanel();
		entryPanel.addStyleName("messageListEntryPanel");
		setEntryHeader(messageMetadata, entryPanel, true);
		entryLinePanel.add(entryPanel);
		addDeleteLink(messageMetadata, entryLinePanel);
		add(entryLinePanel);

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

	private void addDeleteLink(final MessageMetadata messageMetadata,
			final Panel parent) {
		if (!messageMetadata.isAllowedToBeDeleted()) {
			return;
		}
		SubmitButton button = new SubmitButton(
				"<img src=\"images/cross_hand_drawn_linda_k_01_rotated.svg\" class=\"messageListEntryOptionIcon\" />");
		button.addStyleName("messageListEntryOptionButton");
		button.setTitle("Löschen");
		button.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (!Window.confirm("Nachricht wirklich löschen?")) {
					return;
				}
				messagesService.deleteMessage(messageMetadata.getId(),
						password, localStorage.getUserId(),
						new AsyncCallback<Void>() {

							@Override
							public void onSuccess(Void result) {
								parent.setVisible(false);
							}

							@Override
							public void onFailure(Throwable caught) {
								// if problems occur, add error message in the
								// future

							}
						});
			}
		});
		parent.add(button);
	}

	public String getSearchString() {
		return searchString;
	}

	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}
}
