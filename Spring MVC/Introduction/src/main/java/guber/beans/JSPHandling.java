package guber.beans;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class JSPHandling {

	@Autowired
	private SuperService superService;

	@RequestMapping(method = RequestMethod.GET)
	public String printWelcome(ModelMap model) {
		model.addAttribute("message", "Guber taxi is the best taxi in the world!");
		return "index";
	}


	// method = RequestMethod.GET can be skipped
	@RequestMapping(value = "/cabList", method = RequestMethod.GET)
	public ModelAndView main() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("cabs", superService.getCabs());
		modelAndView.setViewName("cabList");
		return modelAndView;
	}

	@GetMapping(value = "/{manufactureYear}")
	public String get(@PathVariable("manufactureYear") int manufactureYear, Model model) {


		Cab cab = superService.findByYear(manufactureYear);
		model.addAttribute("cab", cab);

		return "cab";
	}
}