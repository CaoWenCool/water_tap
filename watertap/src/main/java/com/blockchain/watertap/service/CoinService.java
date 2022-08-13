package com.blockchain.watertap.service;

import com.blockchain.watertap.daemon.task.BtcTask;
import com.blockchain.watertap.daemon.task.EthTask;
import com.blockchain.watertap.model.response.AwardInfoResponse;
import com.blockchain.watertap.model.response.CoinPriceResponse;
import com.blockchain.watertap.model.response.EthAddressResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
public class CoinService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${eth.address:0x82d30797cc1b191dcdebc6c2befe820ec8efe9cb}")
    public String ethAddress;

    public AwardInfoResponse getAwardInfoResponse(){
        AwardInfoResponse awardInfoResponse = new AwardInfoResponse();
        awardInfoResponse.setPrice(BtcTask.getPriceOpeasenPO());
        awardInfoResponse.setHighPrice(BtcTask.getHighPriceOpeasonPO());
        awardInfoResponse.setLowPrice(BtcTask.getLowPriceOpeasonPO());
        return awardInfoResponse;
    }

    public EthAddressResponse getEthNumber(){
        EthAddressResponse ethAddressResponse = new EthAddressResponse();
        StringBuffer sb = new StringBuffer();
        sb.append("<a href='https://etherscan.io/address/");
        sb.append(ethAddress);
        sb.append("'>");
        sb.append(ethAddress);
        sb.append("</a>");
        ethAddressResponse.setAddress(sb.toString());
        ethAddressResponse.setBalance(EthTask.getEthBalance());
        return ethAddressResponse;
    }

    public CoinPriceResponse getBtcPriceInfo() {
        CoinPriceResponse coinPriceResponse = new CoinPriceResponse();
        coinPriceResponse.setPrice(BtcTask.getPrice());
        coinPriceResponse.setLowPrice(BtcTask.getLowPrice());
        coinPriceResponse.setHightPirce(BtcTask.getHighPricre());
        return coinPriceResponse;
    }
}
