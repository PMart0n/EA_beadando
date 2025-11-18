package com.example.ea_beadando.service;

import com.oanda.v20.Context;
import com.oanda.v20.ContextBuilder;
import com.oanda.v20.account.*;
import com.oanda.v20.order.*;
import com.oanda.v20.pricing.*;
import com.oanda.v20.instrument.*;
import com.oanda.v20.primitives.*;
import com.oanda.v20.trade.*;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class ForexService {

    private final Context ctx;
    private final AccountID accountId;

    public ForexService() {

        String token = "";
        String account = "";

        this.ctx = new ContextBuilder("https://api-fxpractice.oanda.com")
                .setToken(token)
                .setApplication("EA_beadando")
                .build();

        this.accountId = new AccountID(account);
    }

    // ------------------ 1. Számlainfo ------------------
    public AccountSummary getAccountSummary() throws Exception {
        AccountContext ac = new AccountContext(ctx);
        return ac.summary(accountId).getAccount();
    }

    // ------------------ 2. Aktuális ár ------------------
    public ClientPrice getCurrentPrice(String instrument) throws Exception {
        PricingContext pc = new PricingContext(ctx);
        PricingGetResponse response =
                pc.get(accountId, Collections.singleton(instrument));

        if (!response.getPrices().isEmpty()) {
            return response.getPrices().get(0);
        }
        return null;
    }

    // ------------------ 3. 10 historikus gyertya ------------------
    public InstrumentCandlesResponse getHistoricalPrices(String instrument, String granularity) throws Exception {

        InstrumentCandlesRequest req =
                new InstrumentCandlesRequest(new InstrumentName(instrument));

        req.setGranularity(CandlestickGranularity.valueOf(granularity));
        req.setCount(10L);

        InstrumentContext ic = new InstrumentContext(ctx);
        return ic.candles(req);
    }

    // ------------------ 4. Pozíció nyitás ------------------
    public OrderCreateResponse openPosition(String instrument, int units) throws Exception {

        MarketOrderRequest order = new MarketOrderRequest();
        order.setInstrument(new InstrumentName(instrument));
        order.setUnits(units);

        OrderCreateRequest req = new OrderCreateRequest(accountId);
        req.setOrder(order);

        OrderContext oc = new OrderContext(ctx);
        return oc.create(req);
    }

    // ------------------ 5. Nyitott pozíciók ------------------
    public TradeListOpenResponse listOpenTrades() throws Exception {
        TradeContext tc = new TradeContext(ctx);
        return tc.listOpen(accountId);
    }

    // ------------------ 6. Pozíció zárás ------------------
    public TradeCloseResponse closeTrade(long tradeId) throws Exception {

        TradeContext tc = new TradeContext(ctx);

        TradeSpecifier spec = new TradeSpecifier(String.valueOf(tradeId));

        TradeCloseRequest req = new TradeCloseRequest(accountId, spec);
        req.setUnits("ALL");

        return tc.close(req); // <-- 1 paraméter a TE verziódban!
    }
}