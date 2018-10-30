
package controllers.investor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.InvestorService;
import controllers.AbstractController;
import domain.Investor;

@Controller
@RequestMapping("/investor/investor")
public class InvestorInvestorController extends AbstractController {

	// Services

	@Autowired
	private InvestorService	investorService;


	// Constructors

	public InvestorInvestorController() {
		super();
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int investorId) {
		ModelAndView result;
		Investor investor;

		investor = this.investorService.findOne(investorId);
		Assert.notNull(investor);

		result = this.createEditModelAndView(investor);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public ModelAndView save(Investor investor, final BindingResult binding) {
		ModelAndView result;

		investor = this.investorService.reconstruct(investor, binding);
		if (binding.hasErrors())
			result = this.createEditModelAndView(investor);
		else
			try {

				this.investorService.save(investor);

				result = new ModelAndView("redirect:/investor/actor/display.do?investorId=".concat(String.valueOf(investor.getId())));

			} catch (final Throwable oops) {
				result = this.createEditModelAndView(investor, "investor.commit.error");

			}
		return result;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Investor investor) {
		ModelAndView result;

		result = this.createEditModelAndView(investor, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Investor investor, final String message) {
		ModelAndView result;
		Boolean permission;
		Investor principal;

		principal = this.investorService.findByPrincipal();

		permission = false;
		if (principal.getId() == investor.getId())
			permission = true;

		result = new ModelAndView("investor/edit");

		result.addObject("investor", investor);
		result.addObject("permission", permission);
		result.addObject("editPersonalData", true);
		result.addObject("message", message);

		return result;
	}

}
