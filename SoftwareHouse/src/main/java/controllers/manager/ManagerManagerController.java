
package controllers.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ManagerService;
import controllers.AbstractController;
import domain.Manager;

@Controller
@RequestMapping("/manager/manager")
public class ManagerManagerController extends AbstractController {

	// Services

	@Autowired
	private ManagerService	managerService;


	// Constructors

	public ManagerManagerController() {
		super();
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int managerId) {
		ModelAndView result;
		Manager manager;

		manager = this.managerService.findOne(managerId);
		Assert.notNull(manager);

		result = this.createEditModelAndView(manager);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public ModelAndView save(Manager manager, final BindingResult binding) {
		ModelAndView result;

		manager = this.managerService.reconstruct(manager, binding);
		if (binding.hasErrors())
			result = this.createEditModelAndView(manager);
		else
			try {
				this.managerService.save(manager);

				result = new ModelAndView("redirect:/manager/actor/display.do?managerId=".concat(String.valueOf(manager.getId())));

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
		Boolean permission;
		Manager principal;

		principal = this.managerService.findByPrincipal();

		permission = false;
		if (principal.getId() == manager.getId())
			permission = true;

		result = new ModelAndView("manager/edit");

		result.addObject("actionURI", "manager/manager/edit.do");
		result.addObject("redirectURI", "manager/actor/display.do?managerId=".concat(String.valueOf(manager.getId())));
		result.addObject("manager", manager);
		result.addObject("permission", permission);
		result.addObject("message", message);

		return result;
	}

}
