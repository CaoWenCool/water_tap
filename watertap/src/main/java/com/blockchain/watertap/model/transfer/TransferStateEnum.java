package com.blockchain.watertap.model.transfer;


public enum TransferStateEnum {
    READY(1), SUCCESS(2), FAIL(3);

    private Integer state;

    TransferStateEnum(Integer state) {
        this.state = state;
    }

    public Integer getState() {
        return state;
    }
}
