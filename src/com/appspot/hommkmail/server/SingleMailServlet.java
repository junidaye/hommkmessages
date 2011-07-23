package com.appspot.hommkmail.server;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SingleMailServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String mailId = req.getParameter("mailId");
		String mailSource = new MailboxServiceImpl().getMailSource(mailId);
		resp.setContentType("text/html");
		resp.getWriter().append(mailSource);
		resp.getWriter().close();
	}
}
