package com.codefarm;

import org.junit.jupiter.api.Test;

public class EventProducerTest {

    EventProducer eventProducer = new EventProducer();

    @Test
    void sendEventTest(){
        eventProducer.sendEvent("321123 My first event from super java project API...");
    }
}
