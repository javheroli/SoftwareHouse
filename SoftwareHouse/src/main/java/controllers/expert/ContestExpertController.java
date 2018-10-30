
package controllers.expert;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ContestService;
import services.ExpertService;
import controllers.AbstractController;
import domain.Contest;
import domain.Expert;

@Controller
@RequestMapping("/contest/expert")
public class ContestExpertController extends AbstractController {

	// Services

	@Autowired
	private ContestService	contestService;

	@Autowired
	private ExpertService	expertService;


	public ContestExpertController() {
		super();
	}

	//Listing
	@RequestMapping(value = "/listEditor", method = RequestMethod.GET)
	public ModelAndView listEditor() {
		final ModelAndView result;
		final Collection<Contest> contests;

		contests = this.contestService.findAllPrincipalChosenAsEditor();

		result = new ModelAndView("contest/list");
		result.addObject("contests", contests);
		result.addObject("requestURI", "contest/expert/listEditor.do");

		return result;

	}

	@RequestMapping(value = "/listJudge", method = RequestMethod.GET)
	public ModelAndView listJudge() {
		final ModelAndView result;
		final Collection<Contest> contests;
		final Expert principal;

		contests = this.contestService.findAllPendingToAssessPrincipalIsJudge();

		principal = this.expertService.findByPrincipal();

		result = new ModelAndView("contest/list");
		result.addObject("contests", contests);
		result.addObject("requestURI", "contest/expert/listJudge.do");
		result.addObject("principal", principal);

		return result;

	}
}
