package com.binance.dex.api.client.Broadcast;

import com.binance.dex.api.client.*;
import com.binance.dex.api.client.domain.TransactionMetadata;
import com.binance.dex.api.client.domain.broadcast.CancelOrder;
import com.binance.dex.api.client.domain.broadcast.TokenFreeze;
import com.binance.dex.api.client.domain.broadcast.TokenUnfreeze;
import com.binance.dex.api.client.domain.broadcast.TransactionOption;
import org.testng.annotations.Test;
import org.testng.log4testng.Logger;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class TestFreezeToken {
    private static Logger logger = Logger.getLogger(Test.class);

    Wallet wallet = new Wallet("29d1f25e20f715579a4bc9f7d05bf85507abc5a24919e4114111fa3fc19b86ef",
            BinanceDexEnvironment.TEST_NET);
    BinanceDexApiRestClient client =
            BinanceDexApiClientFactory.newInstance().newRestClient(BinanceDexEnvironment.TEST_NET.getBaseUrl());
    Wallet toWallet = new Wallet("1bb30cb486f610f6bd6c8b8f7cbdce9e2d37e716e2cb7b82669330c5b126a574",
            BinanceDexEnvironment.TEST_NET);

    @Test(expectedExceptions = BinanceDexApiException.class, expectedExceptionsMessageRegExp = ".*amount should be more than 0.*")
    public void testFreezeTokenIllegalNumber() throws IOException, NoSuchAlgorithmException{

        String symbol = "BNB";
        TokenFreeze freeze = new TokenFreeze();
        freeze.setSymbol(symbol);
        freeze.setAmount("0");
        TransactionOption options = new TransactionOption("test", 1, null);
        try {
            List<TransactionMetadata> resp = client.freeze(freeze, wallet, options, true);
           logger.info(resp.get(0));
        }catch  (BinanceDexApiException e) {
            logger.warn(e.getError());
        }

        freeze.setAmount("-1");
        try {
            List<TransactionMetadata> resp = client.freeze(freeze, wallet, options, true);
            logger.info(resp.get(0));
        }catch  (BinanceDexApiException e) {
            logger.warn(e.getError());
            throw e;
        }
    }

    @Test(expectedExceptions = BinanceDexApiException.class, expectedExceptionsMessageRegExp = ".*amount should be more than 0.*")
    public void testUnFreezeTokenIllegalNumber() throws IOException, NoSuchAlgorithmException{

        String symbol = "BNB";
        TokenUnfreeze unfreeze = new TokenUnfreeze();
        unfreeze.setSymbol(symbol);
        unfreeze.setAmount("0");
        TransactionOption options = new TransactionOption("test", 1, null);

        try {
            List<TransactionMetadata> resp = client.unfreeze(unfreeze, wallet, options, true);
            logger.info(resp.get(0));
        }catch  (BinanceDexApiException e) {
            logger.warn(e.getError());
        }

        unfreeze.setAmount("-1");
        try {
            List<TransactionMetadata> resp = client.unfreeze(unfreeze, wallet, options, true);
           logger.info(resp.get(0));
        }catch  (BinanceDexApiException e) {
            logger.warn(e.getError());
            throw e;
        }
    }
//
//    @Test(expectedExceptions = BinanceDexApiException.class, expectedExceptionsMessageRegExp = ".*amount should be more than 0.*")
//    public void testBurnTokenIllegalNumber() throws IOException, NoSuchAlgorithmException{
//
//        String symbol = "BNB";
//        TokenUnfreeze unfreeze = new TokenUnfreeze();
//        unfreeze.setSymbol(symbol);
//        unfreeze.setAmount("0");
//        TransactionOption options = new TransactionOption("test", 1, null);
//
//        try {
//            List<TransactionMetadata> resp = client.unfreeze(unfreeze, wallet, options, true);
//            logger.info(resp.get(0));
//        }catch  (BinanceDexApiException e) {
//            logger.warn(e.getError());
//        }
//
//        unfreeze.setAmount("-1");
//        try {
//            List<TransactionMetadata> resp = client.unfreeze(unfreeze, wallet, options, true);
//            logger.info(resp.get(0));
//        }catch  (BinanceDexApiException e) {
//            logger.warn(e.getError());
//            throw e;
//        }
//    }


    @Test(expectedExceptions = BinanceDexApiException.class, expectedExceptionsMessageRegExp = ".*do not have enough token to unfreeze.*")
    public void testUnFreezeNonExistToken() throws IOException, NoSuchAlgorithmException{

        String symbol = "ADD.B-C2F";
        TokenUnfreeze unfreeze = new TokenUnfreeze();
        unfreeze.setSymbol(symbol);
        unfreeze.setAmount("5");
        TransactionOption options = new TransactionOption("test", 1, null);

        try {
            List<TransactionMetadata> resp = client.unfreeze(unfreeze, wallet, options, true);
            logger.info(resp.get(0));
        }catch  (BinanceDexApiException e) {
            logger.warn(e.getError());
            throw e ;
        }
    }

    @Test(expectedExceptions = BinanceDexApiException.class, expectedExceptionsMessageRegExp = ".*do not have enough token to freeze.*")
    public void testFreezeNonExistToken() throws IOException, NoSuchAlgorithmException{

        String symbol = "ADD.B-C2F";
        TokenFreeze freeze = new TokenFreeze();
        freeze.setSymbol(symbol);
        freeze.setAmount("2");
        TransactionOption options = new TransactionOption("test", 1, null);
        try {
            List<TransactionMetadata> resp = client.freeze(freeze, wallet, options, true);
            logger.info(resp.get(0));
        }catch  (BinanceDexApiException e) {
            logger.warn(e.getError());
            throw e;
        }
    }

    @Test(expectedExceptions = BinanceDexApiException.class, expectedExceptionsMessageRegExp = ".*do not have enough token to freeze.*")
    public void testFreezeTokenMoreThanHave() throws IOException, NoSuchAlgorithmException{

        String symbol = "ADA.B-F2F";
        TokenFreeze freeze = new TokenFreeze();
        freeze.setSymbol(symbol);
        freeze.setAmount("2000");
        TransactionOption options = new TransactionOption("test", 1, null);
        try {
            List<TransactionMetadata> resp = client.freeze(freeze, wallet, options, true);
            logger.info(resp.get(0));
        }catch  (BinanceDexApiException e) {
            logger.warn(e.getError());
            throw e;
        }
    }

    @Test(expectedExceptions = BinanceDexApiException.class, expectedExceptionsMessageRegExp = ".*do not have enough token to unfreeze.*")
    public void testUnFreezeTokenMoreThanFrozen() throws IOException, NoSuchAlgorithmException{

        String symbol = "ADA.B-F2F";
        TokenUnfreeze unfreeze = new TokenUnfreeze();
        unfreeze.setSymbol(symbol);
        unfreeze.setAmount("200");
        TransactionOption options = new TransactionOption("test", 1, null);

        try {
            List<TransactionMetadata> resp = client.unfreeze(unfreeze, wallet, options, true);
            logger.info(resp.get(0));
        }catch  (BinanceDexApiException e) {
            logger.warn(e.getError());
            throw e ;
        }
    }


}

