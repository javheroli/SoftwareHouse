
package controllers.administrator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ForumService;
import controllers.AbstractController;
import domain.Forum;

@Controller
@RequestMapping("/forum/administrator")
public class ForumAdministratorController extends AbstractController {

	// Services

	@Autowired
	private ForumService	forumService;


	// Constructors

	public ForumAdministratorController() {
		super();
	}

	// Creation 
	@RequestMapping(value = "/create")
	public ModelAndView create() {
		ModelAndView result;
		Forum forum;

		forum = this.forumService.create();

		result = this.createEditModelAndView(forum);
		return result;
	}

	// Edition ----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int forumId) {
		ModelAndView result;
		Forum forum;

		forum = this.forumService.findOne(forumId);
		Assert.notNull(forum);

		result = this.createEditModelAndView(forum);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(Forum forum, final BindingResult binding) {
		ModelAndView result;
		forum = this.forumService.reconstruct(forum, binding);

		if (binding.hasErrors())
			result = this.createEditModelAndView(forum);
		else
			try {
				this.forumService.save(forum);
				result = new ModelAndView("redirect:/forum/list.do");

			} catch (final Throwable oops) {
				result = this.createEditModelAndView(forum, "forum.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(Forum forum, final BindingResult binding) {
		ModelAndView result;

		forum = this.forumService.reconstruct(forum, binding);

		try {
			this.forumService.delete(forum);
			result = new ModelAndView("redirect:/forum/list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(forum, "forum.commit.error");
		}

		return result;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Forum forum) {
		ModelAndView result;

		result = this.createEditModelAndView(forum, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Forum forum, final String message) {
		final ModelAndView result;

		result = new ModelAndView("forum/edit");
		result.addObject("forum", forum);

		result.addObject("message", message);

		return result;
	}

}
