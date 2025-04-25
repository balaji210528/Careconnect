package com.careconnect.careconnect.controller;

import com.careconnect.careconnect.model.Appointment;
import com.careconnect.careconnect.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentRepository appointmentRepository;

    private final List<String> hospitalList = Arrays.asList("Apollo Hospital", "Yashoda Hospital", "Care Hospital");
    private final List<String> doctorList = Arrays.asList("Dr. Saketh(Cardiologist)", "Dr. Abhilash(Neurologist)", "Dr. Harshitha(ENT)");

    @GetMapping
    public String showAppointmentsPage(Model model) {
        model.addAttribute("hospitals", hospitalList);
        model.addAttribute("doctors", doctorList);
        model.addAttribute("appointments", appointmentRepository.findAll());
        return "appointments";
    }

    @PostMapping("/book")
    public String bookAppointment(@ModelAttribute Appointment appointment) {
        appointmentRepository.save(appointment);
        return "redirect:/appointments";
    }

    @PostMapping("/cancel")
    public String cancelAppointment(@RequestParam Long appointmentId) {
        appointmentRepository.deleteById(appointmentId);
        return "redirect:/appointments";
    }
}
