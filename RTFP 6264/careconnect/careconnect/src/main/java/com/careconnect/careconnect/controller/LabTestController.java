package com.careconnect.careconnect.controller;

import com.careconnect.careconnect.model.LabTest;
import com.careconnect.careconnect.repository.LabTestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/labtests")
public class LabTestController {

    @Autowired
    private LabTestRepository labTestRepository;

    @GetMapping
    public String showBookingPage(Model model) {
        // For booking form
        model.addAttribute("labTest", new LabTest());

        // Available test types
        List<String> labTests = Arrays.asList(
                "Blood Test", "Urine Test", "X-Ray", "MRI", "COVID-19 Test"
        );

        // Available time slots
        List<String> timeSlots = Arrays.asList(
                "9:00 AM", "11:00 AM", "2:00 PM", "4:00 PM"
        );

        // Sample lab names in Hyderabad
        List<String> labNames = Arrays.asList(
                "Apollo Diagnostics", 
                "Vijaya Diagnostic Centre",
                "Lucid Medical Diagnostics", 
                "Tenet Diagnostics", 
                "Medicover Labs",
                "Dr. Lal PathLabs", 
                "SRL Diagnostics"
        );

        // Pass all data to view
        model.addAttribute("labTests", labTests);
        model.addAttribute("timeSlots", timeSlots);
        model.addAttribute("labNames", labNames);
        model.addAttribute("scheduledAppointments", labTestRepository.findAll());

        return "labtests";
    }

    @PostMapping("/book")
    public String bookTest(@ModelAttribute LabTest labTest) {
        labTestRepository.save(labTest);
        return "redirect:/labtests";
    }

    @PostMapping("/cancel")
    public String cancelTest(@RequestParam Long id) {
        labTestRepository.deleteById(id);
        return "redirect:/labtests";
    }
}
