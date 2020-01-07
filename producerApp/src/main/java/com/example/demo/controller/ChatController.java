package com.example.demo.controller;

import com.example.demo.service.ContactService;
import org.apache.activemq.artemis.api.core.ActiveMQNotConnectedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.JmsException;
import org.springframework.web.bind.annotation.*;

import javax.jms.JMSException;
import java.io.IOException;

@RestController
public class ChatController {

    @Autowired
    private ContactService contactService;

    @RequestMapping(value = "/chat")
    public ResponseEntity<String> sendChatMessage() throws IOException, InterruptedException, JMSException {
        contactService.chat();
        Thread.sleep(1000);
        return new ResponseEntity<>("chat successfully", HttpStatus.ACCEPTED);
    }

    @RequestMapping(value = "/chatLoop")
    public ResponseEntity<String> sendChatMessagesInLoop(@RequestParam("loop") boolean loop,
                                                         @RequestParam("duration") int duration,
                                                         @RequestParam("sleep") int sleepMillis) throws IOException, InterruptedException {
        if (!loop) {
            try {
                contactService.chat();
            } catch (JMSException e) {

            }
            return new ResponseEntity<>("chat successfully", HttpStatus.ACCEPTED);
        } else {
            int start = 0;
            while (start <= duration) {
                try {
                    contactService.chat();
                } catch (Exception e) {
                System.out.println("JmsException " + e.getMessage());
                }
                Thread.sleep(sleepMillis);
                start++;
            }
            return new ResponseEntity<>("chat successfully", HttpStatus.ACCEPTED);
        }
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(InterruptedException.class)
    public String handleInterruptedException(InterruptedException e) {
        System.out.println(e);
        return "IOException or InterruptedException";
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(JMSException.class)
    public String handleJmsException(JMSException e) {
        System.out.println(e);
        e.printStackTrace();
        return "JmsException";
    }
}
