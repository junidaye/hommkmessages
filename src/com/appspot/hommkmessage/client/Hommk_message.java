package com.appspot.hommkmessage.client;

import com.appspot.hommkmessage.shared.DateFormatter;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Hommk_message implements EntryPoint {
	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network "
			+ "connection and try again.";

	/**
	 * Create a remote service proxy to talk to the server-side Greeting
	 * service.
	 */
	// private final MessagesServiceAsync messageListService = GWT
	// .create(MessagesService.class);

	/**
	 * This is the entry point method.
	 */
	@Override
	public void onModuleLoad() {
		final Button sendButton = new Button("Suche");
		final TextBox searchTextField = new TextBox();
		searchTextField.setText("");
		searchTextField.setFocus(true);
		final Label errorLabel = new Label();
		RootPanel.get("errorLabelContainer").add(errorLabel);

		// We can add style names to widgets
		sendButton.addStyleName("sendButton");

		// Add the nameField and sendButton to the RootPanel
		// Use RootPanel.get() to get the entire body element
		HorizontalPanel searchPanel = new HorizontalPanel();
		searchPanel.add(searchTextField);
		searchPanel.add(sendButton);
		RootPanel.get("searchLineContainer").add(searchPanel);

		// dialogBox.setAnimationEnabled(true);
		// We can set the id of a widget by accessing its Element

		RootPanel.get("listViewContainer").add(
				new HTML("<b>Nachrichten sortiert nach Upload-Datum:</b>"));

		DateFormatter dateFormatter = new DateFormatter();
		LocalStorage localStorage = new LocalStorage();
		final ListView listView = new ListView(dateFormatter, localStorage);
		RootPanel.get("listViewContainer").add(listView);

		class SubmitSearchHandler implements ClickHandler, KeyUpHandler {
			@Override
			public void onClick(ClickEvent event) {
				submitSearch();
			}

			@Override
			public void onKeyUp(KeyUpEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					submitSearch();
				}
			}

			private void submitSearch() {
				errorLabel.setText("");

				listView.setSearchString(searchTextField.getText());
				listView.refresh();
			}
		}

		// Add a handler to send the name to the server
		SubmitSearchHandler handler = new SubmitSearchHandler();
		sendButton.addClickHandler(handler);
		searchTextField.addKeyUpHandler(handler);

		handler.submitSearch();
	}
}
