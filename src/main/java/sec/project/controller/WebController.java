package sec.project.controller;

import java.sql.SQLException;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import sec.project.domain.UserInformation;
import sec.project.repository.UserRepository;

@Controller
public class WebController {

    //@Autowired
    private UserRepository userRepository;
    
    public WebController() throws Exception {
        userRepository = new UserRepository();
    }  

    @RequestMapping("*")
    public String defaultMapping(HttpSession session) {
        if (session.getAttribute("user_id") != null) {
            Long uid = (Long)session.getAttribute("user_id");      
            //return "redirect:/userinfo/" + uid;
            return "redirect:/messages";
        } else {
            return "redirect:/signin";
        }
    }
    
    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String loadRegister() {
        return "register";
    }
    
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String submitForm(@RequestParam String uname, @RequestParam String password, @RequestParam String ssn) throws Exception {
        userRepository.save(new UserInformation(uname, password, ssn));
        return "registered";
    }

}
