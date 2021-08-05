package com.helio.WebApiService;


import com.helio.AppConfig.AppConstant;
import com.helio.RabbitServiceAPI.RabbitServiceRESTAPI;
import com.helio.RequestOB.RequestOB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    RabbitServiceRESTAPI rabbitMqService;
    HashMap maprequest = new HashMap<>();
    HashMap mapresponse = new HashMap<>();
    String valueresult = "noresponse";
    HttpHeaders responseHeaders = new HttpHeaders();
    int reqid;
    String signcalculation = "";
    BigDecimal Result = new BigDecimal("0.0");
    String restOfTheUrl;
    Logger Calcapilogger = LoggerFactory.getLogger(APIController.class);
    /**
     * Controller for handling all request for calculator API
     *
     */
    @GetMapping(path={ "/sum", "/subtract", "/divide", "/multiple"})
    @ResponseBody
    public ResponseEntity<?> calculationrequest(@RequestParam("a") BigDecimal avalue,
                                                @RequestParam("b") BigDecimal bvalue, HttpServletRequest request){

        restOfTheUrl = (String) request.getAttribute(
                HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
        reqid = new Random().nextInt(Integer.MAX_VALUE);


        Calcapilogger.info("APIHandler >> Request received for Calculator API");
        try {
            /**
             * Create object of calculator and set values
             *
             */
            Calcapilogger.info("APIHandler >> Request id is " + reqid);
            RequestOB calobj = new RequestOB(avalue, bvalue, Result, signcalculation);
            signcalculation = calobj.getsignfrompath(restOfTheUrl);
            calobj.setSign(signcalculation);
            Calcapilogger.info("APIHandler >> Request id: " + reqid + " - Request is for calculation of type " + signcalculation);
            Calcapilogger.info("APIHandler >> Request id: " + reqid + " - Request pre processing done successfuly.");
            Calcapilogger.info("APIHandler >> Request id: " + reqid + " - Request being sent to calculator. Using the value of A = " + calobj.getNumA().toString()
                    +" and value of B = " + calobj.getNumB().toString());

            /**
             * Sending Message to Queue, wait then receive response to send to client
             *
             */
            maprequest.clear();
            maprequest.put("requestid", reqid);
            maprequest.put("valueA", calobj.getNumA());
            maprequest.put("valueB", calobj.getNumB());
            maprequest.put("sign", calobj.getSign());
            rabbitMqService.sendAPIMessage(maprequest);

            /**
             * Wait for Async call of Calculator Service(Listener and Sender) and REST API (Listener)
             *
             */
           while (valueresult == "noresponse") {
               Thread.sleep(1000);
               valueresult = rabbitMqService.getRequestresult();
          }

            /**
             * Get the result value of the request from client
             * Setup response and request id to Header
             * Send response to client
             *
             */
            Calcapilogger.info("APIHandler >> Request id: " + reqid + " - Sending response to client");
            mapresponse.clear();
            mapresponse.put("result",valueresult);
            responseHeaders.set("Request_ID", reqid + "");
            valueresult = "noresponse";
            return new ResponseEntity<HashMap>( mapresponse , responseHeaders, HttpStatus.OK);
        } catch (Exception ex) {
            Calcapilogger.error("APIHandler >> Request id: " + reqid + " - Request received for Calculator API is bad request. Error: ", ex);
            return new ResponseEntity<String>("APIHandler >> Request id: " + reqid + AppConstant.MESSAGE_QUEUE_SEND_ERROR,
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
