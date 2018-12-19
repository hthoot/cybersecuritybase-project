/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sec.project.controller;

import javax.servlet.http.HttpSession;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import sec.project.domain.UserInformation;
import sec.project.repository.UserRepository;

public class UserInfoController {
    private UserRepository userRepository;
    
    public UserInfoController() throws Exception {
        userRepository = new UserRepository();
    }

    @RequestMapping(value = "/userinfo", method = RequestMethod.GET)
    public String loadUserinfo(HttpSession session) throws Exception {      
        if (session.getAttribute("user_id") != null) {
            Long uid = (Long)session.getAttribute("user_id");      
            return "redirect:/userinfo/" + uid;
        } else {
            return "redirect:/";
        }
    }
    
    @RequestMapping(value = "/userinfo/{uid}", method = RequestMethod.GET)
    public String loadUserinfo(HttpSession session, Model model, @PathVariable("uid") long uid) throws Exception {      
        UserInformation u = userRepository.findOne(uid);
        
        if (u == null) {
            throw new Exception("Unknown uid=" + uid);
        }
        
        model.addAttribute("uname", u.getName());
        model.addAttribute("ssn", u.getSocialSecurityNumber());
        
        return "userinfo";
    }
    
}
