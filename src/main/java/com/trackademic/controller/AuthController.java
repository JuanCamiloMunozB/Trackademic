package com.trackademic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;

import com.trackademic.nosql.document.Student;
import com.trackademic.service.interfaces.AuthService;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @GetMapping("/login")
    public String showLoginForm() {
        return "auth/login";
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model){
        model.addAttribute("student", new Student());
        return "auth/register";
    }

    @PostMapping("/register")
    public String registerStudent(@ModelAttribute Student student, Model model) {
        if (authService.registerStudent(student)) {
            return "redirect:/auth/login?registered=true";
        } else {
            model.addAttribute("error", "El email ya está en uso");
            return "auth/register";
        }
    }


    
}
