
package controllers.actor;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.DisciplineService;
import services.ForumService;
import services.ThreadService;
import controllers.AbstractController;
import domain.Discipline;
import domain.Forum;

@Controller
@RequestMapping("/thread/actor")
public class ThreadActorController extends AbstractController {

	// Services

	@Autowired
	private ThreadService		threadService;

	@Autowired
	private ForumService		forumService;

	@Autowired
	private DisciplineService	disciplineService;


	// Constructors

	public ThreadActorController() {
		super();
	}

	// Creation 
	@RequestMapping(value = "/create")
	public ModelAndView create(@RequestParam final int forumId) {
		ModelAndView result;
		domain.Thread thread;
		Forum forum;

		forum = this.forumService.findOne(forumId);

		thread = this.threadService.create();
		thread.setForum(forum);

		result = this.createEditModelAndView(thread);
		return result;
	}

	// Edition ----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int threadId) {
		ModelAndView result;
		domain.Thread thread;

		thread = this.threadService.findOne(threadId);
		Assert.notNull(thread);

		result = this.createEditModelAndView(thread);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public ModelAndView save(domain.Thread thread, final BindingResult binding) {
		ModelAndView result;
		domain.Thread saved;
		thread = this.threadService.reconstruct(thread, binding);

		if (binding.hasErrors())
			result = this.createEditModelAndView(thread);
		else
			try {
				saved = this.threadService.save(thread);
				result = new ModelAndView("redirect:/post/list.do?threadId=".concat(String.valueOf(saved.getId())));

			} catch (final Throwable oops) {
				result = this.createEditModelAndView(thread, "thread.commit.error");
			}

		return result;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final domain.Thread thread) {
		ModelAndView result;

		result = this.createEditModelAndView(thread, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final domain.Thread thread, final String message) {
		final ModelAndView result;

		result = new ModelAndView("thread/edit");
		result.addObject("thread", thread);
		if (thread.getId() == 0) {
			Collection<Discipline> disciplines;
			disciplines = this.disciplineService.findAll();
			result.addObject("disciplines", disciplines);
		}

		result.addObject("message", message);

		return result;
	}

}
