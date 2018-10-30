
package controllers.administrator;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.DisciplineService;
import controllers.AbstractController;
import domain.Discipline;

@Controller
@RequestMapping("/discipline/administrator")
public class DisciplineAdministratorController extends AbstractController {

	// Services

	@Autowired
	private DisciplineService	disciplineService;


	// Constructors

	public DisciplineAdministratorController() {
		super();
	}

	// Creation ---------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final Collection<Discipline> disciplines, usedDisciplines;

		disciplines = this.disciplineService.findAll();

		usedDisciplines = this.disciplineService.findUsedDisciplines();

		result = new ModelAndView("discipline/list");
		result.addObject("disciplines", disciplines);
		result.addObject("usedDisciplines", usedDisciplines);

		return result;

	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Discipline discipline;

		discipline = this.disciplineService.create();
		result = this.createEditModelAndView(discipline);
		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ModelAndView save(@Valid final Discipline discipline, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(discipline);
		else
			try {

				this.disciplineService.save(discipline);

				result = new ModelAndView("redirect:list.do");

			} catch (final Throwable oops) {
				result = this.createEditModelAndView(discipline, "discipline.commit.error");

			}
		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int disciplineId) {
		ModelAndView result;
		Discipline discipline;

		discipline = this.disciplineService.findOne(disciplineId);
		Assert.notNull(discipline);

		this.disciplineService.delete(discipline);

		result = new ModelAndView("redirect: list.do");
		return result;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Discipline discipline) {
		ModelAndView result;

		result = this.createEditModelAndView(discipline, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Discipline discipline, final String message) {
		ModelAndView result;

		result = new ModelAndView("discipline/edit");

		result.addObject("discipline", discipline);
		result.addObject("message", message);

		return result;
	}

}
