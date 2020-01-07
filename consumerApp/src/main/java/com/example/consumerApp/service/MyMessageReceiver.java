package com.example.consumerApp.service;

import org.springframework.jms.JmsException;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class MyMessageReceiver {

    @JmsListener(destination = "testQueue")
    public void processMessage(String data) {

        try{
            System.out.println("data: " + data);
        } catch (JmsException e){
            //ignore
        }
    }
}
