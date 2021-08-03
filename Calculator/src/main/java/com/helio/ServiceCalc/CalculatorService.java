package com.helio.ServiceCalc;

import java.math.BigDecimal;

public class CalculatorService {
    private  BigDecimal sRresult = new BigDecimal("0.0");;
    private  BigDecimal sRcvalueA;
    private  BigDecimal sRcvalueB;
    private  String sRsign;

    public CalculatorService(BigDecimal numA, BigDecimal numB, String sign) {
        this.sRcvalueA = numA;
        this.sRcvalueB = numB;
        this.sRsign = sign;

    }

    public BigDecimal CalculationResult() {


        switch (sRsign) {
            case "+":
                sRresult = sRcvalueA.add(sRcvalueB);
                break;
            case "-":
                sRresult = sRcvalueA.subtract(sRcvalueB);
                break;
            case "/":
                sRresult = sRcvalueA.divide(sRcvalueB);
                break;
            case "*":
                sRresult = sRcvalueA.multiply(sRcvalueB);
                break;
        }

        return sRresult;
    }
}
