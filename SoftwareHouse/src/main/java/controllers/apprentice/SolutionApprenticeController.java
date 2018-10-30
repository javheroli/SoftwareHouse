
package controllers.apprentice;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ApprenticeService;
import services.SolutionService;
import controllers.AbstractController;
import domain.Apprentice;
import domain.Solution;

@Controller
@RequestMapping("/solution/apprentice")
public class SolutionApprenticeController extends AbstractController {

	// Services

	@Autowired
	private SolutionService		solutionService;

	@Autowired
	private ApprenticeService	apprenticeService;


	public SolutionApprenticeController() {
		super();
	}

	//Listing
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final Collection<Solution> solutions;

		solutions = this.solutionService.findAllByPrincipalApprentice();

		result = new ModelAndView("solution/list");
		result.addObject("solutions", solutions);
		result.addObject("permission", true);
		result.addObject("requestURI", "solution/apprentice/list.do");

		return result;

	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int applicationId) {
		final ModelAndView result;
		Solution solution;

		solution = this.solutionService.create(applicationId);

		result = this.createEditModelAndView(solution);

		return result;

	}

	// Edition
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int solutionId) {
		ModelAndView result;
		Solution solution;

		solution = this.solutionService.findOne(solutionId);
		Assert.notNull(solution);

		result = this.createEditModelAndView(solution);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "send")
	public ModelAndView save(Solution solution, final BindingResult binding) {
		ModelAndView result;

		solution = this.solutionService.reconstruct(solution, binding);

		if (binding.hasErrors())
			result = this.createEditModelAndView(solution);
		else
			try {
				this.solutionService.save(solution);
				result = new ModelAndView("redirect:/contest/actor/display.do?contestId=" + String.valueOf(solution.getApplication().getContest().getId()));
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(solution, "solution.commit.error");
			}

		return result;
	}

	//Ancillary methods
	protected ModelAndView createEditModelAndView(final Solution solution) {
		ModelAndView result;

		result = this.createEditModelAndView(solution, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Solution solution, final String messageCode) {
		final ModelAndView result;
		boolean permission = false;
		Apprentice principal;

		principal = this.apprenticeService.findByPrincipal();
		Assert.notNull(principal);

		if (solution.getApplication().getApplicant() == principal && solution.getApplication().getContest().getStartMoment().before(new Date(System.currentTimeMillis()))
			&& solution.getApplication().getContest().getEndMoment().after(new Date(System.currentTimeMillis())))
			permission = true;

		result = new ModelAndView("solution/edit");
		result.addObject("solution", solution);
		result.addObject("permission", permission);
		result.addObject("requestURI", "solution/apprentice/edit.do");

		result.addObject("message", messageCode);

		return result;

	}
}
