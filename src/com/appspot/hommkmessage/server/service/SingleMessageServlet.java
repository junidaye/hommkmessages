/*******************************************************************************
 * This file is part of hommkmessage.
 * 
 * hommkmessage is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * hommkmessage is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with hommkmessage.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package com.appspot.hommkmessage.server.service;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.appspot.hommkmessage.server.repository.Message;
import com.appspot.hommkmessage.server.repository.RepositoryAccess;

public class SingleMessageServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String messageId = req.getParameter("messageId");
		String messageSource = getMessageSource(messageId, req.getLocale());
		resp.setContentType("text/html; charset=UTF-8");
		resp.getWriter().print(messageSource);
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
