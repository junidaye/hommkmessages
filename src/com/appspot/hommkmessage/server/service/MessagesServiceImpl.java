package com.appspot.hommkmessage.server.service;

import java.util.ArrayList;
import java.util.List;

import com.appspot.hommkmessage.client.service.MessagesService;
import com.appspot.hommkmessage.server.repository.Message;
import com.appspot.hommkmessage.server.repository.RepositoryAccess;
import com.appspot.hommkmessage.shared.MessageMetadata;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class MessagesServiceImpl extends RemoteServiceServlet implements
		MessagesService {

	@Override
	public List<MessageMetadata> getMessageMetadata(String searchString,
			String password, String forUserId) {
		List<Message> messages = new RepositoryAccess(password)
				.getMessages(searchString);
		List<MessageMetadata> metadataList = new ArrayList<MessageMetadata>();
		for (Message message : messages) {
			MessageMetadata messageMetadata = message.getMetadata(forUserId);
			metadataList.add(messageMetadata);
		}
		return metadataList;
	}

	@Override
	public void deleteMessage(String messageId, String userId) {
		new RepositoryAccess("anypassword").deleteMessage(messageId, userId);
	}

}
