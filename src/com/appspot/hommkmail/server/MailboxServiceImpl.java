package com.appspot.hommkmail.server;

import com.appspot.hommkmail.client.MailboxService;
import com.appspot.hommkmail.repository.RepositoryAccess;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class MailboxServiceImpl extends RemoteServiceServlet implements
		MailboxService {

	@Override
	public String getMailSource(String mailId) {
		String sourceFromRepo = new RepositoryAccess().loadAny();
		return addWrapping(sourceFromRepo);
	}

	private String addWrapping(String sourceFromRepo) {
		String result = "<html>" + sourceFromRepo + "</html>";
		// TODO next : hier nicht mehr html nehmen, sondern ein iframe
		// das hat als "src" eine URL, die eine Mail/Kampfbericht und nichts
		// anderes zurückgibt
		return result;
	}

}
