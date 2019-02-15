package com.binance.dex.api.client.Broadcast;

import com.alibaba.fastjson.JSONObject;
import com.binance.dex.api.client.*;
import com.binance.dex.api.client.domain.*;
import com.binance.dex.api.client.domain.broadcast.Transfer;
import com.binance.dex.api.client.domain.broadcast.TransactionOption;
import org.testng.annotations.Test;
import org.testng.log4testng.Logger;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class TestTransfer {
    private static Logger logger = Logger.getLogger(Test.class);

    Wallet wallet = new Wallet("29d1f25e20f715579a4bc9f7d05bf85507abc5a24919e4114111fa3fc19b86ef",
            BinanceDexEnvironment.TEST_NET);

    BinanceDexApiRestClient client =
            BinanceDexApiClientFactory.newInstance().newRestClient(BinanceDexEnvironment.TEST_NET.getBaseUrl());

    Wallet toWallet = new Wallet("1bb30cb486f610f6bd6c8b8f7cbdce9e2d37e716e2cb7b82669330c5b126a574",
            BinanceDexEnvironment.TEST_NET);

    String toAddress = "tbnb1kwyz3z86v4r4zskffc6jaanu8pycfgckpjnvcf";


    @Test(expectedExceptions = BinanceDexApiException.class, expectedExceptionsMessageRegExp = ".*\"abci_code\":65547,\"message\":\"-100000000BNB\".*")
    public void testTransferNegativeNumber() throws IOException, NoSuchAlgorithmException {
        String symbol = "BNB";
        Transfer transfer = new Transfer();
        transfer.setCoin(symbol);
        transfer.setFromAddress(wallet.getAddress());
        transfer.setToAddress(toAddress);
        transfer.setAmount("-1.0");

        TransactionOption options = TransactionOption.DEFAULT_INSTANCE;
        try {
            List<TransactionMetadata> resp = client.transfer(transfer, wallet, options, true);
            logger.info(resp.get(0));

        } catch (BinanceDexApiException e) {
            logger.warn(e.getError());
            throw e;
        }
    }

    @Test(expectedExceptions = BinanceDexApiException.class, expectedExceptionsMessageRegExp = ".*\"abci_code\":65547,\"message\":\"0BNB\".*")
    public void testTransferZeroNumber() throws IOException, NoSuchAlgorithmException {
        String symbol = "BNB";
        Transfer transfer = new Transfer();
        transfer.setCoin(symbol);
        transfer.setFromAddress(wallet.getAddress());
        transfer.setToAddress(toAddress);
        transfer.setAmount("0");

        TransactionOption options = TransactionOption.DEFAULT_INSTANCE;
        try {
            List<TransactionMetadata> resp = client.transfer(transfer, wallet, options, true);
            logger.info(resp.get(0));

        } catch (BinanceDexApiException e) {
            logger.warn(e.getError());
            throw e;
        }
    }

    @Test(expectedExceptions = BinanceDexApiException.class, expectedExceptionsMessageRegExp = ".*public key of signature should not be nil.*")
    public void testTransferWithNoSign() throws IOException, NoSuchAlgorithmException {
        String symbol = "BNB";
        Transfer transfer = new Transfer();
        transfer.setCoin(symbol);
        transfer.setFromAddress(wallet.getAddress());
        transfer.setToAddress(toAddress);
        transfer.setAmount("1.0");

        TransactionOption options = TransactionOption.DEFAULT_INSTANCE;
        try {
            List<TransactionMetadata> resp = client.transferWithNoSign(transfer, wallet, options, true);
            logger.info(resp.get(0));

        } catch (BinanceDexApiException e) {
            logger.warn(e.getError());
            throw e;
        }
    }

    @Test(expectedExceptions = BinanceDexApiException.class, expectedExceptionsMessageRegExp = ".*public key of signature should not be nil.*")
    public void testTransferWithNoPubkey() throws IOException, NoSuchAlgorithmException {
        String symbol = "BNB";
        Transfer transfer = new Transfer();
        transfer.setCoin(symbol);
        transfer.setFromAddress(wallet.getAddress());
        transfer.setToAddress(toAddress);
        transfer.setAmount("1.0");

        TransactionOption options = TransactionOption.DEFAULT_INSTANCE;
        try {
            List<TransactionMetadata> resp = client.transferWithNoPubkey(transfer, wallet, options, true);
            logger.info(resp.get(0));

        } catch (BinanceDexApiException e) {
            logger.warn(e.getError());
            throw e;
        }
    }

    @Test(expectedExceptions = BinanceDexApiException.class, expectedExceptionsMessageRegExp = ".*65546.*")
    public void testTransferWrongCoin() throws IOException, NoSuchAlgorithmException {
        String symbol = "BBB";
        Transfer transfer = new Transfer();
        transfer.setCoin(symbol);
        transfer.setFromAddress(wallet.getAddress());
        transfer.setToAddress(toAddress);
        transfer.setAmount("1.0");

        TransactionOption options = TransactionOption.DEFAULT_INSTANCE;
        try {
            List<TransactionMetadata> resp = client.transfer(transfer, wallet, options, true);
            logger.info(resp.get(0));

        } catch (BinanceDexApiException e) {
            logger.warn(e.getError());
            throw e;
        }
    }

    @Test(expectedExceptions = BinanceDexApiException.class, expectedExceptionsMessageRegExp = ".*65546.*")
    public void testTransferMoreThanHave() throws IOException, NoSuchAlgorithmException {
        String symbol = "ADA.B-F2F";
        List<Balance> balance = client.getAccount(wallet.getAddress()).getBalances();
        JSONObject.toJSONString(balance);
        Transfer transfer = new Transfer();
        transfer.setCoin(symbol);
        transfer.setFromAddress(wallet.getAddress());
        transfer.setToAddress(toAddress);
        transfer.setAmount("3.0");
        TransactionOption options = TransactionOption.DEFAULT_INSTANCE;

        try {
            List<TransactionMetadata> resp = client.transfer(transfer, wallet, options, true);
            logger.info(resp.get(0));

        } catch (BinanceDexApiException e) {
            logger.warn(e.getError());
            throw e;
        }
    }

    @Test(expectedExceptions = BinanceDexApiException.class, expectedExceptionsMessageRegExp = ".*65547.*")
    public void testTransferLargeAmount() throws IOException, NoSuchAlgorithmException {
        String symbol = "BNB";
        List<Balance> balance = client.getAccount(wallet.getAddress()).getBalances();
        JSONObject.toJSONString(balance);
        Transfer transfer = new Transfer();
        transfer.setCoin(symbol);
        transfer.setFromAddress(wallet.getAddress());
        transfer.setToAddress(toAddress);
        transfer.setAmount("9223372036854775807");
        TransactionOption options = TransactionOption.DEFAULT_INSTANCE;

        try {
            List<TransactionMetadata> resp = client.transfer(transfer, wallet, options, true);
            logger.info(resp.get(0));

        } catch (BinanceDexApiException e) {
            logger.warn(e.getError());
            throw e;
        }
    }

    @Test(expectedExceptions = BinanceDexApiException.class, expectedExceptionsMessageRegExp = ".*65539.*")
    public void testTransferFromOthers() throws IOException, NoSuchAlgorithmException {
        String symbol = "BNB";
        List<Balance> balance = client.getAccount(wallet.getAddress()).getBalances();
        JSONObject.toJSONString(balance);
        Transfer transfer = new Transfer();
        transfer.setCoin(symbol);
        transfer.setFromAddress(toAddress);
        transfer.setToAddress(toAddress);
        transfer.setAmount("12");
        TransactionOption options = TransactionOption.DEFAULT_INSTANCE;

        try {
            List<TransactionMetadata> resp = client.transfer(transfer, wallet, options, true);
            logger.info(resp.get(0));

        } catch (BinanceDexApiException e) {
            logger.warn(e.getError());
            throw e;
        }
    }

    @Test(expectedExceptions = BinanceDexApiException.class, expectedExceptionsMessageRegExp = ".*65546.*")
    public void testTransferNotEnoughFee() throws IOException, NoSuchAlgorithmException {
        String symbol = "BNB";
        List<Balance> balance = client.getAccount(wallet.getAddress()).getBalances();
        Transfer transfer = new Transfer();
        transfer.setCoin(symbol);
        transfer.setFromAddress(toWallet.getAddress());
        transfer.setToAddress(toAddress);
        transfer.setAmount("10.995");  //transfer fee =0.00125
        TransactionOption options = TransactionOption.DEFAULT_INSTANCE;

        try {
            List<TransactionMetadata> resp = client.transfer(transfer, toWallet, options, true);
            logger.info(resp.get(0));

        } catch (BinanceDexApiException e) {
            logger.warn(e.getError());
            throw e;
        }
    }
}
