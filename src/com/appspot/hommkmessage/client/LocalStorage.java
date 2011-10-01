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
