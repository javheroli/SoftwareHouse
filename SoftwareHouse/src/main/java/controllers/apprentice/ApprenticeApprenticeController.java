
package controllers.apprentice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ApprenticeService;
import controllers.AbstractController;
import domain.Apprentice;

@Controller
@RequestMapping("/apprentice/apprentice")
public class ApprenticeApprenticeController extends AbstractController {

	// Services

	@Autowired
	private ApprenticeService	apprenticeService;


	// Constructors

	public ApprenticeApprenticeController() {
		super();
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int apprenticeId) {
		ModelAndView result;
		Apprentice apprentice;

		apprentice = this.apprenticeService.findOne(apprenticeId);
		Assert.notNull(apprentice);

		result = this.createEditModelAndView(apprentice);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public ModelAndView save(Apprentice apprentice, final BindingResult binding) {
		ModelAndView result;

		apprentice = this.apprenticeService.reconstruct(apprentice, binding);
		if (binding.hasErrors())
			result = this.createEditModelAndView(apprentice);
		else
			try {
				this.apprenticeService.save(apprentice);

				result = new ModelAndView("redirect:/apprentice/actor/display.do?apprenticeId=".concat(String.valueOf(apprentice.getId())));

			} catch (final Throwable oops) {
				result = this.createEditModelAndView(apprentice, "apprentice.commit.error");

			}
		return result;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Apprentice apprentice) {
		ModelAndView result;

		result = this.createEditModelAndView(apprentice, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Apprentice apprentice, final String message) {
		ModelAndView result;
		Boolean permission;
		Apprentice principal;

		principal = this.apprenticeService.findByPrincipal();

		permission = false;
		if (principal.getId() == apprentice.getId())
			permission = true;

		result = new ModelAndView("apprentice/edit");

		result.addObject("apprentice", apprentice);
		result.addObject("permission", permission);
		result.addObject("editPersonalData", true);
		result.addObject("message", message);

		return result;
	}

}
