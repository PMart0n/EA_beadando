package com.example.ea_beadando.model.soap;

import java.time.LocalDate;

public class MessagePrice {
    private String currency;
    private LocalDate startDate;
    private LocalDate endDate;

    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }
}