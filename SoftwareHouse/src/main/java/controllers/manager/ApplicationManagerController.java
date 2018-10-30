
package controllers.manager;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ApplicationService;
import controllers.AbstractController;
import domain.Application;

@Controller
@RequestMapping("/application/manager")
public class ApplicationManagerController extends AbstractController {

	// Services

	@Autowired
	private ApplicationService	applicationService;


	// Constructors

	public ApplicationManagerController() {
		super();
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final Collection<Application> applications;

		applications = this.applicationService.findByPrincipalManager();

		result = new ModelAndView("application/list");
		result.addObject("applications", applications);
		result.addObject("requestURI", "application/manager/list.do");

		return result;

	}

	@RequestMapping(value = "/list", method = RequestMethod.GET, params = {
		"applicationStatus"
	})
	public ModelAndView listGrouped(@RequestParam final int applicationStatus) {
		final ModelAndView result;
		final Collection<Application> applications;

		if (applicationStatus == 0)
			applications = this.applicationService.findByPrincipalManager();
		else if (applicationStatus == 1)
			applications = this.applicationService.findAcceptedByPrincipalManager();
		else if (applicationStatus == 2)
			applications = this.applicationService.findPendingByPrincipalManager();
		else if (applicationStatus == 3)
			applications = this.applicationService.findDueByPrincipalManager();
		else
			applications = this.applicationService.findRejectedByPrincipalManager();

		result = new ModelAndView("application/list");
		result.addObject("applications", applications);
		result.addObject("requestURI", "application/manager/list.do");
		result.addObject("applicationStatus", applicationStatus);

		return result;

	}

	@RequestMapping(value = "/approve", method = RequestMethod.GET)
	public ModelAndView approve(@RequestParam final int applicationId) {
		ModelAndView result;
		Application application;

		application = this.applicationService.findOne(applicationId);
		Assert.notNull(application);

		this.applicationService.approve(application);

		result = new ModelAndView("redirect:list.do");

		return result;

	}

	@RequestMapping(value = "/reject", method = RequestMethod.GET)
	public ModelAndView reject(@RequestParam final int applicationId) {
		ModelAndView result;
		Application application;

		application = this.applicationService.findOne(applicationId);
		Assert.notNull(application);

		this.applicationService.reject(application);

		result = new ModelAndView("redirect:list.do");

		return result;

	}
	// Ancillary methods ------------------------------------------------------

}
