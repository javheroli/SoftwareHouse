
package controllers.manager;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import services.ExpertService;
import controllers.AbstractController;
import domain.Expert;

@Controller
@RequestMapping("/expert/manager")
public class ExpertManagerController extends AbstractController {

	// Services

	@Autowired
	private ExpertService	expertService;


	public ExpertManagerController() {
		super();
	}

	//Listing
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public @ResponseBody
	String list(@RequestParam final int disciplineId) {
		final Collection<Expert> experts;
		String result = "";

		experts = this.expertService.findAllByDiscipline(disciplineId);

		for (final Expert e : experts)
			result += String.valueOf(e.getId()) + "," + e.getUserAccount().getUsername() + ";";

		return result;

	}

}
