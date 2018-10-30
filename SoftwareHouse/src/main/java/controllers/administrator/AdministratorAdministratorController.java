
package controllers.administrator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AdministratorService;
import controllers.AbstractController;
import domain.Administrator;

@Controller
@RequestMapping("/administrator/administrator")
public class AdministratorAdministratorController extends AbstractController {

	// Services

	@Autowired
	private AdministratorService	administratorService;


	// Constructors

	public AdministratorAdministratorController() {
		super();
	}

	// Creation ---------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Administrator administrator;

		administrator = this.administratorService.create();
		result = this.createEditModelAndView(administrator);
		result.addObject("permission", true);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int administratorId) {
		ModelAndView result;
		Administrator administrator;

		administrator = this.administratorService.findOne(administratorId);
		Assert.notNull(administrator);

		result = this.createEditModelAndView(administrator);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public ModelAndView save(Administrator administrator, final BindingResult binding) {
		ModelAndView result;

		administrator = this.administratorService.reconstruct(administrator, binding);
		if (binding.hasErrors()) {
			result = this.createEditModelAndView(administrator);
			result.addObject("permission", true);
		} else
			try {

				Administrator principal, saved;
				principal = this.administratorService.findByPrincipal();
				saved = this.administratorService.save(administrator);
				if (principal.getId() == saved.getId())
					result = new ModelAndView("redirect:/administrator/actor/display.do?administratorId=".concat(String.valueOf(principal.getId())));

				else
					result = new ModelAndView("redirect:/administrator/actor/display.do?administratorId=".concat(String.valueOf(saved.getId())));

			} catch (final Throwable oops) {
				result = this.createEditModelAndView(administrator, "administrator.commit.error");

			}
		return result;
	}
	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Administrator administrator) {
		ModelAndView result;

		result = this.createEditModelAndView(administrator, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Administrator administrator, final String message) {
		ModelAndView result;
		Boolean permission;
		Administrator principal;
		String redirectURI;

		permission = false;

		principal = this.administratorService.findByPrincipal();

		if (principal.getId() == administrator.getId())
			permission = true;

		if (administrator.getId() == 0)
			redirectURI = "welcome/index.do";
		else
			redirectURI = "administrator/actor/display.do?administratorId=".concat(String.valueOf(administrator.getId()));

		result = new ModelAndView("administrator/edit");

		result.addObject("administrator", administrator);
		result.addObject("actionURI", "administrator/administrator/edit.do");
		result.addObject("redirectURI", redirectURI);
		result.addObject("message", message);
		result.addObject("permission", permission);

		return result;
	}

}
