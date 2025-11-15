package com.example.ea_beadando.controller;

import com.example.ea_beadando.model.forex.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ForexController {

    // --- 1. Számlainformációk ---
    @GetMapping("/forex-account")
    public String getAccountInfo(Model model) {
        // TODO: API hívás
        model.addAttribute("contentFile", "forex/forex-account");
        return "layout";
    }

    // --- 2. Aktuális Ár ---
    @GetMapping("/forex-aktar")
    public String aktarForm(Model model) {
        model.addAttribute("par", new MessageActPrice());
        model.addAttribute("contentFile", "forex/forex-aktar-form");
        return "layout";
    }

    @PostMapping("/forex-aktar")
    public String aktarResult(@ModelAttribute MessageActPrice messageActPrice, Model model) {
        // TODO: API hívás
        model.addAttribute("contentFile", "forex/forex-aktar-result");
        return "layout";
    }

    // --- 3. Historikus Ár ---
    @GetMapping("/forex-histar")
    public String histarForm(Model model) {
        model.addAttribute("param", new MessageHistPrice());
        model.addAttribute("contentFile", "forex/forex-histar-form");
        return "layout";
    }

    @PostMapping("/forex-histar")
    public String histarResult(@ModelAttribute MessageHistPrice messageHistPrice, Model model) {
        // TODO: API hívás
        model.addAttribute("contentFile", "forex/forex-histar-result");
        return "layout";
    }

    // --- 4. Pozíció Nyitás ---
    @GetMapping("/forex-nyit")
    public String nyitForm(Model model) {
        model.addAttribute("param", new MessageOpenPosition());
        model.addAttribute("contentFile", "forex/forex-nyit-form");
        return "layout";
    }

    @PostMapping("/forex-nyit")
    public String nyitResult(@ModelAttribute MessageOpenPosition messageOpenPosition, Model model) {
        // TODO:API hívás
        model.addAttribute("contentFile", "forex/forex-nyit-result");
        return "layout";
    }

    // --- 5. Nyitott Pozíciók ---
    @GetMapping("/forex-poziciok")
    public String getPoziciok(Model model) {
        // TODO: API hívás
        model.addAttribute("contentFile", "forex/forex-poziciok");
        return "layout";
    }

    // --- 6. Pozíció Zárás ---
    @GetMapping("/forex-zar")
    public String zarForm(Model model) {
        model.addAttribute("param", new MessageClosePosition());
        model.addAttribute("contentFile", "forex/forex-zar-form");
        return "layout";
    }

    @PostMapping("/forex-zar")
    public String zarResult(@ModelAttribute MessageClosePosition messageClosePosition, Model model) {
        // TODO: API hívás
        model.addAttribute("contentFile", "forex/forex-zar-result");
        return "layout";
    }
}