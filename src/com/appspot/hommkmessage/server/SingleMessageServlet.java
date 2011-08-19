package com.appspot.hommkmessage.server;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.appspot.hommkmessage.repository.Message;
import com.appspot.hommkmessage.repository.RepositoryAccess;

public class SingleMessageServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String messageId = req.getParameter("messageId");
		String messageSource = getMessageSource(messageId, req.getLocale());
		resp.setContentType("text/html");
		resp.getWriter().append(messageSource);
		resp.getWriter().close();
	}

	private String getMessageSource(String messageId, Locale locale) {
		Message message = new RepositoryAccess("no_password_necessary_yet")
				.get(messageId);
		String sourceFromRepo = message.getUncompressedHtmlSource();
		return addWrapping(sourceFromRepo);
	}

	private String addWrapping(String incompleteHtmlCode) {
		String result = "<html>" + incompleteHtmlCode + "</html>";
		return result;
	}
}
