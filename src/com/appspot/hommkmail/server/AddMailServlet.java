package com.appspot.hommkmail.server;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.appspot.hommkmail.repository.RepositoryAccess;

public class AddMailServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String htmlSource = req.getParameter("source");
		new RepositoryAccess().save(htmlSource);
	}
}
