package com.example.test.fluxjava8;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.codec.ServerSentEvent;

import java.time.Duration;

import reactor.core.publisher.Flux;

@SpringBootApplication
@RestController
public class Fluxjava8Application {

    public static void main(String[] args) {
		SpringApplication.run(Fluxjava8Application.class, args);
	}

    /**
     * The javascript new EventSource(url) only work with the event-stream data type
     *
     * Not with the @GetMapping(value = "/api", produces = "application/stream+json")
     *
     *
     * @return
     */
	@GetMapping(value = "/api", produces = "text/event-stream")
    public Flux<ServerSentEvent<Integer>> rootGet() {
        Flux<java.lang.Integer> sampleFlux = Flux.just(1, 9, 4, 7, 6, 2, 2, 7, 3, 4, 8);

        final Flux<Long> interval = Flux.interval(Duration.ofSeconds(1));

        return interval.zipWith(sampleFlux).map(data ->
                ServerSentEvent.<Integer>builder()
                        .id(Long.toString(data.getT1()))
                        .data(data.getT2())
                        .build());
    }
}
