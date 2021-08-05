package com.helio.Calculator;

import java.math.BigDecimal;

public class CalculatorOb {
    private  BigDecimal sRresult ;
    private  BigDecimal sRcvalueA;
    private  BigDecimal sRcvalueB;
    private  String sRsign;

    public CalculatorOb() {
    }

    public CalculatorOb(BigDecimal numA, BigDecimal numB, String sign) {
        this.sRcvalueA = numA;
        this.sRcvalueB = numB;
        this.sRsign = sign;

    }
    /**
     * Calculate tow values based on sign
     *
     */
    public BigDecimal CalculationResult() {
        switch (sRsign) {
            case "sum":
                sRresult = sRcvalueA.add(sRcvalueB);
                break;
            case "subtract":
                sRresult = sRcvalueA.subtract(sRcvalueB);
                break;
            case "divide":
                sRresult = sRcvalueA.divide(sRcvalueB);
                break;
            case "multiple":
                sRresult = sRcvalueA.multiply(sRcvalueB);
                break;
        }
        return sRresult;
    }
}
