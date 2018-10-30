
package controllers.apprentice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import services.PostService;
import controllers.AbstractController;
import domain.Post;

@Controller
@RequestMapping("/post/apprentice")
public class PostApprenticeController extends AbstractController {

	// Services

	@Autowired
	private PostService	postService;


	// Constructors

	public PostApprenticeController() {
		super();
	}

	@RequestMapping(value = "/mark", method = RequestMethod.GET)
	public ModelAndView mark(@RequestParam final int postId, final RedirectAttributes redirect) {
		ModelAndView result;
		Post post;

		post = this.postService.findOne(postId);

		result = new ModelAndView("redirect:/post/list.do?threadId=".concat(String.valueOf(post.getTopic().getId())));

		try {
			this.postService.mark(postId);
			redirect.addFlashAttribute("markAsBestAnswerSuccess", true);
		} catch (final Throwable oops) {
			redirect.addFlashAttribute("markAsBestAnswerFail", true);
		}

		return result;
	}

}
