package com.blockchain.watertap.model.response;


import com.blockchain.watertap.mapper.opensea.model.OpeasenPO;

public class AwardInfoResponse {

    private OpeasenPO price;

    private OpeasenPO highPrice;

    private OpeasenPO lowPrice;

    public OpeasenPO getPrice() {
        return price;
    }

    public void setPrice(OpeasenPO price) {
        this.price = price;
    }

    public OpeasenPO getHighPrice() {
        return highPrice;
    }

    public void setHighPrice(OpeasenPO highPrice) {
        this.highPrice = highPrice;
    }

    public OpeasenPO getLowPrice() {
        return lowPrice;
    }

    public void setLowPrice(OpeasenPO lowPrice) {
        this.lowPrice = lowPrice;
    }
}
