package com.appspot.hommkmessage.client;

import com.google.gwt.event.dom.client.LoadEvent;
import com.google.gwt.event.dom.client.LoadHandler;
import com.google.gwt.user.client.ui.Frame;

public class MessageFrame extends Frame {

	public MessageFrame() {
		getElement().setId("messageFrame");
		addLoadHandler(new LoadHandler() {

			@Override
			public void onLoad(LoadEvent event) {
				/*
				 * com.google.gwt.user.client.Element contentWindow = mailFrame
				 * .getElement(); // .getPropertyJSO( "contentWindow"); //
				 * .getItem(0); String width = contentWindow.getScrollWidth() +
				 * "px"; String height = contentWindow.getScrollHeight() + "px";
				 * message.setSize(width, height);
				 */
				resizeMessageFrame();
			}
		});
	}

	private native void resizeMessageFrame() /*-{
		var id = 'messageFrame';
		var newheight = $wnd.document.getElementById(id).contentWindow.document.body.scrollHeight;
		var newwidth = $wnd.document.getElementById(id).contentWindow.document.body.scrollWidth;

		$wnd.document.getElementById(id).height = (newheight) + "px";
		$wnd.document.getElementById(id).width = (newwidth) + "px";
	}-*/;

	public void showMessage(String messageId) {
		setUrl("/hommk_message/message?messageId=" + messageId);
	}
}
