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
