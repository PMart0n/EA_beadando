package com.example.ea_beadando.service;

import com.oanda.v20.Context;
import com.oanda.v20.ContextBuilder;
import com.oanda.v20.account.*;
import com.oanda.v20.instrument.CandlestickGranularity;
import com.oanda.v20.instrument.InstrumentCandlesRequest;
import com.oanda.v20.instrument.InstrumentContext;
import com.oanda.v20.order.MarketOrderRequest;
import com.oanda.v20.order.OrderContext;
import com.oanda.v20.order.OrderCreateRequest;
import com.oanda.v20.order.OrderCreateResponse;
import com.oanda.v20.pricing.*;
import com.oanda.v20.primitives.InstrumentName;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class ForexService {

    private final Context ctx;
    private final AccountID accountId;

    public ForexService() {
        String token = "//IDE KELL MAJD A TOKENEM";
        String account = "//IDE KELL MAJD AZ ACCOUNT ID";

        this.ctx = new ContextBuilder("https://api-fxpractice.oanda.com")
                .setToken(token)
                .setApplication("EA_beadando")
                .build();

        this.accountId = new AccountID(account);
    }

    // 1. Számlainformációk lekérése //
    public AccountSummary getAccountSummary() throws Exception {
        AccountContext accountContext = new AccountContext(ctx);
        AccountSummaryResponse response = accountContext.summary(accountId);
        return response.getAccount();
    }

    // 2. Aktuális ár lekérése //
    public ClientPrice getCurrentPrice(String instrument) throws Exception {
        PricingContext pricingContext = new PricingContext(ctx);

        PricingGetResponse response = pricingContext.get(
                accountId,
                Collections.singleton(instrument)
        );

        if (!response.getPrices().isEmpty()) {
            return response.getPrices().get(0);
        }
        return null;
    }

    // 3. Historikus árak (10 utolsó gyertya)
    public CandlestickResponse getHistoricalPrices(String instrument, String granularity) throws Exception {

        InstrumentCandlesRequest req = new InstrumentCandlesRequest(new InstrumentName(instrument));
        req.setGranularity(CandlestickGranularity.valueOf(granularity)); // pl. D, H1, M15
        req.setCount(10); // 10 utolsó gyertya

        InstrumentContext ic = new InstrumentContext(ctx);
        return ic.candles(req);
    }
    // 4. Pozíció nyitás (Market Order)
    public OrderCreateResponse openPosition(String instrument, int units) throws Exception {

        OrderContext oc = new OrderContext(ctx);

        MarketOrderRequest mr = new MarketOrderRequest();
        mr.setInstrument(new InstrumentName(instrument));
        mr.setUnits(units); // pozitív = long, negatív = short

        OrderCreateRequest req = new OrderCreateRequest(accountId);
        req.setOrder(mr);

        return oc.create(req);
    }

}
