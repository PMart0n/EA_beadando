package com.example.ea_beadando.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String fooldal(Model model) {
        // layout.html az index.html tartalmát töltse be
        model.addAttribute("contentFile", "index");
        return "layout";
    }
}