package com.binance.dex.api.client.Broadcast;
import com.binance.dex.api.client.BinanceDexApiException;
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
import org.testng.annotations.Test;
import org.testng.log4testng.Logger;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class TestNewOrder {
    private static Logger logger = Logger.getLogger(Test.class);

    Wallet wallet = new Wallet("29d1f25e20f715579a4bc9f7d05bf85507abc5a24919e4114111fa3fc19b86ef",
            BinanceDexEnvironment.TEST_NET);
    BinanceDexApiRestClient client =
            BinanceDexApiClientFactory.newInstance().newRestClient(BinanceDexEnvironment.TEST_NET.getBaseUrl());
    Wallet toWallet = new Wallet("1bb30cb486f610f6bd6c8b8f7cbdce9e2d37e716e2cb7b82669330c5b126a574",
            BinanceDexEnvironment.TEST_NET);

    @Test
    public void testNewOrder() throws IOException, NoSuchAlgorithmException{
        String symbol = "ADA.B-F2F_BNB";

        NewOrder no = new NewOrder();
        no.setTimeInForce(TimeInForce.GTE);
        no.setOrderType(OrderType.LIMIT);
        no.setSide(OrderSide.BUY);
        no.setPrice("0.0042");
        no.setQuantity("1.0");
        no.setSymbol(symbol);
        TransactionOption options = TransactionOption.DEFAULT_INSTANCE;

        try {
            List<TransactionMetadata> resp = client.newOrder(no, wallet, options, true);
            logger.info(resp.get(0));

        }catch  (BinanceDexApiException e) {
            logger.warn(e.getError());
            throw e;
        }
    }

    @Test(expectedExceptions = BinanceDexApiException.class, expectedExceptionsMessageRegExp = ".*Quantity:Zero/Negative Number.*")
    public void testNewOrderNegativeNumber() throws IOException, NoSuchAlgorithmException{
        String symbol = "ADA.B-F2F_BNB";

        NewOrder no = new NewOrder();
        no.setTimeInForce(TimeInForce.GTE);
        no.setOrderType(OrderType.LIMIT);
        no.setSide(OrderSide.BUY);
        no.setPrice("0.0042");
        no.setQuantity("-1.0");
        no.setSymbol(symbol);
        TransactionOption options = TransactionOption.DEFAULT_INSTANCE;

        try {
            List<TransactionMetadata> resp = client.newOrder(no, wallet, options, true);
            logger.info(resp.get(0));

        }catch  (BinanceDexApiException e) {
            logger.warn(e.getError());
            throw e;
        }
    }

    @Test(expectedExceptions = BinanceDexApiException.class, expectedExceptionsMessageRegExp = ".*Quantity:Zero.*")
    public void testNewOrderZeroAmount() throws IOException, NoSuchAlgorithmException{

        String symbol = "ADA.B-F2F_BNB";
        NewOrder no = new NewOrder();
        no.setTimeInForce(TimeInForce.GTE);
        no.setOrderType(OrderType.LIMIT);
        no.setSide(OrderSide.BUY);
        no.setPrice("0.0042");
        no.setQuantity("0");
        no.setSymbol(symbol);

        TransactionOption options = TransactionOption.DEFAULT_INSTANCE;
        try {
            List<TransactionMetadata> resp = client.newOrder(no, wallet, options, true);
            logger.info(resp.get(0));

        }catch  (BinanceDexApiException e) {
            logger.warn(e.getError());
            throw e;
        }
    }

    @Test(expectedExceptions = BinanceDexApiException.class, expectedExceptionsMessageRegExp = ".*Price:Zero.*")
    public void testNewOrderZeroPrice() throws IOException, NoSuchAlgorithmException{

        String symbol = "ADA.B-F2F_BNB";
        NewOrder no = new NewOrder();
        no.setTimeInForce(TimeInForce.GTE);
        no.setOrderType(OrderType.LIMIT);
        no.setSide(OrderSide.BUY);
        no.setPrice("0");
        no.setQuantity("1.0");
        no.setSymbol(symbol);

        TransactionOption options = TransactionOption.DEFAULT_INSTANCE;
        try {
            List<TransactionMetadata> resp = client.newOrder(no, wallet, options, true);
            logger.info(resp.get(0));

        }catch  (BinanceDexApiException e) {
            logger.warn(e.getError());
            throw e;
        }
    }

    //todo
    @Test(expectedExceptions = BinanceDexApiException.class, expectedExceptionsMessageRegExp = ".*trading pair not found.*")
    public void testNewOrderNoExitPair() throws IOException, NoSuchAlgorithmException{

        String symbol = "ADA.B-F_BNB";

        NewOrder no = new NewOrder();
        no.setTimeInForce(TimeInForce.GTE);
        no.setOrderType(OrderType.LIMIT);
        no.setSide(OrderSide.BUY);
        no.setPrice("0.0042");
        no.setQuantity("1.0");
        no.setSymbol(symbol);

        TransactionOption options = TransactionOption.DEFAULT_INSTANCE;
        try {
            List<TransactionMetadata> resp = client.newOrder(no, wallet, options, true);
            logger.info(resp.get(0));

        }catch  (BinanceDexApiException e) {
            logger.warn(e.getError());
            throw e;
        }
    }

    @Test(expectedExceptions = BinanceDexApiException.class, expectedExceptionsMessageRegExp = ".*Price:Zero/Negative Number.*")
    public void testNewOrderNegativePrice() throws IOException, NoSuchAlgorithmException{

        String symbol = "ADA.B-F2F_BNB";

        NewOrder no = new NewOrder();
        no.setTimeInForce(TimeInForce.GTE);
        no.setOrderType(OrderType.LIMIT);
        no.setSide(OrderSide.BUY);
        no.setPrice("-0.0042");
        no.setQuantity("1.0");
        no.setSymbol(symbol);

        TransactionOption options = TransactionOption.DEFAULT_INSTANCE;
        try {
            List<TransactionMetadata> resp = client.newOrder(no, wallet, options, true);
            logger.info(resp.get(0));

        }catch  (BinanceDexApiException e) {
            logger.warn(e.getError());
            throw e;
        }
    }

    @Test(expectedExceptions = BinanceDexApiException.class, expectedExceptionsMessageRegExp = ".*do not have enough token to lock.*")
    public void testNewOrderLargePrice() throws IOException, NoSuchAlgorithmException{

        String symbol = "ADA.B-F2F_BNB";

        NewOrder no = new NewOrder();
        no.setTimeInForce(TimeInForce.GTE);
        no.setOrderType(OrderType.LIMIT);
        no.setSide(OrderSide.BUY);
        no.setPrice("92233720368");
        no.setQuantity("1");
        no.setSymbol(symbol);

        TransactionOption options = TransactionOption.DEFAULT_INSTANCE;
        try {
            List<TransactionMetadata> resp = client.newOrder(no, wallet, options, true);
            logger.info(resp.get(0));

        }catch  (BinanceDexApiException e) {
            logger.warn(e.getError());
            throw e;
        }
    }

    @Test(expectedExceptions = BinanceDexApiException.class, expectedExceptionsMessageRegExp = ".*do not have enough token to lock.*")
    public void testNewOrderLargeAmount() throws IOException, NoSuchAlgorithmException{

        String symbol = "ADA.B-F2F_BNB";

        NewOrder no = new NewOrder();
        no.setTimeInForce(TimeInForce.GTE);
        no.setOrderType(OrderType.LIMIT);
        no.setSide(OrderSide.BUY);
        no.setPrice("0.0042");
        no.setQuantity("92233720368");
        no.setSymbol(symbol);

        TransactionOption options = TransactionOption.DEFAULT_INSTANCE;
        try {
            List<TransactionMetadata> resp = client.newOrder(no, wallet, options, true);
            logger.info(resp.get(0));

        }catch  (BinanceDexApiException e) {
            logger.warn(e.getError());
            throw e;
        }
    }
    @Test(expectedExceptions = BinanceDexApiException.class, expectedExceptionsMessageRegExp = ".*signature verification failed.*")
    public void testNewOrderFromOthers() throws IOException, NoSuchAlgorithmException{

        String symbol = "ADA.B-F2F_BNB";

        NewOrder no = new NewOrder();
        no.setTimeInForce(TimeInForce.GTE);
        no.setOrderType(OrderType.LIMIT);
        no.setSide(OrderSide.BUY);
        no.setPrice("0.0042");
        no.setQuantity("1");
        no.setSymbol(symbol);

        TransactionOption options = TransactionOption.DEFAULT_INSTANCE;
        try {
            List<TransactionMetadata> resp = client.newOrderFromOthers(no, wallet,options, true);
            System.out.println(resp.get(0));

        }catch  (BinanceDexApiException e) {
            logger.warn(e.getError());
            throw e;
        }
    }

    @Test(expectedExceptions = BinanceDexApiException.class, expectedExceptionsMessageRegExp = ".*do not have enough token to lock.*")
    public void testNewOrderMoreThanHave() throws IOException, NoSuchAlgorithmException{

        String symbol = "ADA.B-F2F_BNB";

        NewOrder no = new NewOrder();
        no.setTimeInForce(TimeInForce.GTE);
        no.setOrderType(OrderType.LIMIT);
        no.setSide(OrderSide.SELL);
        no.setPrice("0.0042");
        no.setQuantity("1035");
        no.setSymbol(symbol);

        TransactionOption options = TransactionOption.DEFAULT_INSTANCE;
        try {
            List<TransactionMetadata> resp = client.newOrder(no, wallet, options, true);
            logger.info(resp.get(0));

        }catch  (BinanceDexApiException e) {
            logger.warn(e.getError());
            throw e;
        }
    }

    @Test(expectedExceptions = BinanceDexApiException.class, expectedExceptionsMessageRegExp = ".*quantity.* is not rounded to lotSize.*")
    public void testNewOrderNotRoundQuantity() throws IOException, NoSuchAlgorithmException{

        String symbol = "ADA.B-F2F_BNB";

        NewOrder no = new NewOrder();
        no.setTimeInForce(TimeInForce.GTE);
        no.setOrderType(OrderType.LIMIT);
        no.setSide(OrderSide.BUY);
        no.setPrice("0.0042");
        no.setQuantity("0.1");
        no.setSymbol(symbol);

        TransactionOption options = TransactionOption.DEFAULT_INSTANCE;
        try {
            List<TransactionMetadata> resp = client.newOrder(no, toWallet, options, true);
            logger.info(resp.get(0));

        }catch  (BinanceDexApiException e) {
            logger.warn(e.getError());
            throw e;
        }
    }

    @Test(expectedExceptions = BinanceDexApiException.class, expectedExceptionsMessageRegExp = ".*Price:Zero/Negative Number.*")
    public void testNewOrderNotRoundPrice() throws IOException, NoSuchAlgorithmException{

        String symbol = "ADA.B-F2F_BNB";

        NewOrder no = new NewOrder();
        no.setTimeInForce(TimeInForce.GTE);
        no.setOrderType(OrderType.LIMIT);
        no.setSide(OrderSide.BUY);
        no.setPrice("0.000000009");
        no.setQuantity("1");
        no.setSymbol(symbol);

        TransactionOption options = TransactionOption.DEFAULT_INSTANCE;
        try {
            List<TransactionMetadata> resp = client.newOrder(no, toWallet, options, true);
            logger.info(resp.get(0));

        }catch  (BinanceDexApiException e) {
            logger.warn(e.getError());
            throw e;
        }
    }

    @Test
    public void testTradeWithOneself() throws IOException, NoSuchAlgorithmException{

        String symbol = "ADA.B-F2F_BNB";

        NewOrder no = new NewOrder();
        no.setTimeInForce(TimeInForce.GTE);
        no.setOrderType(OrderType.LIMIT);
        no.setPrice("0.0046");
        no.setQuantity("1");
        no.setSymbol(symbol);

        TransactionOption options = TransactionOption.DEFAULT_INSTANCE;
        try {
            no.setSide(OrderSide.BUY);
            List<TransactionMetadata> buyResp = client.newOrder(no, wallet, options, true);
            logger.info(buyResp.get(0));
            no.setSide(OrderSide.SELL);
            List<TransactionMetadata> sellResp = client.newOrder(no, wallet, options, true);
            logger.info(sellResp.get(0));

        }catch  (BinanceDexApiException e) {
            logger.warn(e.getError());
            throw e;
        }
    }


}
