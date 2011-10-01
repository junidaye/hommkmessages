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
