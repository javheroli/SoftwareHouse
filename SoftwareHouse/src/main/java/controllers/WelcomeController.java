/*
 * WelcomeController.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/welcome")
public class WelcomeController extends AbstractController {

	// Constructors -----------------------------------------------------------

	public WelcomeController() {
		super();
	}

	// Index ------------------------------------------------------------------		

	@RequestMapping(value = "/index")
	public ModelAndView index(@RequestParam(required = false, value = "language", defaultValue = "en") final String language) {
		ModelAndView result;
		Date moment;

		final String cookiePolicyPage;

		if (language.endsWith("s"))
			cookiePolicyPage = "welcome/cookiePolicy.do?language=es";
		else
			cookiePolicyPage = "welcome/cookiePolicy.do?language=en";

		moment = new Date();

		result = new ModelAndView("welcome/index");
		result.addObject("cookiePolicyPage", cookiePolicyPage);
		result.addObject("moment", moment);

		return result;
	}

	@RequestMapping(value = "/cookiePolicy", params = {
		"language"
	})
	public ModelAndView cookiePolicy(@RequestParam final String language) {
		ModelAndView result;
		result = new ModelAndView("welcome/cookiePolicy");

		if (language.equals("es"))
			result.addObject("language", "es");
		else
			result.addObject("language", "en");

		return result;
	}

	@RequestMapping(value = "/termsAndConditions", params = {
		"language"
	})
	public ModelAndView termsAndConditions(@RequestParam final String language) {
		ModelAndView result;
		result = new ModelAndView("welcome/termsAndConditions");

		if (language.equals("es"))
			result.addObject("language", "es");
		else
			result.addObject("language", "en");

		return result;
	}

}
