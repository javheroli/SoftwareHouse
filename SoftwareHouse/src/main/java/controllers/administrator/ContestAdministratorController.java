
package controllers.administrator;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ContestService;
import controllers.AbstractController;
import domain.Contest;

@Controller
@RequestMapping("/contest/administrator")
public class ContestAdministratorController extends AbstractController {

	// Services

	@Autowired
	private ContestService	contestService;


	public ContestAdministratorController() {
		super();
	}

	//Listing
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final Collection<Contest> contests;

		contests = this.contestService.findAllForAdmin();

		result = new ModelAndView("contest/list");
		result.addObject("contests", contests);
		result.addObject("requestURI", "contest/administrator/list.do");

		return result;

	}

}
