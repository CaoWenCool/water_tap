package com.blockchain.watertap.mapper.opensea.model;

import java.time.LocalDateTime;

public class TransferPO {

    private Integer id;

    private String toAddress;

    private Integer transferVal;

    private LocalDateTime transferTime;

    private LocalDateTime updateTime;

    private String txHash;

    private Integer state;

    private String network;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getToAddress() {
        return toAddress;
    }

    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }

    public Integer getTransferVal() {
        return transferVal;
    }

    public void setTransferVal(Integer transferVal) {
        this.transferVal = transferVal;
    }

    public LocalDateTime getTransferTime() {
        return transferTime;
    }

    public void setTransferTime(LocalDateTime transferTime) {
        this.transferTime = transferTime;
    }

    public String getTxHash() {
        return txHash;
    }

    public void setTxHash(String txHash) {
        this.txHash = txHash;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getNetwork() {
        return network;
    }

    public void setNetwork(String network) {
        this.network = network;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }
}
