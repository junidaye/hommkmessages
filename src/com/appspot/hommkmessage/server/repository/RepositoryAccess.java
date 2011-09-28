package com.appspot.hommkmessage.server.repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;

import net.sf.jsr107cache.Cache;

import com.appspot.hommkmessage.shared.MessageMetadata;

public class RepositoryAccess {

	private final PersistenceManagerFactory persistenceManagerFactory = PersistenceManagerFactorySingleton.INSTANCE
			.getFactory();
	private final Cache cache = CacheSingleton.INSTANCE.getCache();

	private final String password;

	public RepositoryAccess(String key) {
		this.password = key;
	}

	public void save(Message message) {
		cache.remove(password);
		PersistenceManager persistenceManager = persistenceManagerFactory
				.getPersistenceManager();
		try {
			persistenceManager.makePersistent(message);
			persistenceManager.flush();
		} finally {
			persistenceManager.close();
		}
	}

	public Message get(String messageId) {
		PersistenceManager persistenceManager = persistenceManagerFactory
				.getPersistenceManager();
		Query query = persistenceManager.newQuery(Message.class,
				"id == messageId");
		query.declareParameters("java.lang.String messageId");
		try {
			List<Message> messages = (List<Message>) query.execute(messageId);
			if (messages.isEmpty()) {
				throw new IllegalArgumentException("message does not exist: "
						+ messageId);
			}
			return messages.get(0);
		} finally {
			persistenceManager.close();
		}
	}

	public List<MessageMetadata> getMessagesMetadata(String searchString,
			String forUserId) {
		List<MessageMetadata> metadataList;
		if (cache.containsKey(password)) {
			metadataList = (List<MessageMetadata>) cache.get(password);
		} else {
			List<Message> messages = getMessages(searchString);
			metadataList = new ArrayList<MessageMetadata>();
			for (Message message : messages) {
				MessageMetadata messageMetadata = message
						.getMetadata(forUserId);
				metadataList.add(messageMetadata);
			}
			cache.put(password, metadataList);
		}
		return metadataList;
	}

	private List<Message> getMessages(String searchString) {
		PersistenceManager persistenceManager = persistenceManagerFactory
				.getPersistenceManager();
		Query query = persistenceManager
				.newQuery("SELECT FROM "
						+ Message.class.getName()
						+ " WHERE password == accessPassword ORDER BY creationDate DESC");
		query.declareParameters("java.lang.String accessPassword");
		try {
			List<Message> result = new ArrayList<Message>(
					(List<Message>) query.execute(password));
			filter(result, searchString);
			return result;
		} finally {
			persistenceManager.close();
		}
	}

	private void filter(List<Message> messages, String searchString) {
		String searchStringLowerCase = searchString.toLowerCase();
		for (Iterator<Message> it = messages.iterator(); it.hasNext();) {
			Message message = it.next();
			if (!message.matchesSearchText(searchStringLowerCase)) {
				it.remove();
			}
		}
	}

	public void deleteMessage(String messageId, String userId) {
		Message message = get(messageId);
		if (!message.getUserId().equals(userId)) {
			throw new IllegalArgumentException("Access denied");
		}
		cache.remove(password);
		PersistenceManager persistenceManager = persistenceManagerFactory
				.getPersistenceManager();
		try {
			persistenceManager.makePersistent(message);
			persistenceManager.deletePersistent(message);
			persistenceManager.flush();
		} finally {
			persistenceManager.close();
		}
	}

}
