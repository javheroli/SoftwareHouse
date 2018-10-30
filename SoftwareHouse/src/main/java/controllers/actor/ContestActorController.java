
package controllers.actor;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.ApplicationService;
import services.ContestService;
import services.SolutionService;
import controllers.AbstractController;
import domain.Actor;
import domain.Administrator;
import domain.Application;
import domain.Apprentice;
import domain.Contest;
import domain.Expert;
import domain.Manager;
import domain.Solution;

@Controller
@RequestMapping("/contest/actor")
public class ContestActorController extends AbstractController {

	// Services

	@Autowired
	private ContestService		contestService;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private ApplicationService	applicationService;

	@Autowired
	private SolutionService		solutionService;


	public ContestActorController() {
		super();
	}

	//Listing
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final Collection<Contest> contests;
		Actor principal;

		contests = this.contestService.findAllPublished();

		principal = this.actorService.findByPrincipal();

		result = new ModelAndView("contest/list");
		result.addObject("contests", contests);
		result.addObject("principal", principal);
		result.addObject("requestURI", "contest/actor/list.do");

		return result;

	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int contestId) {
		final ModelAndView result;
		Contest contest;
		Collection<Apprentice> winners = new ArrayList<Apprentice>();
		boolean permission = true;
		boolean isEditor = false;
		boolean isOwner = false;
		boolean isAdmin = false;
		boolean hasApplication = false;
		Application solicitud = null;
		Solution solution = null;
		Actor principal;

		principal = this.actorService.findByPrincipal();

		contest = this.contestService.findOne(contestId);
		Assert.notNull(contest);

		if (contest.getIsDraft() && principal instanceof Apprentice)
			permission = false;
		else if (contest.getIsDraft() && principal instanceof Expert && contest.getEditor().getId() != principal.getId())
			permission = false;
		else if (contest.getIsDraft() && principal instanceof Manager && contest.getManager().getId() != principal.getId())
			permission = false;

		if (this.contestService.hasContestWinner(contestId))
			winners = this.contestService.findWinnersByContest(contestId);

		if (principal instanceof Expert && contest.getEditor().getId() == principal.getId())
			isEditor = true;

		if (principal instanceof Manager && contest.getManager().getId() == principal.getId())
			isOwner = true;

		if (principal instanceof Apprentice) {
			hasApplication = this.applicationService.hasPrincipalApplicationForContest(contestId);
			if (hasApplication) {
				solicitud = this.applicationService.findByPrincipalApprenticeAndByContest(contest.getId());
				if (solicitud.getStatus().equals("ACCEPTED"))
					solution = this.solutionService.findByApprenticePrincipalAndContest(contest.getId());
			}
		}
		if (principal instanceof Administrator)
			isAdmin = true;

		result = new ModelAndView("contest/display");
		result.addObject("contest", contest);
		result.addObject("permission", permission);
		result.addObject("winners", winners);
		result.addObject("winnersSize", winners.size());
		result.addObject("isEditor", isEditor);
		result.addObject("isAdmin", isAdmin);
		result.addObject("isOwner", isOwner);
		result.addObject("hasApplication", hasApplication);
		result.addObject("solicitud", solicitud);
		result.addObject("solution", solution);

		return result;

	}
}
