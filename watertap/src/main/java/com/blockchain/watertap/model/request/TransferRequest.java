package com.blockchain.watertap.model.request;

public class TransferRequest {

    private String network;
    private String token_address;
    private String abi;
    private String private_key;
    private String to_address;
    private Integer trans_value;

    public String getNetwork() {
        return network;
    }

    public void setNetwork(String network) {
        this.network = network;
    }

    public String getToken_address() {
        return token_address;
    }

    public void setToken_address(String token_address) {
        this.token_address = token_address;
    }

    public String getAbi() {
        return abi;
    }

    public void setAbi(String abi) {
        this.abi = abi;
    }

    public String getPrivate_key() {
        return private_key;
    }

    public void setPrivate_key(String private_key) {
        this.private_key = private_key;
    }

    public String getTo_address() {
        return to_address;
    }

    public void setTo_address(String to_address) {
        this.to_address = to_address;
    }

    public Integer getTrans_value() {
        return trans_value;
    }

    public void setTrans_value(Integer trans_value) {
        this.trans_value = trans_value;
    }
}
