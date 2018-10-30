
package controllers.administrator;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import controllers.AbstractController;
import domain.Actor;

@Controller
@RequestMapping("/actor/administrator")
public class ActorAdministratorController extends AbstractController {

	// Services

	@Autowired
	private ActorService	actorService;


	public ActorAdministratorController() {
		super();
	}

	// Listing
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final Collection<Actor> actors;
		String requestURI;

		actors = this.actorService.findAll();

		requestURI = "actor/administrator/list.do";

		result = new ModelAndView("actor/list");
		result.addObject("actors", actors);
		result.addObject("requestURI", requestURI);

		return result;

	}
}
