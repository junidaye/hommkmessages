package com.appspot.hommkmessage.client;

import com.google.gwt.storage.client.Storage;

public class LocalStorage {

	private static final String STATUS_READ = "read";
	private final Storage storage;

	public LocalStorage() {
		storage = Storage.getLocalStorageIfSupported();
	}

	public boolean isMessageUnread(String messageId) {
		if (notSupported()) {
			return true;
		}
		String item = storage.getItem(messageId);
		return !STATUS_READ.equals(item);
	}

	public void markAsRead(String messageId) {
		if (notSupported()) {
			return;
		}
		storage.setItem(messageId, STATUS_READ);
	}

	private boolean notSupported() {
		return storage == null;
	}

	public String getUserId() {
		if (notSupported()) {
			return null;
		}
		return storage.getItem("userId");
	}

}
