package com.apinizer.example.api;


import com.apinizer.example.api.rest.EncDecMessage;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Base64;

@RestController
@RequestMapping("/api")
public class EncDecApiResource {

    public static final Logger logger = LoggerFactory.getLogger(EncDecApiResource.class);

    @RequestMapping(value = "/enc/", method = RequestMethod.POST)
    public ResponseEntity<?> enc(@RequestBody String encDecMessageStr) {
        EncDecMessage encDecMessage = null;
        try {
            encDecMessage = new ObjectMapper().readValue(encDecMessageStr, EncDecMessage.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String response = Base64.getEncoder().encodeToString(encDecMessage.getData().getBytes());
        return new ResponseEntity<String>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/dec/", method = RequestMethod.POST)
    public ResponseEntity<?> dec(@RequestBody String encDecMessageStr) {
        EncDecMessage encDecMessage = null;
        try {
            encDecMessage = new ObjectMapper().readValue(encDecMessageStr, EncDecMessage.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String json = new String(Base64.getDecoder().decode(encDecMessage.getData().getBytes()));
        json = json.replace("\"", "\\\"");
        String response = "{\"ProcessResult\": \"" + json + "\"}";
        return new ResponseEntity<String>(response, HttpStatus.OK);
    }

    public static void main(String[] args) {
        String message = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:tem=\"http://tempuri.org/\">\n" +
                "   <soapenv:Header/>\n" +
                "   <soapenv:Body>\n" +
                "      <tem:Add>\n" +
                "         <tem:intA>1</tem:intA>\n" +
                "         <tem:intB>2</tem:intB>\n" +
                "      </tem:Add>\n" +
                "   </soapenv:Body>\n" +
                "</soapenv:Envelope>";
        System.out.println(Base64.getEncoder().encodeToString(message.getBytes()));

        message = "PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0idXRmLTgiPz48c29hcDpFbnZlbG9wZSB4bWxuczpzb2FwPSJodHRwOi8vc2NoZW1hcy54bWxzb2FwLm9yZy9zb2FwL2VudmVsb3BlLyIgeG1sbnM6eHNpPSJodHRwOi8vd3d3LnczLm9yZy8yMDAxL1hNTFNjaGVtYS1pbnN0YW5jZSIgeG1sbnM6eHNkPSJodHRwOi8vd3d3LnczLm9yZy8yMDAxL1hNTFNjaGVtYSI+PHNvYXA6Qm9keT48QWRkUmVzcG9uc2UgeG1sbnM9Imh0dHA6Ly90ZW1wdXJpLm9yZy8iPjxBZGRSZXN1bHQ+MzwvQWRkUmVzdWx0PjwvQWRkUmVzcG9uc2U+PC9zb2FwOkJvZHk+PC9zb2FwOkVudmVsb3BlPg==";
        System.out.println(new String(Base64.getDecoder().decode(message.getBytes())));
    }

}
