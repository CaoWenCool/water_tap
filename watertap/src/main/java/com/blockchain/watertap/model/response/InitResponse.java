package com.blockchain.watertap.model.response;

import java.util.List;

public class InitResponse {

    private Boolean clickDownload = false;

    private List<TransferResponse> transferResponses;


    public Boolean getClickDownload() {
        return clickDownload;
    }

    public void setClickDownload(Boolean clickDownload) {
        this.clickDownload = clickDownload;
    }

    public List<TransferResponse> getTransferResponses() {
        return transferResponses;
    }

    public void setTransferResponses(List<TransferResponse> transferResponses) {
        this.transferResponses = transferResponses;
    }
}
