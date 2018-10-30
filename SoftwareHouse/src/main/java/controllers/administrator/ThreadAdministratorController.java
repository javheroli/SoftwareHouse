
package controllers.administrator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ForumService;
import services.ThreadService;
import controllers.AbstractController;
import domain.Forum;

@Controller
@RequestMapping("/thread/administrator")
public class ThreadAdministratorController extends AbstractController {

	// Services

	@Autowired
	private ThreadService	threadService;

	@Autowired
	private ForumService	forumService;


	// Constructors

	public ThreadAdministratorController() {
		super();
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int threadId) {
		ModelAndView result;
		domain.Thread thread;
		Forum forum;

		thread = this.threadService.findOne(threadId);
		forum = thread.getForum();

		this.threadService.delete(thread);
		this.forumService.updateLastPost(forum.getId());

		result = new ModelAndView("redirect:/forum/display.do?forumId=".concat(String.valueOf(forum.getId())));
		return result;
	}

}
