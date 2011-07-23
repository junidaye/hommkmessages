package com.appspot.hommkmail.client;

import com.google.gwt.event.dom.client.LoadEvent;
import com.google.gwt.event.dom.client.LoadHandler;
import com.google.gwt.user.client.ui.Frame;

public class MailFrame extends Frame {

	public MailFrame() {
		getElement().setId("mailFrame");
		addLoadHandler(new LoadHandler() {

			@Override
			public void onLoad(LoadEvent event) {
				/*
				 * com.google.gwt.user.client.Element contentWindow = mailFrame
				 * .getElement(); // .getPropertyJSO( "contentWindow"); //
				 * .getItem(0); String width = contentWindow.getScrollWidth() +
				 * "px"; String height = contentWindow.getScrollHeight() + "px";
				 * mailFrame.setSize(width, height);
				 */
				resizeMailFrame();
			}
		});
	}

	private native void resizeMailFrame() /*-{
		var id = 'mailFrame';
		var newheight = $wnd.document.getElementById(id).contentWindow.document.body.scrollHeight;
		var newwidth = $wnd.document.getElementById(id).contentWindow.document.body.scrollWidth;

		$wnd.document.getElementById(id).height = (newheight) + "px";
		$wnd.document.getElementById(id).width = (newwidth) + "px";
	}-*/;

	public void showMail(String mailId) {
		setUrl("/hommk_mail/mail?mailId=" + mailId);
	}
}
