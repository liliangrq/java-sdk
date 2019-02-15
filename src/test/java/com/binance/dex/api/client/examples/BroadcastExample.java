package com.binance.dex.api.client.examples;

import com.binance.dex.api.client.BinanceDexApiClientFactory;
import com.binance.dex.api.client.BinanceDexApiRestClient;
import com.binance.dex.api.client.BinanceDexEnvironment;
import com.binance.dex.api.client.Wallet;
import com.binance.dex.api.client.domain.OrderSide;
import com.binance.dex.api.client.domain.OrderType;
import com.binance.dex.api.client.domain.TimeInForce;
import com.binance.dex.api.client.domain.TransactionMetadata;
import com.binance.dex.api.client.domain.broadcast.NewOrder;
import com.binance.dex.api.client.domain.broadcast.TransactionOption;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class BroadcastExample {
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        Wallet wallet =
                new Wallet("29d1f25e20f715579a4bc9f7d05bf85507abc5a24919e4114111fa3fc19b86ef",
                        BinanceDexEnvironment.TEST_NET);
        BinanceDexApiRestClient client =
                BinanceDexApiClientFactory.newInstance().newRestClient(BinanceDexEnvironment.TEST_NET.getBaseUrl());

        String symbol = "ADA.B-B63_BNB";

        NewOrder no = new NewOrder();
        no.setTimeInForce(TimeInForce.GTE);
        no.setOrderType(OrderType.LIMIT);
        no.setSide(OrderSide.BUY);
        no.setPrice("10.7");
        no.setQuantity("100.0");
        no.setSymbol(symbol);

        TransactionOption options = TransactionOption.DEFAULT_INSTANCE;
        List<TransactionMetadata> resp = client.newOrder(no, wallet, options, true);
        System.out.println(resp.get(0));
    }
}
