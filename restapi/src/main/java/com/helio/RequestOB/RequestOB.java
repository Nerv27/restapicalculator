package com.helio.RequestOB;

import java.math.BigDecimal;

public class RequestOB {
    private BigDecimal numA;
    private BigDecimal numB;
    private BigDecimal result;
    private String sign;

    public RequestOB() {
    }

    public RequestOB(BigDecimal numA, BigDecimal numB, BigDecimal result, String sign) {
        this.numA = numA;
        this.numB = numB;
        this.result = result;
        this.sign = sign;
    }


    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public BigDecimal getNumA() {
        return numA;
    }

    public void setNumA(BigDecimal numA) {
        this.numA = numA;
    }

    public BigDecimal getNumB() {
        return numB;
    }

    public void setNumB(BigDecimal numB) {
        this.numB = numB;
    }

    public BigDecimal getResult() {
        return result;
    }

    public void setResult(BigDecimal result) {
        this.result = result;
    }

    public String getsignfrompath(String urlpath){
        int strlength = urlpath.length();
        String checkpath =  urlpath.substring(urlpath.lastIndexOf("/")+1,strlength);
        String sign = "";
        switch (checkpath) {
            case "sum":
                sign = "+";
                break;
            case "subtract":
                sign = "-";
                break;
            case "divide":
                sign = "/";
                break;
            case "multiple":
                sign = "*";
                break;
        }
        return sign;
    }
}
