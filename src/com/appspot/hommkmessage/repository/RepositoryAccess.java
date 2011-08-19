package com.appspot.hommkmessage.repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;

public class RepositoryAccess {

	private final PersistenceManagerFactory persistenceManagerFactory = PersistenceManagerFactorySingleton.INSTANCE
			.get();
	private final String password;

	public RepositoryAccess(String key) {
		this.password = key;
	}

	public void save(Message message) {
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

	public List<Message> getMessages(String searchString) {
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

}
