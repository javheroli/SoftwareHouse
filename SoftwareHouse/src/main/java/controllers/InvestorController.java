
package controllers;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.InvestorService;
import domain.Investor;
import forms.ActorForm;

@Controller
@RequestMapping("/investor")
public class InvestorController extends AbstractController {

	// Services

	@Autowired
	private InvestorService	investorService;


	// Constructors

	public InvestorController() {
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
		Investor investor;

		investor = this.investorService.reconstruct(actorForm, binding);

		if (binding.hasErrors())
			result = this.createEditModelAndView(actorForm);
		else if (investor == null)
			result = this.createEditModelAndView(actorForm, "investor.commit.error");
		else
			try {
				this.investorService.save(investor);

				Date moment;
				final String welcomeMessage;

				moment = new Date();

				welcomeMessage = "welcome.greeting.signUp.investor";

				result = new ModelAndView("welcome/index");

				result.addObject("welcomeMessage", welcomeMessage);
				result.addObject("moment", moment);
				result.addObject("signUp", true);

			} catch (final Throwable oops) {
				result = this.createEditModelAndView(actorForm, "investor.commit.error");
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

		result = new ModelAndView("investor/edit");
		result.addObject("actorForm", actorForm);

		result.addObject("redirectURI", "welcome/index.do");

		result.addObject("permission", true);

		result.addObject("message", message);

		return result;
	}

}
