package com.example.ea_beadando.controller;

import com.example.ea_beadando.model.forex.MessageActPrice;
import com.example.ea_beadando.model.forex.MessageClosePosition;
import com.example.ea_beadando.model.forex.MessageHistPrice;
import com.example.ea_beadando.model.forex.MessageOpenPosition;
import com.example.ea_beadando.service.ForexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ForexController {

    @Autowired
    private ForexService forexService;

    // --- 1. Számlainformációk ---
    @GetMapping("/forex-account")
    public String getAccountInfo(Model model) throws Exception {
        model.addAttribute("account", forexService.getAccountSummary());
        model.addAttribute("contentFile", "forex/forex-account");
        return "layout";
    }

    @GetMapping("/forex-aktar")
    public String aktarForm(Model model) {
        model.addAttribute("par", new MessageActPrice());
        model.addAttribute("contentFile", "forex/forex-aktar-form");
        return "layout";
    }

    @PostMapping("/forex-aktar")
    public String aktarResult(@ModelAttribute MessageActPrice messageActPrice, Model model) throws Exception {
        model.addAttribute("price", forexService.getCurrentPrice(messageActPrice.getInstrument()));
        model.addAttribute("instrument", messageActPrice.getInstrument());
        model.addAttribute("contentFile", "forex/forex-aktar-result");
        return "layout";
    }

    // --- 3. Historikus Ár ---
    // --- 3. Historikus Ár ---
    @GetMapping("/forex-histar")
    public String histarForm(Model model) {
        model.addAttribute("param", new MessageHistPrice());
        model.addAttribute("contentFile", "forex/forex-histar-form");
        return "layout";
    }

    @PostMapping("/forex-histar")
    public String histarResult(@ModelAttribute MessageHistPrice messageHistPrice, Model model) throws Exception {

        model.addAttribute("candles",
                forexService.getHistoricalPrices(
                        messageHistPrice.getInstrument(),
                        messageHistPrice.getGranularity()
                )
        );

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
    public String nyitResult(@ModelAttribute MessageOpenPosition messageOpenPosition, Model model) throws Exception {
        model.addAttribute("result",
                forexService.openPosition(messageOpenPosition.getInstrument(), messageOpenPosition.getUnits()));
        model.addAttribute("contentFile", "forex/forex-nyit-result");
        return "layout";
    }

    // --- 5. Nyitott Pozíciók ---
    @GetMapping("/forex-poziciok")
    public String getPoziciok(Model model) throws Exception {
        model.addAttribute("trades", forexService.listOpenTrades());
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
    public String zarResult(@ModelAttribute MessageClosePosition messageClosePosition, Model model) throws Exception {
        model.addAttribute("result", forexService.closeTrade(messageClosePosition.getTradeId()));
        model.addAttribute("contentFile", "forex/forex-zar-result");
        return "layout";
    }
}