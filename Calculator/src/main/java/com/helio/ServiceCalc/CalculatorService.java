package com.helio.ServiceCalc;

import org.springframework.stereotype.Service;
import java.math.BigDecimal;



@Service
public class CalculatorService {
    private  BigDecimal sRresult ;
    private  BigDecimal sRcvalueA;
    private  BigDecimal sRcvalueB;
    private  String sRsign;

    public CalculatorService() {
    }

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
