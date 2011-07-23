package com.appspot.hommkmail.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("mailbox")
public interface MailboxService extends RemoteService {

	public String getMailSource(String mailId);
}
