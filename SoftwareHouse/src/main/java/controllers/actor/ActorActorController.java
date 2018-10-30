
package controllers.actor;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import controllers.AbstractController;
import domain.Actor;
import domain.Administrator;
import domain.Apprentice;
import domain.Expert;
import domain.Investor;
import domain.Manager;

@Controller
@RequestMapping("/actor/actor")
public class ActorActorController extends AbstractController {

	// Services

	@Autowired
	private ActorService	actorService;


	public ActorActorController() {
		super();
	}

	// Listing
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final Collection<Actor> actors;
		String requestURI;

		actors = this.actorService.findAllButAdmins();

		requestURI = "actor/actor/list.do";

		result = new ModelAndView("actor/list");
		result.addObject("actors", actors);
		result.addObject("requestURI", requestURI);

		return result;

	}

	//Displaying
	@RequestMapping(value = "/display")
	public ModelAndView display() {
		final ModelAndView result;
		Actor principal;

		principal = this.actorService.findByPrincipal();

		result = new ModelAndView("redirect:redirect.do?actorId=".concat(String.valueOf(principal.getId())));
		return result;

	}

	@RequestMapping(value = "/redirect")
	public ModelAndView redirect(@RequestParam final int actorId) {
		ModelAndView result;
		Actor actor;

		actor = null;

		actor = this.actorService.findOne(actorId);
		Assert.notNull(actor);

		if (actor instanceof Apprentice) {
			Apprentice apprentice;
			apprentice = (Apprentice) actor;
			result = new ModelAndView("redirect:/apprentice/actor/display.do?apprenticeId=".concat(String.valueOf(apprentice.getId())));
		} else if (actor instanceof Manager) {
			Manager manager;
			manager = (Manager) actor;
			result = new ModelAndView("redirect:/manager/actor/display.do?managerId=".concat(String.valueOf(manager.getId())));

		} else if (actor instanceof Expert) {
			Expert expert;
			expert = (Expert) actor;
			result = new ModelAndView("redirect:/expert/actor/display.do?expertId=".concat(String.valueOf(expert.getId())));

		} else if (actor instanceof Investor) {
			Investor investor;
			investor = (Investor) actor;
			result = new ModelAndView("redirect:/investor/actor/display.do?investorId=".concat(String.valueOf(investor.getId())));

		} else {
			Administrator administrator;
			administrator = (Administrator) actor;
			result = new ModelAndView("redirect:/administrator/actor/display.do?administratorId=".concat(String.valueOf(administrator.getId())));
		}

		return result;

	}
}
