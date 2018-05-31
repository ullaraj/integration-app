package com.ig.integration.app.domain;



import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDateTime;

@JacksonXmlRootElement
public class Order {
    private String accont;


    private String market;


    private String action;


    private Integer size;


    private Long submittedAt;


    private Long receivedAt;

    public String getAccont() {
        return accont;
    }

    public void setAccont(String account) {
        this.accont = account;
    }

    public String getMarket() {
        return market;
    }

    public void setMarket(String market) {
        this.market = market;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Long getSubmittedAt() {
        return submittedAt;
    }

    public void setSubmittedAt(Long submittedAt) {
        this.submittedAt = submittedAt;
    }

    public Long getReceivedAt() {
        return receivedAt;
    }

    public void setReceivedAt(Long receivedAt) {
        this.receivedAt = receivedAt;
    }

    @Override
    public String toString() {
        return "Order{" +
                "accont='" + accont + '\'' +
                ", market='" + market + '\'' +
                ", action='" + action + '\'' +
                ", size=" + size +
                ", submittedAt=" + submittedAt +
                ", receivedAt=" + receivedAt +
                '}';
    }
}
