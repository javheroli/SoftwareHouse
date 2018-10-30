
package controllers.actor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.InvestmentService;
import services.InvestorService;
import controllers.AbstractController;
import domain.Investor;

@Controller
@RequestMapping("/investor/actor")
public class InvestorActorController extends AbstractController {

	// Services

	@Autowired
	private InvestorService		investorService;

	@Autowired
	private InvestmentService	investmentService;


	public InvestorActorController() {
		super();
	}

	//Displaying
	@RequestMapping(value = "/display")
	public ModelAndView display(@RequestParam final int investorId) {
		final ModelAndView result;
		Investor investor;
		Double totalAmountInvested;

		investor = this.investorService.findOne(investorId);

		totalAmountInvested = this.investmentService.getTotalAmountInvestedByInvestor(investorId);

		result = new ModelAndView("investor/display");
		result.addObject("investor", investor);
		result.addObject("totalAmountInvested", totalAmountInvested);

		return result;

	}

}
