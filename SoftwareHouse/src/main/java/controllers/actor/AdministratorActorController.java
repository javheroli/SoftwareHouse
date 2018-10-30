
package controllers.actor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AdministratorService;
import controllers.AbstractController;
import domain.Administrator;

@Controller
@RequestMapping("/administrator/actor")
public class AdministratorActorController extends AbstractController {

	// Services

	@Autowired
	private AdministratorService	administratorService;


	public AdministratorActorController() {
		super();
	}

	//Displaying
	@RequestMapping(value = "/display")
	public ModelAndView display(@RequestParam final int administratorId) {
		final ModelAndView result;
		Administrator administrator;

		administrator = this.administratorService.findOne(administratorId);

		result = new ModelAndView("administrator/display");
		result.addObject("administrator", administrator);

		return result;

	}

}
