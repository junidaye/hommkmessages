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
package com.appspot.hommkmessage.shared;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class MessageMetadataTest {

	private MessageMetadata message;

	@Before
	public void setUp() {
		message = new MessageMetadata();
	}

	@Test
	public void getClientSafeCopy_hasNoUserId() {
		message.setUserId("AB");

		MessageMetadata clientCopy = message.getClientSafeCopy("forAnyUser");

		assertNull(clientCopy.getUserId());
	}

	@Test
	public void getClientSafeCopy_deletableIfUserIdMatches() {
		message.setUserId("AB");

		MessageMetadata clientCopy = message.getClientSafeCopy("AB");

		assertTrue(clientCopy.isAllowedToBeDeleted());
	}

	@Test
	public void getClientSafeCopy_notDeletableIfUserIdIsDifferent() {
		message.setUserId("AB");

		MessageMetadata clientCopy = message.getClientSafeCopy("XY");

		assertFalse(clientCopy.isAllowedToBeDeleted());
	}
}
