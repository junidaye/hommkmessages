package com.appspot.hommkmessage.server;

import java.util.ArrayList;
import java.util.List;

import com.appspot.hommkmessage.client.MessagesService;
import com.appspot.hommkmessage.repository.Message;
import com.appspot.hommkmessage.repository.RepositoryAccess;
import com.appspot.hommkmessage.shared.MessageMetadata;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class MessagesServiceImpl extends RemoteServiceServlet implements
		MessagesService {

	@Override
	public List<MessageMetadata> getMessageMetadata(String searchString) {
		List<Message> messages = new RepositoryAccess()
				.getMessages(searchString);
		List<MessageMetadata> metadataList = new ArrayList<MessageMetadata>();
		for (Message message : messages) {
			MessageMetadata messageMetadata = message.getMetadata();
			metadataList.add(messageMetadata);
		}
		return metadataList;
	}

}
