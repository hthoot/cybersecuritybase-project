/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sec.project.controller;

import java.sql.SQLException;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import sec.project.domain.UserInformation;
import sec.project.repository.UserRepository;

/**
 *
 * @author hhati
 */
@Controller
public class SignInController {

    //@Autowired
    private UserRepository userRepository;
    
    public SignInController() throws Exception {
        userRepository = new UserRepository();
    }

    @RequestMapping(value = "/signin", method = RequestMethod.GET)
    public String loadSignin(HttpSession session) {
        if (session.getAttribute("user_id") != null) {
            return "redirect:/userinfo";
        } else {
            return "signin";
        }
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpSession session) {
        session.removeAttribute("user_id");
        return "redirect:/signin";
    }
    
    @RequestMapping(value = "/signin", method = RequestMethod.POST)
    public String SignIn(Model model, HttpSession session, @RequestParam String uname, @RequestParam String password) throws Exception {
        List<UserInformation> users = userRepository.findAll();
        
        for(UserInformation user : users) {
            if (user.getName().equals(uname)) {
                return SignIn(model, session, user, password);
            }
        }
        
        model.addAttribute("signin_error", "Username \"" + uname + "\" not found");
        
        return "signin";
    }

    private String SignIn(Model model, HttpSession session, @RequestParam UserInformation user, @RequestParam String password) {        
        if (user.getPassword().equals(password)) {
            session.setAttribute("user_id", user.getId());
            return "redirect:/";
        } else {
            model.addAttribute("signin_error", "Password was not correct.");            
            return "signin";
        }
    }
    
}
