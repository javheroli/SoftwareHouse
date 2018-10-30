
package controllers.apprentice;

import java.util.Calendar;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ApplicationService;
import services.ApprenticeService;
import controllers.AbstractController;
import domain.Application;
import domain.Apprentice;

@Controller
@RequestMapping("/application/apprentice")
public class ApplicationApprenticeController extends AbstractController {

	// Services

	@Autowired
	private ApprenticeService	apprenticeService;

	@Autowired
	private ApplicationService	applicationService;


	// Constructors

	public ApplicationApprenticeController() {
		super();
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final Collection<Application> applications;

		applications = this.applicationService.findByPrincipalApprentice();

		result = new ModelAndView("application/list");
		result.addObject("applications", applications);
		result.addObject("requestURI", "application/apprentice/list.do");
		result.addObject("groupedURI", "application/apprentice/listGrouped.do");

		return result;

	}

	@RequestMapping(value = "/list", method = RequestMethod.GET, params = {
		"applicationStatus"
	})
	public ModelAndView listGrouped(@RequestParam final int applicationStatus) {
		final ModelAndView result;
		final Collection<Application> applications;

		if (applicationStatus == 0)
			applications = this.applicationService.findByPrincipalApprentice();
		else if (applicationStatus == 1)
			applications = this.applicationService.findAcceptedByPrincipalApprentice();
		else if (applicationStatus == 2)
			applications = this.applicationService.findPendingByPrincipalApprentice();
		else if (applicationStatus == 3)
			applications = this.applicationService.findDueByPrincipalApprentice();
		else
			applications = this.applicationService.findRejectedByPrincipalApprentice();

		result = new ModelAndView("application/list");
		result.addObject("applications", applications);
		result.addObject("requestURI", "application/apprentice/list.do");
		result.addObject("applicationStatus", applicationStatus);

		return result;

	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int contestId) {
		final ModelAndView result;
		Application application;

		application = this.applicationService.create(contestId);

		result = this.createEditModelAndView(application);

		return result;

	}

	// Edition
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int applicationId) {
		ModelAndView result;
		Application application;

		application = this.applicationService.findOne(applicationId);
		Assert.notNull(application);

		result = this.createEditModelAndView(application);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(Application application, final BindingResult binding) {
		ModelAndView result;

		application = this.applicationService.reconstruct(application, binding);

		if (binding.hasErrors())
			result = this.createEditModelAndView(application);
		else
			try {
				if (application.getId() == 0)
					this.applicationService.save(application);
				else
					this.applicationService.accept(application);

				result = new ModelAndView("redirect:/application/apprentice/list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(application, "application.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/confirm", method = RequestMethod.GET)
	public ModelAndView confirm(@RequestParam final int applicationId) {
		ModelAndView result;
		Application application;

		application = this.applicationService.findOne(applicationId);
		Assert.notNull(application);

		this.applicationService.accept(application);

		result = new ModelAndView("redirect:list.do");

		return result;

	}

	// Ancillary methods ------------------------------------------------------
	protected ModelAndView createEditModelAndView(final Application application) {
		ModelAndView result;

		result = this.createEditModelAndView(application, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Application application, final String messageCode) {
		final ModelAndView result;
		boolean permission = false;
		boolean hasApplication;
		boolean hasEnoughPoints = false;
		Apprentice principal;
		Calendar calendar;

		principal = this.apprenticeService.findByPrincipal();
		Assert.notNull(principal);

		hasApplication = this.applicationService.hasPrincipalApplicationForContest(application.getContest().getId());

		if (application.getId() == 0 && !hasApplication)
			permission = true;
		else if (principal.getId() == application.getApplicant().getId())
			permission = true;

		if (principal.getPoints() >= application.getContest().getRequiredPoints())
			hasEnoughPoints = true;

		calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);

		year = year % 100;
		month = month + 1;

		result = new ModelAndView("application/edit");
		result.addObject("application", application);
		result.addObject("permission", permission);
		result.addObject("hasApplication", hasApplication);
		result.addObject("hasEnoughPoints", hasEnoughPoints);
		result.addObject("year", year);
		result.addObject("month", month);

		result.addObject("message", messageCode);

		return result;

	}
}
