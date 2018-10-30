
package controllers.actor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ExpertService;
import controllers.AbstractController;
import domain.Expert;

@Controller
@RequestMapping("/expert/actor")
public class ExpertActorController extends AbstractController {

	// Services

	@Autowired
	private ExpertService	expertService;


	public ExpertActorController() {
		super();
	}

	//Displaying
	@RequestMapping(value = "/display")
	public ModelAndView display(@RequestParam final int expertId) {
		final ModelAndView result;
		Expert expert;

		expert = this.expertService.findOne(expertId);

		result = new ModelAndView("expert/display");
		result.addObject("expert", expert);

		return result;

	}

}
