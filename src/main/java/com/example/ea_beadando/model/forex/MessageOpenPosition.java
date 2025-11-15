package com.example.ea_beadando.model.forex;

public class MessageOpenPosition {
    private String instrument;
    private int units;
    // Getters/Setters
    public String getInstrument() { return instrument; }
    public void setInstrument(String instrument) { this.instrument = instrument; }
    public int getUnits() { return units; }
    public void setUnits(int units) { this.units = units; }
}