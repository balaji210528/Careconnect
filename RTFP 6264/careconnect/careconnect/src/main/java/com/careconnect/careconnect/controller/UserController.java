package com.careconnect.careconnect.controller;

import com.careconnect.careconnect.model.User;
import com.careconnect.careconnect.repository.UserRepository;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/register")
    public String showRegister(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute User user, Model model) {
        if (userRepository.findByEmail(user.getEmail()) != null) {
            model.addAttribute("error", "Email already exists.");
            return "register";
        }
        userRepository.save(user);
        model.addAttribute("success", "Registration successful! Please login.");
        return "login";
    }

    @GetMapping("/login")
    public String showLogin() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email,
                        @RequestParam String password,
                        Model model,
                        HttpSession session) {

        User user = userRepository.findByEmail(email);
        if (user != null && user.getPassword().equals(password)) {
            session.setAttribute("loggedInUser", user); // ✅ Store user in session
            model.addAttribute("user", user);
            return "dashboard";
        } else {
            model.addAttribute("error", "Invalid email or password.");
            return "login";
        }
    }

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser"); // ✅ Use session
        if (user == null) return "redirect:/login";
        model.addAttribute("user", user);
        return "dashboard";
    }

    @GetMapping("/profile")
    public String profile(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser"); // ✅ Use session
        if (user == null) return "redirect:/login";
        model.addAttribute("user", user);
        return "profile";
    }

    @GetMapping("/profile/edit")
    public String editProfile(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser"); // ✅ Use session
        if (user == null) return "redirect:/login";
        model.addAttribute("user", user);
        return "edit-profile";
    }

    @PostMapping("/profile/update")
    public String updateProfile(@ModelAttribute("user") User updatedUser,
                                HttpSession session,
                                RedirectAttributes redirectAttributes) {

        User existingUser = userRepository.findById(updatedUser.getId()).orElse(null);

        if (existingUser != null) {
            existingUser.setFullName(updatedUser.getFullName());
            existingUser.setEmail(updatedUser.getEmail());
            existingUser.setAge(updatedUser.getAge());
            existingUser.setDob(updatedUser.getDob());

            if (updatedUser.getPassword() != null && !updatedUser.getPassword().isBlank()) {
                existingUser.setPassword(updatedUser.getPassword());
            }

            userRepository.save(existingUser);

            session.setAttribute("loggedInUser", existingUser); // ✅ Update session user
            redirectAttributes.addFlashAttribute("success", "Profile updated successfully!");
        }

        return "redirect:/profile/edit";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // ✅ Clear session
        return "redirect:/login";
    }
}
