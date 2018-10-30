
package controllers.expert;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import services.ExpertService;
import services.ResearchService;
import controllers.AbstractController;
import domain.Expert;
import domain.Research;

@Controller
@RequestMapping("/research/expert")
public class ResearchExpertController extends AbstractController {

	// Services

	@Autowired
	private ResearchService	researchService;

	@Autowired
	private ExpertService	expertService;


	// Constructors

	public ResearchExpertController() {
		super();
	}

	// Listing

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		final Collection<Research> researches;

		researches = this.researchService.findAllByExpert();

		result = new ModelAndView("research/list");
		result.addObject("researches", researches);
		result.addObject("requestURI", "research/expert/list.do");

		return result;
	}

	// Displaying
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int researchId) {
		final ModelAndView result;
		Research research;
		boolean isPrincipalMemberOfTeam;
		Expert principal;

		research = this.researchService.findOne(researchId);

		principal = this.expertService.findByPrincipal();
		isPrincipalMemberOfTeam = research.getTeam().contains(principal);

		result = new ModelAndView("research/display");
		result.addObject("research", research);
		result.addObject("isPrincipalMemberOfTeam", isPrincipalMemberOfTeam);

		return result;

	}

	// Creation 
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Research research;

		research = this.researchService.create();
		result = this.createEditModelAndView(research, true);
		return result;
	}

	// Edition ----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int researchId, @RequestParam final boolean listResearch) {
		ModelAndView result;
		Research research;

		research = this.researchService.findOne(researchId);
		Assert.notNull(research);

		result = this.createEditModelAndView(research, listResearch);

		return result;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "saveFromList")
	public ModelAndView saveFromList(Research research, final BindingResult binding) {
		ModelAndView result;

		research = this.researchService.reconstruct(research, binding);
		if (binding.hasErrors())
			result = this.createEditModelAndView(research, true);
		else
			try {
				this.researchService.save(research);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(research, "research.commit.error", true);
			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "saveFromDisplay")
	public ModelAndView saveFromDisplay(Research research, final BindingResult binding) {
		ModelAndView result;

		research = this.researchService.reconstruct(research, binding);
		if (binding.hasErrors())
			result = this.createEditModelAndView(research, false);
		else
			try {
				this.researchService.save(research);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(research, "research.commit.error", false);
			}

		return result;
	}

	// Cancelling
	@RequestMapping(value = "/cancel", method = RequestMethod.GET)
	public ModelAndView cancel(@RequestParam final int researchId, final RedirectAttributes redirect) {
		ModelAndView result;

		try {
			this.researchService.cancel(researchId);
			result = new ModelAndView("redirect:display.do?researchId=".concat(String.valueOf(researchId)));
			redirect.addFlashAttribute("cancelSuccess", true);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:display.do?researchId=".concat(String.valueOf(researchId)));
			redirect.addFlashAttribute("cancelFail", true);
		}

		return result;
	}

	// Finalizing
	@RequestMapping(value = "/finalize", method = RequestMethod.GET)
	public ModelAndView finalize(@RequestParam final int researchId, final RedirectAttributes redirect) {
		ModelAndView result;

		try {
			this.researchService.finalize(researchId);
			result = new ModelAndView("redirect:display.do?researchId=".concat(String.valueOf(researchId)));
			redirect.addFlashAttribute("finalizeSuccess", true);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:display.do?researchId=".concat(String.valueOf(researchId)));
			redirect.addFlashAttribute("finalizeFail", true);
		}

		return result;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Research research, final boolean fromList) {
		ModelAndView result;

		result = this.createEditModelAndView(research, null, fromList);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Research research, final String message, final boolean fromList) {
		final ModelAndView result;
		boolean permission;
		Expert principal;
		Collection<Expert> experts;
		String redirectURI, save;

		principal = this.expertService.findByPrincipal();
		Assert.notNull(principal);

		if (research.getId() == 0)
			permission = true;
		else
			permission = research.getTeam().contains(principal);

		experts = this.expertService.findAll();
		experts.remove(principal);

		if (fromList) {
			redirectURI = "research/expert/list.do";
			save = "saveFromList";
		} else {
			redirectURI = "research/expert/display.do?researchId=".concat(String.valueOf(research.getId()));
			save = "saveFromDisplay";
		}
		result = new ModelAndView("research/edit");
		result.addObject("research", research);
		result.addObject("experts", experts);
		result.addObject("redirectURI", redirectURI);
		result.addObject("save", save);
		result.addObject("message", message);
		result.addObject("permission", permission);

		return result;
	}

}
