package com.blockchain.watertap.service;

import jep.Jep;
import jep.JepException;
import jep.SharedInterpreter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
public class Web3Service {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Value("${water.tab.test.python:}")
    public String pythonPath;

    @Value("${water.tab.transfer.python:}")
    private String transferPath;

    @Value("${water.tab.network:}")
    private String network;

    @Value("${water.tab.prinvate.key:}")
    private String privateKey;

    @Value("${water.tab.abi:}")
    private String abi;

    @Value("${water.tab.token.address:}")
    private String tokenAddress;

    @Value("${water.tab.blockchain.browser:}")
    private String blockChainBrowser;


    private ThreadLocal<Jep> tlInterp = new ThreadLocal<>();

    private Jep getPythonInterp() {
        if (tlInterp.get() == null) {
            Jep interp = null;
            try {
                interp = new SharedInterpreter();
                InputStream inputStream = getClass().getClassLoader().getResourceAsStream(transferPath);
                int length = inputStream.available();
                byte[] b = new byte[length];
                inputStream.read(b);
                inputStream.close();
                ;
                String pythonData = new String(b);
                interp.exec(pythonData);
                tlInterp.set(interp);
            } catch (Exception e) {
                if (interp != null) {
                    try {
                        interp.close();
                    } catch (JepException jepException) {
                        jepException.printStackTrace();
                    }
                }
                e.printStackTrace();
            }
        }
        return tlInterp.get();
    }

    public Object getTest(Integer a, Integer b) {
        Jep jep = getPythonInterp();
        try {
            jep.set("a", a);
            jep.set("b", b);
            Object ret = jep.getValue("add_num(a,b)");
            return ret;
        } catch (JepException jepException) {
            jepException.printStackTrace();
        }
        return null;
    }

    public Object web3Transfer(String toAddress,Integer transVale){
        Jep jep = getPythonInterp();
        try {
            jep.set("network", network);
            jep.set("token_address", tokenAddress);
            jep.set("abi", abi);
            jep.set("private_key", privateKey);
            jep.set("to_address", toAddress);
            jep.set("trans_value", transVale);
            Object ret = jep.getValue("transfer(network, token_address, abi, private_key, to_address, trans_value)");
            logger.info("transfer address:" + toAddress + ", transfer value:" + transVale + ",trasferHash:" + ret);
            return blockChainBrowser + ret;
        } catch (JepException jepException) {
            jepException.printStackTrace();
        }
        return null;
    }


}
