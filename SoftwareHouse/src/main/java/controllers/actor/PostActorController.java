
package controllers.actor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.PostService;
import services.ThreadService;
import controllers.AbstractController;
import domain.Post;

@Controller
@RequestMapping("/post/actor")
public class PostActorController extends AbstractController {

	// Services

	@Autowired
	private PostService		postService;

	@Autowired
	private ThreadService	threadService;


	// Constructors

	public PostActorController() {
		super();
	}

	// Creation 
	@RequestMapping(value = "/create", method = RequestMethod.GET, params = {
		"threadId"
	})
	public ModelAndView createToThread(@RequestParam final int threadId) {
		ModelAndView result;
		Post post;
		domain.Thread thread;

		thread = this.threadService.findOne(threadId);

		post = this.postService.create();
		post.setThread(thread);
		post.setTopic(thread);
		post.setParentPost(null);

		result = this.createEditModelAndView(post);
		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET, params = {
		"postId"
	})
	public ModelAndView createToPost(@RequestParam final int postId) {
		ModelAndView result;
		Post post, parentPost;

		parentPost = this.postService.findOne(postId);

		post = this.postService.create();
		post.setParentPost(parentPost);
		post.setTopic(parentPost.getTopic());
		post.setThread(null);
		result = this.createEditModelAndView(post);
		return result;
	}

	// Edition ----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int postId) {
		ModelAndView result;
		Post post;

		post = this.postService.findOne(postId);
		Assert.notNull(post);

		result = this.createEditModelAndView(post);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(Post post, final BindingResult binding) {
		ModelAndView result;
		post = this.postService.reconstruct(post, binding);

		if (binding.hasErrors())
			result = this.createEditModelAndView(post);
		else
			try {
				this.postService.save(post);
				result = new ModelAndView("redirect:/post/list.do?threadId=".concat(String.valueOf(post.getTopic().getId())));

			} catch (final Throwable oops) {
				result = this.createEditModelAndView(post, "post.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(Post post, final BindingResult binding) {
		ModelAndView result;

		post = this.postService.reconstruct(post, binding);

		try {
			this.postService.userDelete(post.getId());
			result = new ModelAndView("redirect:/post/list.do?threadId=".concat(String.valueOf(post.getTopic().getId())));
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(post, "post.commit.error");
		}

		return result;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Post post) {
		ModelAndView result;

		result = this.createEditModelAndView(post, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Post post, final String message) {
		final ModelAndView result;

		result = new ModelAndView("post/edit");
		result.addObject("post", post);
		result.addObject("message", message);

		return result;
	}

}
