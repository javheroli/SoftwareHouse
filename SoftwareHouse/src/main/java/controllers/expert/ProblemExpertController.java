
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
import services.ProblemService;
import controllers.AbstractController;
import domain.Expert;
import domain.Problem;

@Controller
@RequestMapping("/problem/expert")
public class ProblemExpertController extends AbstractController {

	// Services

	@Autowired
	private ProblemService	problemService;

	@Autowired
	private ExpertService	expertService;


	// Constructors

	public ProblemExpertController() {
		super();
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int contestId) {
		final ModelAndView result;
		Problem problem;

		problem = this.problemService.create(contestId);

		result = this.createEditModelAndView(problem);

		return result;

	}

	// Edition
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int problemId) {
		ModelAndView result;
		Problem problem;

		problem = this.problemService.findOne(problemId);
		Assert.notNull(problem);

		result = this.createEditModelAndView(problem);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(Problem problem, final BindingResult binding) {
		ModelAndView result;

		problem = this.problemService.reconstruct(problem, binding);

		if (binding.hasErrors())
			result = this.createEditModelAndView(problem);
		else
			try {
				this.problemService.save(problem);
				result = new ModelAndView("redirect:/contest/actor/display.do?contestId=" + String.valueOf(problem.getContest().getId()));
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(problem, "problem.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(Problem problem, final BindingResult binding) {
		ModelAndView result;

		problem = this.problemService.reconstruct(problem, binding);

		try {
			this.problemService.delete(problem);
			result = new ModelAndView("redirect:/contest/actor/display.do?contestId=" + String.valueOf(problem.getContest().getId()));
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(problem, "problem.commit.error");
		}

		return result;
	}

	//Ancillary methods
	protected ModelAndView createEditModelAndView(final Problem problem) {
		ModelAndView result;

		result = this.createEditModelAndView(problem, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Problem problem, final String messageCode) {
		final ModelAndView result;
		boolean permission = false;
		Expert principal;

		principal = this.expertService.findByPrincipal();
		Assert.notNull(principal);

		if (problem.getContest().getIsDraft() && problem.getContest().getEditor().getId() == principal.getId())
			permission = true;

		result = new ModelAndView("problem/edit");
		result.addObject("problem", problem);
		result.addObject("permission", permission);

		result.addObject("message", messageCode);

		return result;

	}
}
