package com.apinizer.example.api.rest;

import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;

public class EncDecMessage implements Serializable {

    @JsonProperty("SlotNo")
    private String slotNo;
    @JsonProperty("KeyNamePRI")
    private String keyNamePRI;
    @JsonProperty("KeyNameSIM")
    private String keyNameSIM;
    @JsonProperty("MobileToken")
    private String mobileToken;
    @JsonProperty("Data")
    private String data;

    public String getSlotNo() {
        return slotNo;
    }

    public void setSlotNo(String slotNo) {
        this.slotNo = slotNo;
    }

    public String getKeyNamePRI() {
        return keyNamePRI;
    }

    public void setKeyNamePRI(String keyNamePRI) {
        this.keyNamePRI = keyNamePRI;
    }

    public String getKeyNameSIM() {
        return keyNameSIM;
    }

    public void setKeyNameSIM(String keyNameSIM) {
        this.keyNameSIM = keyNameSIM;
    }

    public String getMobileToken() {
        return mobileToken;
    }

    public void setMobileToken(String mobileToken) {
        this.mobileToken = mobileToken;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
