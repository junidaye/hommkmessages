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

import com.appspot.hommkmessage.client.LocalStorage;
import com.appspot.hommkmessage.shared.DateFormatter;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;

public class Hommk_message implements EntryPoint {

	@Override
	public void onModuleLoad() {
		String password = Window.Location.getParameter("key");
		validateParameters(password);

		final ListView listView = createListView(password);

		SearchHandler search = initSearch(listView);
		search.submitSearch();
	}

	private void validateParameters(String password) {
		if (password == null) {
			throw new IllegalArgumentException("parameter key is not set");
		}
	}

	private ListView createListView(String password) {
		RootPanel.get("listViewContainer").add(
				new HTML("<b>Nachrichten sortiert nach Upload-Datum:</b>"));

		DateFormatter dateFormatter = new DateFormatter();
		LocalStorage localStorage = new LocalStorage();
		final ListView listView = new ListView(dateFormatter, localStorage,
				password);
		RootPanel.get("listViewContainer").add(listView);
		return listView;
	}

	private SearchHandler initSearch(final ListView listView) {
		final Button searchButton = new Button("Suche");
		final TextBox searchTextField = new TextBox();
		searchTextField.setText("");
		searchTextField.setFocus(true);
		searchButton.addStyleName("sendButton");
		HorizontalPanel searchPanel = new HorizontalPanel();
		searchPanel.add(searchTextField);
		searchPanel.add(searchButton);
		RootPanel.get("searchLineContainer").add(searchPanel);

		SearchHandler handler = new SearchHandler(listView, searchTextField);
		searchButton.addClickHandler(handler);
		searchTextField.addKeyUpHandler(handler);
		return handler;
	}
}
