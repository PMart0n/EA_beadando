package com.example.ea_beadando.model.forex;

public class MessageClosePosition {
    private long tradeId;
    // Getters/Setters
    public int getTradeId() { return Math.toIntExact(tradeId); }
    public void setTradeId(int tradeId) { this.tradeId = tradeId; }
}