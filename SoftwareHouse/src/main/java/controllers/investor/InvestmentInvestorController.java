
package controllers.investor;

import java.util.Calendar;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.InvestmentService;
import services.ResearchService;
import controllers.AbstractController;
import domain.Investment;
import domain.Research;

@Controller
@RequestMapping("/investment/investor")
public class InvestmentInvestorController extends AbstractController {

	// Services

	@Autowired
	private InvestmentService	investmentService;

	@Autowired
	private ResearchService		researchService;


	// Constructors

	public InvestmentInvestorController() {
		super();
	}

	// Listing

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		final Collection<Investment> investments;

		investments = this.investmentService.findAllByPrincipal();

		result = new ModelAndView("investment/list");
		result.addObject("investments", investments);

		return result;
	}

	// Creation ----------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int researchId, @RequestParam final String redirect) {
		ModelAndView result;
		Investment investment;
		Research research;

		research = this.researchService.findOne(researchId);
		Assert.notNull(research);

		investment = this.investmentService.create();
		investment.setResearch(research);

		result = this.createEditModelAndView(investment, redirect);

		return result;

	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = {
		"saveFromListForFunding"
	})
	public ModelAndView saveFromListForFunding(Investment investment, final BindingResult binding) {
		ModelAndView result;

		investment = this.investmentService.reconstruct(investment, binding);
		if (binding.hasErrors())
			result = this.createEditModelAndView(investment, "listForFunding");
		else
			try {
				this.investmentService.save(investment);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(investment, "investment.commit.error", "listForFunding");
			}

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = {
		"saveFromListAll"
	})
	public ModelAndView saveFromListAll(Investment investment, final BindingResult binding) {
		ModelAndView result;

		investment = this.investmentService.reconstruct(investment, binding);
		if (binding.hasErrors())
			result = this.createEditModelAndView(investment, "listAll");
		else
			try {
				this.investmentService.save(investment);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(investment, "investment.commit.error", "listAll");
			}

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = {
		"saveFromDisplay"
	})
	public ModelAndView saveFromDisplay(Investment investment, final BindingResult binding) {
		ModelAndView result;

		investment = this.investmentService.reconstruct(investment, binding);
		if (binding.hasErrors())
			result = this.createEditModelAndView(investment, "display");
		else
			try {
				this.investmentService.save(investment);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(investment, "investment.commit.error", "display");
			}

		return result;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Investment investment, final String redirect) {
		ModelAndView result;

		result = this.createEditModelAndView(investment, null, redirect);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Investment investment, final String message, final String redirect) {
		final ModelAndView result;
		Calendar calendar;
		String redirectURI, save;

		calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);

		year = year % 100;
		month = month + 1;

		if (redirect.equals("listForFunding")) {
			redirectURI = "research/investor/listForFunding.do";
			save = "saveFromListForFunding";
		} else if (redirect.equals("listAll")) {
			redirectURI = "research/investor/listAll.do";
			save = "saveFromListAll";
		} else {
			redirectURI = "research/investor/display.do?researchId=".concat(String.valueOf(investment.getResearch().getId()));
			save = "saveFromDisplay";
		}

		result = new ModelAndView("investment/edit");
		result.addObject("investment", investment);
		result.addObject("message", message);
		result.addObject("year", year);
		result.addObject("month", month);
		result.addObject("redirectURI", redirectURI);
		result.addObject("save", save);

		return result;
	}

}
