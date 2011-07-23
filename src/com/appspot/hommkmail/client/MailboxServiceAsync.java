package com.appspot.hommkmail.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface MailboxServiceAsync {

	public void getMailSource(String mailId, AsyncCallback<String> callback);
}
