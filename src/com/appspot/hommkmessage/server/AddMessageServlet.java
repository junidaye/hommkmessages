package com.appspot.hommkmessage.server;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.appspot.hommkmessage.repository.Message;
import com.appspot.hommkmessage.repository.RepositoryAccess;

public class AddMessageServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String htmlSource = req.getParameter("source");
		Message message = new Message(htmlSource);
		new RepositoryAccess().save(message);

		String newMessageUrl = req.getContextPath() + "message?messageId="
				+ message.getId();
		resp.sendRedirect(newMessageUrl);
	}

}
