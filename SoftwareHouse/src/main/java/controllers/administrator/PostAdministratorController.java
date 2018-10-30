
package controllers.administrator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.PostService;
import controllers.AbstractController;
import domain.Post;

@Controller
@RequestMapping("/post/administrator")
public class PostAdministratorController extends AbstractController {

	// Services

	@Autowired
	private PostService	postService;


	// Constructors

	public PostAdministratorController() {
		super();
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int postId) {
		ModelAndView result;
		Post post;

		post = this.postService.findOne(postId);

		try {
			this.postService.adminDelete(postId);
		} catch (final Throwable oops) {
		}

		result = new ModelAndView("redirect:/post/list.do?threadId=".concat(String.valueOf(post.getTopic().getId())));
		return result;
	}

}
