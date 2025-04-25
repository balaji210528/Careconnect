package com.careconnect.careconnect.controller;

import com.careconnect.careconnect.model.Message;
import com.careconnect.careconnect.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/messages")
public class MessageController {

    @Autowired
    private MessageRepository messageRepository;

    // Predefined doctor list
    private final List<String> doctorList = Arrays.asList("Dr. Arjun Rao", "Dr. Priya Iyer", "Dr. Ramesh Gupta");

    // Load messages page with conversation and doctor dropdown
    @GetMapping
    public String showMessages(Model model) {
        model.addAttribute("messages", messageRepository.findAll());
        model.addAttribute("doctors", doctorList);
        return "messages";
    }

    // Save new message
    @PostMapping("/send")
    public String sendMessage(@RequestParam String doctor, @RequestParam String message) {
        Message msg = new Message();
        msg.setSender("Patient");
        msg.setRecipient(doctor);
        msg.setText(message);
        messageRepository.save(msg);
        return "redirect:/messages";
    }

    // ✅ Clear all messages (conversation)
    @PostMapping("/clear")
    public String clearConversation() {
        messageRepository.deleteAll();
        return "redirect:/messages";
    }
}
