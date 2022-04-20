package com.example.RgApparel1.controllers;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.RgApparel1.models.Apparels;
import com.example.RgApparel1.models.Users;
import com.example.RgApparel1.services.DbService;

@Controller
public class HomeController {
	@Autowired
	private DbService serv;
	
	@RequestMapping("/*")
	public String any() {
		return "forward:/login/";
	}

	@RequestMapping("login")
	public String login(HttpServletRequest req) {

		System.out.println("in login " + req.getRequestURI());

		Cookie[] cks = req.getCookies();
		if (cks != null) {
			for (Cookie c : cks) {
				if (c.getName().equals("rgaID") && c.getValue()!=null) {

					System.out.println("cookie found " + c.getName() + " " + c.getValue());

					req.setAttribute("id", Integer.parseInt(c.getValue()));
					return "forward:/home/";
				}
			}
		}

		return "login";
	}

	@RequestMapping("home")
	public ModelAndView home(@RequestAttribute("id") Integer id) {

		System.out.println("in home " + id);

		Users u = serv.findById(id);
		if (u == null) {
			ModelAndView mv = new ModelAndView("forward:/login/");
			return mv;
		}
		
		List<Apparels> us = serv.getApparelsbyUserPref(u.getRangeBot(), u.getRangeTop(), u.getType());

		ModelAndView mv = new ModelAndView("home");
		mv.addObject("id", u.getId());
		String type = "";
		if(u.getType().equals("seas"))
			type = "Seasonals";
		else
			type = "New Arrivals";
		mv.addObject("pref", type);
		mv.addObject("apparels", us);

		return mv;
	}
	
	@RequestMapping("allApparels")
	public ModelAndView allApparels() {
		Iterable<Apparels> us = serv.getAllApparels();

		ModelAndView mv = new ModelAndView("all");
		mv.addObject("apparels", us);

		return mv;
	}
	
	@RequestMapping("purchase")
	public String purchase(HttpServletRequest req) {
		String[] icodes = req.getParameterValues("icodes[]");
		if(icodes==null) {
			return "forward:/login/";
		}
			
		int id = 0;
		Cookie[] cks = req.getCookies();
		if (cks != null) {
			for (Cookie c : cks) {
				if (c.getName().equals("rgaID") && c.getValue()!=null) {
					id = Integer.valueOf(c.getValue());
					break;
				}
			}
		}
		
		System.out.println("in purchase " + id);
		
		serv.updateRange(id, icodes);
		
		req.setAttribute("id", id);
		return "forward:/home/";
 	}
	
	@RequestMapping("out")
	public String signOut(HttpServletResponse res) {
		Cookie c = new Cookie("rgaID",null);
		c.setMaxAge(0);
		res.addCookie(c);

		return "login";
	}
}