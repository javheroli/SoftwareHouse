
package controllers.administrator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ManagerService;
import controllers.AbstractController;
import domain.Manager;

@Controller
@RequestMapping("/manager/administrator")
public class ManagerAdministratorController extends AbstractController {

	// Services

	@Autowired
	private ManagerService	managerService;


	// Constructors

	public ManagerAdministratorController() {
		super();
	}

	// Creation ---------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Manager manager;

		manager = this.managerService.create();
		result = this.createEditModelAndView(manager);
		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ModelAndView save(Manager manager, final BindingResult binding) {
		ModelAndView result;

		manager = this.managerService.reconstruct(manager, binding);
		if (binding.hasErrors()) {
			result = this.createEditModelAndView(manager);
			result.addObject("permission", true);
		} else
			try {
				Manager saved;
				saved = this.managerService.save(manager);

				result = new ModelAndView("redirect:/manager/actor/display.do?managerId=".concat(String.valueOf(saved.getId())));
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(manager, "manager.commit.error");

			}
		return result;
	}
	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Manager manager) {
		ModelAndView result;

		result = this.createEditModelAndView(manager, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Manager manager, final String message) {
		ModelAndView result;

		result = new ModelAndView("manager/edit");

		result.addObject("manager", manager);
		result.addObject("actionURI", "manager/administrator/create.do");
		result.addObject("redirectURI", "welcome/index.do");
		result.addObject("message", message);
		result.addObject("permission", true);

		return result;
	}

}
