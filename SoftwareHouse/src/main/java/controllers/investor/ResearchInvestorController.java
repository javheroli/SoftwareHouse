
package controllers.investor;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ResearchService;
import controllers.AbstractController;
import domain.Research;

@Controller
@RequestMapping("/research/investor")
public class ResearchInvestorController extends AbstractController {

	// Services

	@Autowired
	private ResearchService	researchService;


	// Constructors

	public ResearchInvestorController() {
		super();
	}

	// Listing

	@RequestMapping(value = "/listAll", method = RequestMethod.GET)
	public ModelAndView listAll() {
		ModelAndView result;
		final Collection<Research> researches;

		researches = this.researchService.findAll();

		result = new ModelAndView("research/list");
		result.addObject("researches", researches);
		result.addObject("requestURI", "research/investor/listAll.do");

		return result;
	}

	@RequestMapping(value = "/listForFunding", method = RequestMethod.GET)
	public ModelAndView listForFunding() {
		ModelAndView result;
		final Collection<Research> researches;

		researches = this.researchService.findAllThatNeedFunding();

		result = new ModelAndView("research/list");
		result.addObject("researches", researches);
		result.addObject("requestURI", "research/investor/listForFunding.do");

		return result;
	}

	// Displaying
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int researchId) {
		final ModelAndView result;
		Research research;

		research = this.researchService.findOne(researchId);

		result = new ModelAndView("research/display");

		result.addObject("research", research);

		return result;

	}

}
