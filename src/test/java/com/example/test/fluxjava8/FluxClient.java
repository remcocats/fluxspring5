package com.example.test.fluxjava8;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

public class FluxClient {

    @Test
    public void testClient() throws InterruptedException {

        final WebClient producerClient = WebClient.create("http://localhost:8080/api");

        Flux<String> entries = producerClient.get()
                .accept(MediaType.TEXT_EVENT_STREAM)
                .exchange()
                .flatMapMany(clientResponse -> clientResponse.bodyToFlux(String.class));

        entries.subscribe(evt -> System.out.println("data: " + evt));

        Thread.sleep(20500L);
    }
}
