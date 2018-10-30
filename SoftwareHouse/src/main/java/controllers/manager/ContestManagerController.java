
package controllers.manager;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ContestService;
import services.DisciplineService;
import services.ManagerService;
import controllers.AbstractController;
import domain.Contest;
import domain.Discipline;
import domain.Expert;
import domain.Manager;

@Controller
@RequestMapping("/contest/manager")
public class ContestManagerController extends AbstractController {

	// Services

	@Autowired
	private ContestService		contestService;

	@Autowired
	private ManagerService		managerService;

	@Autowired
	private DisciplineService	disciplineService;


	public ContestManagerController() {
		super();
	}

	//Listing
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final Collection<Contest> contests;

		contests = this.contestService.findAllCreatedByPrincipalManager();

		result = new ModelAndView("contest/list");
		result.addObject("contests", contests);
		result.addObject("requestURI", "contest/actor/list.do");

		return result;

	}
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int contestId) {
		ModelAndView result;
		Contest contest;

		contest = this.contestService.findOne(contestId);
		Assert.notNull(contest);

		result = this.createEditModelAndView(contest);

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;
		Contest contest;

		contest = this.contestService.create();

		result = this.createEditModelAndView(contest);

		return result;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "saveDraft")
	public ModelAndView saveDraft(Contest contest, final BindingResult binding) {
		ModelAndView result;
		Contest saved;

		contest = this.contestService.reconstruct(contest, binding);

		if (binding.hasErrors())
			result = this.createEditModelAndView(contest);
		else
			try {
				saved = this.contestService.save(contest, true);
				result = new ModelAndView("redirect:/contest/actor/display.do?contestId=" + String.valueOf(saved.getId()));
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(contest, "contest.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "saveFinal")
	public ModelAndView saveFinal(Contest contest, final BindingResult binding) {
		ModelAndView result;
		Contest saved;

		contest = this.contestService.reconstruct(contest, binding);

		if (binding.hasErrors())
			result = this.createEditModelAndView(contest);
		else
			try {
				saved = this.contestService.save(contest, false);
				result = new ModelAndView("redirect:/contest/actor/display.do?contestId=" + String.valueOf(saved.getId()));
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(contest, "contest.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(Contest contest, final BindingResult binding) {
		ModelAndView result;

		contest = this.contestService.reconstruct(contest, binding);

		try {
			this.contestService.delete(contest);
			result = new ModelAndView("redirect:/contest/manager/list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(contest, "contest.commit.error");
		}

		return result;
	}
	//Ancillary methods
	protected ModelAndView createEditModelAndView(final Contest contest) {
		ModelAndView result;

		result = this.createEditModelAndView(contest, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Contest contest, final String messageCode) {
		final ModelAndView result;
		boolean permission = false;
		Manager principal;
		Collection<Discipline> disciplines;
		Collection<Expert> experts;

		principal = this.managerService.findByPrincipal();
		Assert.notNull(principal);

		if (contest.getId() == 0 || (contest.getIsDraft() && principal.getContests().contains(contest)))
			permission = true;

		disciplines = this.disciplineService.findAll();

		experts = new ArrayList<Expert>();

		result = new ModelAndView("contest/edit");
		result.addObject("contest", contest);
		result.addObject("permission", permission);
		result.addObject("disciplines", disciplines);
		result.addObject("editors", experts);
		result.addObject("judges", experts);
		result.addObject("requestURI", "contest/manager/edit.do");

		result.addObject("message", messageCode);

		return result;

	}

}
