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

import com.google.gwt.event.dom.client.LoadEvent;
import com.google.gwt.event.dom.client.LoadHandler;
import com.google.gwt.user.client.ui.Frame;

public class MessageFrame extends Frame {

	public MessageFrame(final String frameId) {
		getElement().setId(frameId);
		addLoadHandler(new LoadHandler() {

			@Override
			public void onLoad(LoadEvent event) {
				resizeMessageFrame(frameId);
			}
		});
	}

	private native void resizeMessageFrame(String frameId) /*-{
		var newheight = $wnd.document.getElementById(frameId).contentWindow.document.body.scrollHeight;
		var newwidth = $wnd.document.getElementById(frameId).contentWindow.document.body.scrollWidth;

		$wnd.document.getElementById(frameId).height = (newheight) + "px";
		$wnd.document.getElementById(frameId).width = (newwidth) + "px";
	}-*/;

	public void showMessage(String messageId) {
		setUrl("/hommk_message/message?messageId=" + messageId);
	}
}
