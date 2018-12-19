/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sec.project.controller;

import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import sec.project.domain.Message;
import sec.project.domain.UserInformation;
import sec.project.repository.MessageRepository;
import sec.project.repository.UserRepository;

@Controller
public class MessagesController {
    private UserRepository userRepository;
    private MessageRepository messageRepository;
    
    public MessagesController() throws Exception {
        userRepository = new UserRepository();
        messageRepository = new MessageRepository();
    }
    
    @RequestMapping(value = "/messages", method = RequestMethod.GET)
    public String loadMessages(HttpSession session, Model model) throws Exception {      
        if (session.getAttribute("user_id") != null) {
            Long uid = (Long)session.getAttribute("user_id");
            return loadMessagesAuthed(session, model, uid);
        } else {
            return "redirect:/";
        }
    }

    @RequestMapping(value = "/messages", method = RequestMethod.POST)
    public String postMessage(HttpSession session, Model model, @RequestParam String message) throws Exception {      
        if (session.getAttribute("user_id") != null) {
            Long uid = (Long)session.getAttribute("user_id");
            saveMessage((int)(long)uid, message);
            return loadMessagesAuthed(session, model, uid);
        } else {
            return "redirect:/";
        }
    }
    
    void saveMessage(int uid, String message) throws Exception {
        messageRepository.save(new Message(uid, message));
    }

    private String loadMessagesAuthed(HttpSession session, Model model, Long uid) throws Exception {
        UserInformation u = userRepository.findOne(uid);        
        if (u == null) {
            throw new Exception("Unknown uid=" + uid);
        }        
        
        model.addAttribute("uname", u.getName());
        
        List<Message> msgs = messageRepository.findAll();        
        model.addAttribute("messages", msgs);
        
        return "messages";        
    }
    
}
