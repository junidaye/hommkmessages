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
		String key = req.getParameter("key");
		String subjectText = req.getParameter("subjectText");
		String dateText = req.getParameter("dateText");
		String receiverText = req.getParameter("receiverText");
		String contentText = req.getParameter("contentText");
		validateParameters(htmlSource, key, subjectText, dateText,
				receiverText, contentText);
		Message message = new Message(htmlSource, key, subjectText, dateText,
				receiverText, contentText);
		new RepositoryAccess().save(message);

		String newMessageUrl = req.getContextPath() + "message?messageId="
				+ message.getId();
		resp.sendRedirect(newMessageUrl);
	}

	private void validateParameters(String htmlSource, String key,
			String subjectText, String dateText, String receiverText,
			String contentText) {
		if (htmlSource == null || key == null || subjectText == null
				|| dateText == null || receiverText == null
				|| contentText == null) {
			throw new IllegalArgumentException("missing parameter");
		}
	}

}
