package com.example.RgApparel1.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.RgApparel1.models.Users;
import com.example.RgApparel1.services.DbService;

@Component
public class LoginFilter implements Filter {
	@Autowired
	DbService serv;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;

		System.out.println("in filter " + req.getRequestURI());

		if (!req.getRequestURI().contains("home") && !req.getRequestURI().contains("allApparels")) {
			chain.doFilter(request, response);
			return;
		}

		/* if valid cookie present */
		Cookie[] cks = req.getCookies();
		if (cks != null) {
			for (Cookie c : cks) {
				if (c.getName().equals("rgaID") && c.getValue()!=null) {

					System.out.println("cookie found " + c.getName() + " " + c.getValue());

					req.setAttribute("id", Integer.parseInt(c.getValue()));
					chain.doFilter(request, response);
					return;
				}
			}
		}

		/* no valid cookie present */
		String id = req.getParameter("id");
		String type = req.getParameter("pref");
		if (id != null && !id.equals("") && type != null && !type.equals("")) {/* first login */
			int x = Integer.parseInt(id);
			
			if(!serv.save(new Users(x, type))) {
				RequestDispatcher rd = req.getRequestDispatcher("login");
				rd.forward(request, response);
				return;
			}

			System.out.println("user " + id + " saved to db");

			/* set cookie */
			Cookie c = new Cookie("rgaID", id);
			res.addCookie(c);

			req.setAttribute("id", x);
			chain.doFilter(request, response);
		} else {/* invalid request, go back to login */

			System.out.println("in filter req dispatched to login");

			RequestDispatcher rd = req.getRequestDispatcher("login");
			rd.forward(request, response);
		}

	}
}
