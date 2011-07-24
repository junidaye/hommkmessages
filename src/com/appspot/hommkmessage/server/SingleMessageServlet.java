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
		Message message = new RepositoryAccess().get(messageId);
		String sourceFromRepo = message.getHtmlSource();
		String info = "<div>Creation Date: "
				+ message.getCreationDateFormatted(locale) + "</div>";
		return addWrapping(info + sourceFromRepo);
	}

	private String addWrapping(String sourceFromRepo) {
		String result = "<html>" + sourceFromRepo + "</html>";
		// TODO next : hier nicht mehr html nehmen, sondern ein iframe
		// das hat als "src" eine URL, die eine Mail/Kampfbericht und nichts
		// anderes zurückgibt
		return result;
	}
}
