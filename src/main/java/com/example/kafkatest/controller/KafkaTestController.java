package com.example.kafkatest.controller;

import com.example.kafkatest.producer.KafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Signature;
import java.util.function.Consumer;

@RestController
public class KafkaTestController {

    @Autowired
    private KafkaProducer kafkaProducer;


    @GetMapping("/pubish")
    public ResponseEntity<String> sentMsg(@RequestParam("msg") String msg){

        //perofmr intal requrest processing
        Consumer<String> process =  this::processMsg;
        Consumer<String> logging =  this::log;
        Consumer<String> requestHandler =  process.andThen(logging);
        requestHandler.accept(msg);
        return ResponseEntity.ok("Message is sent");
    }

    private void processMsg(String msg){
        kafkaProducer.sendMessage(msg);
    }

    private void log(String str){
        System.out.println(str);
    }
}
