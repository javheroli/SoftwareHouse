
package controllers.actor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ApprenticeService;
import controllers.AbstractController;
import domain.Apprentice;

@Controller
@RequestMapping("/apprentice/actor")
public class ApprenticeActorController extends AbstractController {

	// Services

	@Autowired
	private ApprenticeService	apprenticeService;


	public ApprenticeActorController() {
		super();
	}

	//Displaying
	@RequestMapping(value = "/display")
	public ModelAndView display(@RequestParam final int apprenticeId) {
		final ModelAndView result;
		Apprentice apprentice;

		apprentice = this.apprenticeService.findOne(apprenticeId);

		result = new ModelAndView("apprentice/display");
		result.addObject("apprentice", apprentice);

		return result;

	}

}
