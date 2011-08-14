package com.appspot.hommkmessage.shared;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class MessageTypeTest {

	@Test
	public void getIconHtml_isEmptyForUnknownIcons() {
		MessageType type = MessageType.TYPE_UNKNOWN;

		String iconHtml = type.getIconHtml();

		assertTrue(iconHtml.isEmpty());
	}

}
