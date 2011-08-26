package com.appspot.hommkmessage.client.view;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.ui.TextBox;

public class SearchHandler implements ClickHandler,
		KeyUpHandler {
	private final ListView listView;
	private final TextBox searchTextField;

	public SearchHandler(ListView listView, TextBox searchTextField) {
		this.listView = listView;
		this.searchTextField = searchTextField;
	}

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

	void submitSearch() {
		listView.setSearchString(searchTextField.getText());
		listView.refresh();
	}
}