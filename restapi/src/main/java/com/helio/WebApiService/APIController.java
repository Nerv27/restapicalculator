package com.helio.WebApiService;


import com.helio.AppConfig.AppConstant;
import com.helio.RequestOB.RequestOB;
import com.helio.ServiceCalc.CalculatorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Random;

@RestController
@RequestMapping(path="api/calculator")
public class APIController {

    Logger Calcapilogger = LoggerFactory.getLogger(APIController.class);


    @GetMapping(path={ "/sum", "/subtract", "/divide", "/multiple"})
    @ResponseBody
    public ResponseEntity<?> calculationrequest(@RequestParam("a") BigDecimal avalue,
                                                @RequestParam("b") BigDecimal bvalue, HttpServletRequest request){
        String signcalculation = "";
        BigDecimal Result = new BigDecimal("0.0");
        String restOfTheUrl = (String) request.getAttribute(
                HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
        HttpHeaders responseHeaders = new HttpHeaders();
        int reqid = new Random().nextInt(Integer.MAX_VALUE);
        HashMap map = new HashMap<>();

        Calcapilogger.info("Request received for Caculator API");
        try {
            Calcapilogger.info("Request is for caculation of type " + signcalculation);
            Calcapilogger.info("Request id is " + reqid);
            /*CrCalcapilogger.info("Request is for caculation of type " + signcalculation);eate object of calculator set values*/
            RequestOB calobj = new RequestOB(avalue, bvalue, Result, signcalculation);
            signcalculation = calobj.getsignfrompath(restOfTheUrl); // identify the type of calculation to via the url path used
            Calcapilogger.info("Request id: " + reqid + " - Request pre processing done successfuly.");
            Calcapilogger.info("Request id: " + reqid + " - Request being sent to calculator. Using the value of A = " + calobj.getNumA().toString()
                    +" and value of B = " + calobj.getNumB().toString());
            CalculatorService calculationrequest = new CalculatorService(avalue, bvalue, signcalculation);
            Calcapilogger.info("Request id: " + reqid + " - Request calculated successfuly.");
            calobj.setResult(calculationrequest.CalculationResult());
            //calobj.setSign(signcalculation);

            /* Sending to Message Queue, wait then get response */
            /*map.put("requestid", reqid);
            map.put("valueA", calobj.getNumA());
            map.put("valueB", calobj.getNumB());
            map.put("sign", calobj.getSign());*/
            map.put("Result", calobj.getResult());
            Calcapilogger.info("Request id: " + reqid + " - Request result is " + calobj.getResult().toString());
            responseHeaders.set("RequestID", reqid+""); //set unique id of request as new header key

            Calcapilogger.info("Request id: " + reqid + " - Sending response to client");
            return new ResponseEntity<HashMap>( map , responseHeaders, HttpStatus.OK);

        } catch (Exception ex) {
            Calcapilogger.error("Request id: " + reqid + " - Request received for Caculator API is bad request. Error: ", ex);
            return new ResponseEntity<String>(AppConstant.MESSAGE_QUEUE_SEND_ERROR,
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
