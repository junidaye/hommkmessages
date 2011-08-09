package com.appspot.hommkmessage.client;

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
