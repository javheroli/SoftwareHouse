
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.PostService;
import services.ThreadService;
import domain.Post;

@Controller
@RequestMapping("/post")
public class PostController extends AbstractController {

	// Services

	@Autowired
	private PostService		postService;

	@Autowired
	private ThreadService	threadService;


	// Constructors

	public PostController() {
		super();
	}

	// Listing
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int threadId) {
		final ModelAndView result;
		final Collection<Post> posts;
		domain.Thread thread;

		posts = this.postService.loadPostsByThread(threadId);
		thread = this.threadService.findOne(threadId);

		result = new ModelAndView("post/list");
		result.addObject("posts", posts);
		result.addObject("thread", thread);

		return result;

	}

}
