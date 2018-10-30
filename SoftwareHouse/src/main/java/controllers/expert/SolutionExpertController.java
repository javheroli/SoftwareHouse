
package controllers.expert;

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

import services.ContestService;
import services.ExpertService;
import services.SolutionService;
import controllers.AbstractController;
import domain.Answer;
import domain.Contest;
import domain.Expert;
import domain.Solution;

@Controller
@RequestMapping("/solution/expert")
public class SolutionExpertController extends AbstractController {

	// Services

	@Autowired
	private SolutionService	solutionService;

	@Autowired
	private ContestService	contestService;

	@Autowired
	private ExpertService	expertService;


	public SolutionExpertController() {
		super();
	}

	//Listing
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int contestId) {
		final ModelAndView result;
		final Collection<Solution> solutions;
		final Contest contest;
		Expert principal;
		boolean permission = false;

		principal = this.expertService.findByPrincipal();
		Assert.notNull(principal);

		contest = this.contestService.findOne(contestId);
		if (principal.getContestsForEvaluation().contains(contest))
			permission = true;

		solutions = this.solutionService.findAllByContest(contestId);

		result = new ModelAndView("solution/list");
		result.addObject("solutions", solutions);
		result.addObject("permission", permission);
		result.addObject("requestURI", "solution/expert/list.do?contestId=" + String.valueOf(contestId));

		return result;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int solutionId) {
		ModelAndView result;
		Solution solution;

		solution = this.solutionService.findOne(solutionId);
		Assert.notNull(solution);

		for (final Answer a : solution.getAnswers())
			a.setMark(0.);

		result = this.createEditModelAndView(solution);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "assess")
	public ModelAndView save(Solution solution, final BindingResult binding) {
		ModelAndView result;

		solution = this.solutionService.reconstruct(solution, binding);

		if (binding.hasErrors())
			result = this.createEditModelAndView(solution);
		else
			try {
				this.solutionService.save(solution);
				result = new ModelAndView("redirect:/solution/expert/list.do?contestId=" + String.valueOf(solution.getApplication().getContest().getId()));
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
		Expert principal;

		principal = this.expertService.findByPrincipal();
		Assert.notNull(principal);

		if (solution.getMark() == null && solution.getApplication().getContest().getJudges().contains(principal) && solution.getApplication().getContest().getStartMoment().before(new Date(System.currentTimeMillis()))
			&& solution.getApplication().getContest().getEndMoment().before(new Date(System.currentTimeMillis())))
			permission = true;

		result = new ModelAndView("solution/edit");
		result.addObject("solution", solution);
		result.addObject("permission", permission);
		result.addObject("requestURI", "solution/expert/edit.do");

		result.addObject("message", messageCode);

		return result;

	}
}
