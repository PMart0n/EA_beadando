package com.example.ea_beadando.controller;

import com.example.ea_beadando.model.soap.MessagePrice;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import soapclient.MNBArfolyamServiceSoap;
import soapclient.MNBArfolyamServiceSoapImpl;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

@Controller
public class SoapController {

    @GetMapping("/soap")
    public String soapForm(Model model) {
        model.addAttribute("param", new MessagePrice());
        model.addAttribute("contentFile", "soap/soap-form");
        return "layout";
    }

    @PostMapping("/soap")
    public String soapResult(@ModelAttribute MessagePrice messagePrice, Model model) throws Exception {
        // 1. SOAP Kliens hívása
        MNBArfolyamServiceSoapImpl impl = new MNBArfolyamServiceSoapImpl();
        MNBArfolyamServiceSoap service = impl.getCustomBindingMNBArfolyamServiceSoap();

        // Null ellenőrzés a dátumokra
        String kezdoDatum = (messagePrice.getStartDate() != null) ? messagePrice.getStartDate().toString() : java.time.LocalDate.now().minusDays(30).toString();
        String vegDatum = (messagePrice.getEndDate() != null) ? messagePrice.getEndDate().toString() : java.time.LocalDate.now().toString();

        String eredmenyXML = service.getExchangeRates(kezdoDatum, vegDatum, messagePrice.getCurrency());

        // 2. XML Feldolgozása
        List<String> dateLabels = new ArrayList<>();
        List<Double> rateData = new ArrayList<>();

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            InputSource is = new InputSource(new StringReader(eredmenyXML));
            Document doc = builder.parse(is);
            doc.getDocumentElement().normalize();
            NodeList dayNodes = doc.getElementsByTagName("Day");

            for (int i = dayNodes.getLength() - 1; i >= 0; i--) { // Fordított sorrend, hogy időrendben legyen
                Element dayElement = (Element) dayNodes.item(i);
                String date = dayElement.getAttribute("date");
                NodeList rateNodes = dayElement.getElementsByTagName("Rate");
                if (rateNodes.getLength() > 0) {
                    String rateString = rateNodes.item(0).getTextContent();
                    dateLabels.add(date);
                    rateData.add(Double.parseDouble(rateString.replace(",", ".")));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        model.addAttribute("chartLabels", dateLabels);
        model.addAttribute("chartData", rateData);
        model.addAttribute("contentFile", "soap/soap-result");
        return "layout";
    }
}