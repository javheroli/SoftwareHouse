
package controllers;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ApprenticeService;
import domain.Apprentice;
import forms.ActorForm;

@Controller
@RequestMapping("/apprentice")
public class ApprenticeController extends AbstractController {

	// Services

	@Autowired
	private ApprenticeService	apprenticeService;


	// Constructors

	public ApprenticeController() {
		super();
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		ActorForm actorForm;

		actorForm = new ActorForm();
		result = this.createEditModelAndView(actorForm);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public ModelAndView save(final ActorForm actorForm, final BindingResult binding) {
		ModelAndView result;
		Apprentice apprentice;

		apprentice = this.apprenticeService.reconstruct(actorForm, binding);

		if (binding.hasErrors())
			result = this.createEditModelAndView(actorForm);
		else if (apprentice == null)
			result = this.createEditModelAndView(actorForm, "apprentice.commit.error");
		else
			try {
				this.apprenticeService.save(apprentice);

				Date moment;
				final String welcomeMessage;

				moment = new Date();

				welcomeMessage = "welcome.greeting.signUp.apprentice";

				result = new ModelAndView("welcome/index");

				result.addObject("welcomeMessage", welcomeMessage);
				result.addObject("moment", moment);
				result.addObject("signUp", true);

			} catch (final Throwable oops) {
				result = this.createEditModelAndView(actorForm, "apprentice.commit.error");
			}
		return result;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final ActorForm actorForm) {
		ModelAndView result;

		result = this.createEditModelAndView(actorForm, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final ActorForm actorForm, final String message) {
		ModelAndView result;

		result = new ModelAndView("apprentice/edit");
		result.addObject("actorForm", actorForm);

		result.addObject("redirectURI", "welcome/index.do");

		result.addObject("permission", true);

		result.addObject("message", message);

		return result;
	}

}
