package com.appspot.hommkmessage.client;

import java.util.List;

import com.appspot.hommkmessage.shared.MessageMetadata;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface MessagesServiceAsync {

	void getMessageMetadata(String searchString,
			AsyncCallback<List<MessageMetadata>> asyncCallback);

}
