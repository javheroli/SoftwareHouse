
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ForumService;
import services.ThreadService;
import domain.Forum;

@Controller
@RequestMapping("/forum")
public class ForumController extends AbstractController {

	// Services

	@Autowired
	private ForumService	forumService;

	@Autowired
	private ThreadService	threadService;


	// Constructors

	public ForumController() {
		super();
	}

	// Listing
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final Collection<Forum> forums;

		forums = this.forumService.findAll();

		result = new ModelAndView("forum/list");
		result.addObject("forums", forums);

		return result;

	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int forumId) {
		final ModelAndView result;
		final Collection<domain.Thread> threads;
		Forum forum;
		String name, description;

		forum = this.forumService.findOne(forumId);
		name = forum.getName();
		description = forum.getDescription();

		threads = this.threadService.loadThreadsByForum(forumId);

		result = new ModelAndView("forum/display");
		result.addObject("threads", threads);
		result.addObject("name", name);
		result.addObject("description", description);
		result.addObject("forumId", forumId);

		return result;

	}

}
