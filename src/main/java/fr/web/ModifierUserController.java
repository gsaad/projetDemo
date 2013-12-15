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
public class ModifierUserController {

    @Autowired
    private UserService userService;
    
    @RequestMapping(value = "/modifierUser", method = RequestMethod.GET)
    public ModelAndView display() {
        ModelAndView mv = new ModelAndView("welcomeModifierUser");
        mv.addObject("user", userService.getCurrentUser());
        return mv;
    }

    @RequestMapping(value = "/modifierUser", method = RequestMethod.POST)
    public ModelAndView modifierUser(User user, ModelMap model) {
    	ModelAndView mav = new ModelAndView("welcomeModifierUser");
    	 if (StringUtils.isEmpty(user.getLastName()) ||
         		StringUtils.isEmpty(user.getFirstName()) ||
         		StringUtils.isEmpty(user.getEmail()) ||
         		StringUtils.isEmpty(user.getPassword()) ||
         		StringUtils.isEmpty(user.getVerifyPassword())){
    		 mav.addObject("messageErreur", "user.info.champs.obligatoire");
    		 mav.addObject("user", user);
         	return mav;
         }

         if (!user.getPassword().equals(user.getVerifyPassword())) {
        	 mav.addObject("messageErreur", "user.info.password.not.matching");
        	 mav.addObject("user", user);
         	return mav;
         }
        
        User userContext = userService.getCurrentUser();
        userContext.setLastName(user.getLastName());
        userContext.setEmail(user.getEmail());
        userContext.setFirstName(user.getFirstName());
        userContext.setPassword(user.getPassword());
        userService.updateUser(userContext);
        mav.setViewName("modifierUser_ok");
        return mav;
    }

    @RequestMapping(value = "/modifierUser/Cancel", method = RequestMethod.GET)
    public String cancel() {
    	 return "redirect:/listeDocs";
    }
}
