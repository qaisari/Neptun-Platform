package com.example.seminarHomework.core.controller;

import com.example.seminarHomework.core.entity.Message;
import com.example.seminarHomework.core.repository.MessageRepo;
import com.example.seminarHomework.core.entity.User;
import com.example.seminarHomework.core.repository.UserRepo;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;

@Controller
public class ContactController {
    @Autowired private MessageRepo messageRepo;
    @Autowired private UserRepo userRepo;

    @GetMapping("/contact")
    public String showContactForm(Model model, HttpServletRequest request) {
        // Get logged-in user's email
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = auth.getName();

        // Fetch user from database to get full details
        User user = userRepo.findByEmail(userEmail).orElse(null);

        Message message = new Message();
        if (user != null) {
            message.setName(user.getName());
            message.setEmail(user.getEmail());
        }

        model.addAttribute("uri", request.getRequestURI());
        model.addAttribute("message", message);
        return "core/func/contact";
    }

    @PostMapping("/contact")
    public String submitContactForm(@ModelAttribute Message message, RedirectAttributes redAttr) {
        try {
            // Get logged-in user
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String userEmail = auth.getName();
            User user = userRepo.findByEmail(userEmail).orElse(null);

            // Associate message with user
            if (user != null) {
                message.setUser(user);
            }

            messageRepo.save(message);
            redAttr.addFlashAttribute("success", "Thank you! Your message has been sent successfully.");
            return "redirect:/contact";
        } catch (Exception e) {
            redAttr.addFlashAttribute("error", "Failed to send message. Please try again.");
            return "redirect:/contact";
        }
    }

    @GetMapping("/messages")
    public String viewMessages(Model model, HttpServletRequest request) {
        model.addAttribute("uri", request.getRequestURI());
        // Fetch all messages ordered by newest first
        List<Message> messages = messageRepo.findAllOrderByNewest();
        model.addAttribute("messages", messages);
        return "core/users/adminMessages";
    }
}

