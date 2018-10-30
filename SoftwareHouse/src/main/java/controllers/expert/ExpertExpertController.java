
package controllers.expert;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ExpertService;
import controllers.AbstractController;
import domain.Expert;

@Controller
@RequestMapping("/expert/expert")
public class ExpertExpertController extends AbstractController {

	// Services

	@Autowired
	private ExpertService	expertService;


	// Constructors

	public ExpertExpertController() {
		super();
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int expertId) {
		ModelAndView result;
		Expert expert;

		expert = this.expertService.findOne(expertId);
		Assert.notNull(expert);

		result = this.createEditModelAndView(expert);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public ModelAndView save(Expert expert, final BindingResult binding) {
		ModelAndView result;

		expert = this.expertService.reconstruct(expert, binding);
		if (binding.hasErrors())
			result = this.createEditModelAndView(expert);
		else
			try {
				this.expertService.save(expert);

				result = new ModelAndView("redirect:/expert/actor/display.do?expertId=".concat(String.valueOf(expert.getId())));

			} catch (final Throwable oops) {
				result = this.createEditModelAndView(expert, "expert.commit.error");

			}
		return result;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Expert expert) {
		ModelAndView result;

		result = this.createEditModelAndView(expert, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Expert expert, final String message) {
		ModelAndView result;
		Boolean permission;
		Expert principal;

		principal = this.expertService.findByPrincipal();

		permission = false;
		if (principal.getId() == expert.getId())
			permission = true;

		result = new ModelAndView("expert/edit");

		result.addObject("actionURI", "expert/expert/edit.do");
		result.addObject("redirectURI", "expert/actor/display.do?expertId=".concat(String.valueOf(expert.getId())));
		result.addObject("expert", expert);
		result.addObject("permission", permission);
		result.addObject("message", message);

		return result;
	}

}
