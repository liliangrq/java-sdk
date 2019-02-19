package com.binance.dex.api.client.Broadcast;

import com.binance.dex.api.client.*;
import com.binance.dex.api.client.domain.OrderSide;
import com.binance.dex.api.client.domain.OrderType;
import com.binance.dex.api.client.domain.TimeInForce;
import com.binance.dex.api.client.domain.TransactionMetadata;
import com.binance.dex.api.client.domain.broadcast.NewOrder;
import com.binance.dex.api.client.domain.broadcast.TransactionOption;
import org.testng.annotations.Test;
import org.testng.log4testng.Logger;
import com.binance.dex.api.client.BinanceDexApiClientFactory;
import com.binance.dex.api.client.BinanceDexApiRestClient;
import com.binance.dex.api.client.BinanceDexEnvironment;
import com.binance.dex.api.client.Wallet;
import com.binance.dex.api.client.domain.OrderSide;
import com.binance.dex.api.client.domain.OrderType;
import com.binance.dex.api.client.domain.TimeInForce;
import com.binance.dex.api.client.domain.TransactionMetadata;
import com.binance.dex.api.client.domain.broadcast.*;
import org.junit.Ignore;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class TestCancelOrder {
    private static Logger logger = Logger.getLogger(Test.class);

    Wallet wallet = new Wallet("29d1f25e20f715579a4bc9f7d05bf85507abc5a24919e4114111fa3fc19b86ef",
            BinanceDexEnvironment.TEST_NET);
    BinanceDexApiRestClient client =
            BinanceDexApiClientFactory.newInstance().newRestClient(BinanceDexEnvironment.TEST_NET.getBaseUrl());
    Wallet toWallet = new Wallet("1bb30cb486f610f6bd6c8b8f7cbdce9e2d37e716e2cb7b82669330c5b126a574",
            BinanceDexEnvironment.TEST_NET);

    @Test(expectedExceptions = BinanceDexApiException.class, expectedExceptionsMessageRegExp = ".*does not belong to transaction sender.*")
    public void testCancelOthersOrder() throws IOException, NoSuchAlgorithmException{

        String symbol = "ADA.B-F2F_BNB";
        CancelOrder co = new CancelOrder();
        co.setSymbol(symbol);
        co.setRefId("8672775CD0C8EA51364B5231FD9A63893B55FF82-22");

        try {
            List<TransactionMetadata> resp = client.cancelOrder(co, toWallet, TransactionOption.DEFAULT_INSTANCE, true);
            logger.info(resp.get(0));
        }catch  (BinanceDexApiException e) {
            logger.warn(e.getError());
            throw e;
        }
    }

    @Test(expectedExceptions = BinanceDexApiException.class, expectedExceptionsMessageRegExp = ".*Failed to find order.*")
    public void testCancelNonExistOrder() throws IOException, NoSuchAlgorithmException{

        String symbol = "ADA.B-F2F_BNB";
        CancelOrder co = new CancelOrder();
        co.setSymbol(symbol);
        co.setRefId("8672775CD0C8EA51364B5231FD9A63893B55FF82-23");

        try {
            List<TransactionMetadata> resp = client.cancelOrder(co, wallet, TransactionOption.DEFAULT_INSTANCE, true);
            logger.info(resp.get(0));
        }catch  (BinanceDexApiException e) {
            logger.warn(e.getError());
            throw e;
        }
    }

    @Test
    public void testCancelOrderNonEnoughFee() throws IOException, NoSuchAlgorithmException{

        String symbol = "ADA.B-F2F_BNB";
        CancelOrder co = new CancelOrder();
        co.setSymbol(symbol);
        co.setRefId("B3882888FA65475142C94E352EF67C384984A316-9");

        try {
            List<TransactionMetadata> resp = client.cancelOrder(co, toWallet, TransactionOption.DEFAULT_INSTANCE, true);
            logger.info(resp.get(0));
        }catch  (BinanceDexApiException e) {
            logger.warn(e.getError());
            throw e;
        }
    }

}

