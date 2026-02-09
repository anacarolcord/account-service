package com.anadev.accountservice.messaging;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class WarningLimitProducer {

    private final StreamBridge streamBridge;
    private static final String MESSAGE = "Seu limite foi atingido!";

    public void publish(){

        streamBridge.send("warning", MESSAGE);

    }


}
