
package controllers.administrator;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.DisciplineService;
import services.ExpertService;
import controllers.AbstractController;
import domain.Discipline;
import domain.Expert;

@Controller
@RequestMapping("/expert/administrator")
public class ExpertAdministratorController extends AbstractController {

	// Services

	@Autowired
	private ExpertService		expertService;

	@Autowired
	private DisciplineService	disciplineService;


	// Constructors

	public ExpertAdministratorController() {
		super();
	}

	// Creation ---------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Expert expert;

		expert = this.expertService.create();
		result = this.createEditModelAndView(expert);
		result.addObject("permission", true);
		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ModelAndView save(Expert expert, final BindingResult binding) {
		ModelAndView result;

		expert = this.expertService.reconstruct(expert, binding);
		if (binding.hasErrors()) {
			result = this.createEditModelAndView(expert);
			result.addObject("permission", true);
		} else
			try {
				Expert saved;

				saved = this.expertService.save(expert);

				result = new ModelAndView("redirect:/expert/actor/display.do?expertId=".concat(String.valueOf(saved.getId())));

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
		Collection<Discipline> disciplines;

		result = new ModelAndView("expert/edit");

		disciplines = this.disciplineService.findAll();

		result.addObject("expert", expert);
		result.addObject("disciplines", disciplines);
		result.addObject("actionURI", "expert/administrator/create.do");
		result.addObject("redirectURI", "welcome/index.do");
		result.addObject("message", message);
		result.addObject("permission", true);

		return result;
	}

}
