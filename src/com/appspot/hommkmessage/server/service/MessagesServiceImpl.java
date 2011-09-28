package com.appspot.hommkmessage.server.service;

import java.util.List;

import com.appspot.hommkmessage.client.service.MessagesService;
import com.appspot.hommkmessage.server.repository.RepositoryAccess;
import com.appspot.hommkmessage.shared.MessageMetadata;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class MessagesServiceImpl extends RemoteServiceServlet implements
		MessagesService {

	@Override
	public List<MessageMetadata> getMessageMetadata(String searchString,
			String password, String forUserId) {
		return new RepositoryAccess(password).getMessagesMetadata(searchString,
				forUserId);
	}

	@Override
	public void deleteMessage(String messageId, String userId) {
		new RepositoryAccess("anypassword").deleteMessage(messageId, userId);
	}

}
