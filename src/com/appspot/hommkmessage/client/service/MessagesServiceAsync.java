package com.appspot.hommkmessage.client.service;

import java.util.List;

import com.appspot.hommkmessage.shared.MessageMetadata;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface MessagesServiceAsync {

	void getMessageMetadata(String searchString, String password,
			String forUserId, AsyncCallback<List<MessageMetadata>> asyncCallback);

	void deleteMessage(String messageId, String userId,
			AsyncCallback<Void> callback);

}
