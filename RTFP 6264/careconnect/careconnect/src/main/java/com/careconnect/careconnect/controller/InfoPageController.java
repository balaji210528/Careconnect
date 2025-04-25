package com.careconnect.careconnect.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class InfoPageController {

    @GetMapping("/contact")
    public String showContactPage() {
        return "contact";
    }

    @GetMapping("/privacy")
    public String showPrivacyPolicy() {
        return "privacy";
    }
}
