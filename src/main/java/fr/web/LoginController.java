package fr.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import fr.service.DocumentService;
import fr.service.UserService;
 
@Controller
public class LoginController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	DocumentService documentService;
 
	@RequestMapping(value="/login", method = RequestMethod.GET)
	public ModelAndView login() {
		fr.persistence.domain.User currentUser = userService.getCurrentUser();
		if(currentUser!=null){
			ModelAndView mv = new ModelAndView("redirect:/document/listeDocs");
	        return mv;
		}else{
			ModelAndView mv = new ModelAndView("accueilLogin");
		    mv.addObject("user", new fr.persistence.domain.User());
		    return mv;
		}
	}
	
	@RequestMapping(value="/loginfailed", method = RequestMethod.GET)
	public ModelAndView loginfailed(ModelMap model) {
		ModelAndView mv = new ModelAndView("accueilLogin");
		mv.addObject("error", "true");
		return mv;
 
	}
	
	@RequestMapping(value="/logout", method = RequestMethod.GET)
	public String logout(ModelMap model) {
		return "accueilLogin";
	}
}