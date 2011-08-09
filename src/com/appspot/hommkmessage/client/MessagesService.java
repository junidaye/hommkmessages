package com.appspot.hommkmessage.client;

import java.util.List;

import com.appspot.hommkmessage.shared.MessageMetadata;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("messages")
public interface MessagesService extends RemoteService {

	List<MessageMetadata> getMessageMetadata(String searchString);
}
