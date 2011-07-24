package com.appspot.hommkmessage.repository;

public class RepositoryAccess {
	static Message oneAndOnlyMessage = new Message("");

	public void save(Message message) {
		oneAndOnlyMessage = message;
	}

	public Message loadAny() {
		return oneAndOnlyMessage;
	}

	public Message get(String messageId) {
		return loadAny();
	}

}
