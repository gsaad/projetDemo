package fr.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import fr.persistence.domain.User;
import fr.service.UserService;

@Controller
public class RegisterController {

    @Autowired
    private UserService userService;
    
    @RequestMapping(value="/register", method = RequestMethod.GET)
    public ModelAndView display() {
        ModelAndView mv = new ModelAndView("accueilRegister");
        mv.addObject("user", new User());
        return mv;
    }

    @RequestMapping(value="/register",method = RequestMethod.POST)
    public ModelAndView register(User user, ModelMap model) {
    	ModelAndView mv = new ModelAndView("accueilRegister");
        if (StringUtils.isEmpty(user.getLogin()) ||
        		StringUtils.isEmpty(user.getLastName()) ||
        		StringUtils.isEmpty(user.getFirstName()) ||
        		StringUtils.isEmpty(user.getEmail()) ||
        		StringUtils.isEmpty(user.getPassword()) ||
        		StringUtils.isEmpty(user.getVerifyPassword())){
        	mv.addObject("messageErreur", "user.info.champs.obligatoire");
        	mv.addObject("user", user);
        	return mv;
        }
        
        if(userService.findUser(user.getLogin())!=null){
        	mv.addObject("messageErreur", "user.info.utilisateur.attribue");
        	mv.addObject("user", user);
        	return mv;
        }
        
        if (!user.getPassword().equals(user.getVerifyPassword())) {
        	mv.addObject("messageErreur", "user.info.password.not.matching");
        	mv.addObject("user", user);
        	return mv;
        }
        mv.setViewName("register_ok");
        userService.createUser(user);
        return mv;
    }

    @RequestMapping(value = "/register/Cancel", method = RequestMethod.GET)
    public String cancel() {
    	 return "redirect:/login";
    }
}
