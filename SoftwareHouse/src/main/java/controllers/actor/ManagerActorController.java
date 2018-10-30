
package controllers.actor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ManagerService;
import controllers.AbstractController;
import domain.Manager;

@Controller
@RequestMapping("/manager/actor")
public class ManagerActorController extends AbstractController {

	// Services

	@Autowired
	private ManagerService	managerService;


	public ManagerActorController() {
		super();
	}

	//Displaying
	@RequestMapping(value = "/display")
	public ModelAndView display(@RequestParam final int managerId) {
		final ModelAndView result;
		Manager manager;

		manager = this.managerService.findOne(managerId);

		result = new ModelAndView("manager/display");
		result.addObject("manager", manager);

		return result;

	}

}
