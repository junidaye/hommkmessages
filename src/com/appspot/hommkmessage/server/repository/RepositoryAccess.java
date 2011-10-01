/*******************************************************************************
 * This file is part of hommkmessage.
 * 
 * hommkmessage is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * hommkmessage is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with hommkmessage.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
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
		List<MessageMetadata> metadataList = new ArrayList<MessageMetadata>();
		if (cache.containsKey(password)) {
			metadataList.addAll((List<MessageMetadata>) cache.get(password));
		} else {
			List<Message> messages = getMessages(searchString);
			for (Message message : messages) {
				MessageMetadata messageMetadata = message
						.getMetadata(forUserId);
				metadataList.add(messageMetadata);
			}
			cache.put(password, new ArrayList<MessageMetadata>(metadataList));
		}
		filter(metadataList, searchString);
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
			return new ArrayList<Message>(
					(List<Message>) query.execute(password));
		} finally {
			persistenceManager.close();
		}
	}

	private void filter(List<MessageMetadata> messages, String searchString) {
		String searchStringLowerCase = searchString.toLowerCase();
		for (Iterator<MessageMetadata> it = messages.iterator(); it.hasNext();) {
			MessageMetadata message = it.next();
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
