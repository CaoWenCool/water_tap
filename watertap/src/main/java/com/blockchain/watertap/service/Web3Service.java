package com.blockchain.watertap.service;

import com.blockchain.watertap.exception.XCloudCommonExceptions;
import com.blockchain.watertap.mapper.opensea.mapper.TransferMapper;
import com.blockchain.watertap.mapper.opensea.model.TransferPO;
import com.blockchain.watertap.model.request.ListRequest;
import com.blockchain.watertap.model.response.TransferResponse;
import com.blockchain.watertap.model.transfer.TransferStateEnum;
import com.blockchain.watertap.util.AddressCheck;
import com.blockchain.watertap.util.LocalDateTimeUtil;
import jep.Jep;
import jep.JepException;
import jep.SharedInterpreter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.*;

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

    @Autowired
    TransferMapper transferMapper;

    private static final String PENDING = "Pending";

    private static final String SEND_FAIL = "Send Fail";

    private static final Integer START_TRANSFER = 1;

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
                String pythonData = new String(b);
                logger.error(pythonData);
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

    public List<TransferResponse> transferHis() {
        ListRequest listRequest = new ListRequest("desc", "id", 1, 5);
        List<TransferPO> transferPOList = transferMapper.listByPage(listRequest);
        List<TransferResponse> transferResponses = new ArrayList<>();
        if (null == transferPOList || transferPOList.size() == 0) {
            return transferResponses;
        }
        for (TransferPO eachTransfer : transferPOList) {
            TransferResponse transferResponse = new TransferResponse();
            transferResponse.setToAddress(eachTransfer.getToAddress());
            transferResponse.setNumber(eachTransfer.getTransferVal());
            if (TransferStateEnum.READY.getState().equals(eachTransfer.getState())) {
                transferResponse.setTime(PENDING);
            } else if(TransferStateEnum.SUCCESS.getState().equals(eachTransfer.getState())){
                LocalDateTime transferTime = eachTransfer.getTransferTime();
                LocalDateTime nowTime = LocalDateTime.now();
                transferResponse.setTime(LocalDateTimeUtil.calTimeDiff(transferTime, nowTime));
                StringBuffer sb = new StringBuffer();
                sb.append(eachTransfer.getNetwork());
                sb.append(eachTransfer.getTxHash());
                transferResponse.setUrl(sb.toString());
            }else {
                transferResponse.setTime(SEND_FAIL);
            }
            transferResponses.add(transferResponse);
        }
        return transferResponses;
    }

    public void transferReady(String toAddress, Integer transVale) {
        // 检验地址的合法性
        if(!AddressCheck.isETHVaildAddress(toAddress)){
            throw new XCloudCommonExceptions.RequestInvalidException("This is not a valid address");
        }
        // 判断是否已经存在
        List<TransferPO> transferPOHis = transferMapper.getByState(TransferStateEnum.READY.getState(), toAddress);
        if (null != transferPOHis && transferPOHis.size() > 0) {
            throw new XCloudCommonExceptions.RequestInvalidException("this address is ready to send,please do not submit again!");
        }
        // 判断时间
        LocalDateTime yesDateTime = LocalDateTime.now().plusDays(-1);
        List<TransferPO> transferPOList = transferMapper.getByTransferTime(yesDateTime, toAddress);
        if (null != transferPOList && transferPOList.size() > 0) {
            throw new XCloudCommonExceptions.RequestInvalidException("every address only get once at the same day!");
        }
        Random rand = new Random();
        Integer transfer = START_TRANSFER + rand.nextInt(transVale);
        // 插入数据库
        TransferPO transferPO = new TransferPO();
        transferPO.setToAddress(toAddress);
        transferPO.setTransferVal(transfer);
        transferPO.setNetwork(blockChainBrowser);
        transferPO.setState(TransferStateEnum.READY.getState());
        transferPO.setUpdateTime(LocalDateTime.now());
        transferMapper.insert(transferPO);
    }

    @Async("asyncTaskExecutor")
    public void checkAndSend() {
        List<TransferPO> transferPOHis = transferMapper.getByState(TransferStateEnum.READY.getState(), null);
        if (null == transferPOHis || transferPOHis.size() == 0) {
            return;
        }
        // 去重，防止重发
        Map<String, TransferPO> toAddressMap = new HashMap<>();
        for (TransferPO eachTransfer : transferPOHis) {
            if (toAddressMap.containsKey(eachTransfer.getToAddress())) {
                continue;
            }
            toAddressMap.put(eachTransfer.getToAddress(), eachTransfer);
        }
        Iterator<Map.Entry<String, TransferPO>> entries = toAddressMap.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry<String, TransferPO> entry = entries.next();
            web3Transfer(entry.getValue());
        }
    }


    public void web3Transfer(TransferPO transferPO) {
        Jep jep = getPythonInterp();
        Integer state = TransferStateEnum.FAIL.getState();
        String txHash = null;
        try {
            jep.set("network", network);
            jep.set("token_address", tokenAddress);
            jep.set("abi", abi);
            jep.set("private_key", privateKey);
            jep.set("to_address", transferPO.getToAddress());
            jep.set("trans_value", transferPO.getTransferVal());
            Object ret = jep.getValue("transfer(network, token_address, abi, private_key, to_address, trans_value)");
            logger.info("transfer address:" + transferPO.getToAddress() + ", transfer value:" + transferPO.getTransferVal() + ",trasferHash:" + ret);
            // 进行更新
            txHash = String.valueOf(ret);
            if (!txHash.equals("-1")) {
                state = TransferStateEnum.SUCCESS.getState();
            } else {
                logger.error("The from accound of balance not have enough token!!!");
            }
        } catch (JepException jepException) {
            logger.error(jepException.getMessage());
            logger.info("transfer address:" + transferPO.getToAddress() + ", transfer value:" + transferPO.getTransferVal() + ",trasferHash:" + txHash);
        }
        transferPO.setState(state);
        transferPO.setTransferTime(LocalDateTime.now());
        transferPO.setTxHash(txHash);
        transferMapper.update(transferPO);
    }


}
