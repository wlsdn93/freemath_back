package com.math.weakness.config;

import static org.springframework.http.HttpHeaders.ACCEPT;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import java.time.Duration;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

@Slf4j
@Configuration
public class WebClientConfig {
    private static final Integer MEMORY_BUFFER = 100 * 1024 * 1024;

    private static final Integer CONNECTION_TIMEOUT = 1000 * 5;
    private static final Integer READ_TIMEOUT = 1000 * 5;
    private static final Integer WRITE_TIMEOUT = 1000 * 5;
    @Bean
    public WebClient webClient() {

        ExchangeStrategies strategies = ExchangeStrategies.builder().codecs(clientCodecConfigurer -> clientCodecConfigurer
                .defaultCodecs()
                .maxInMemorySize(MEMORY_BUFFER))
            .build();
        HttpClient httpClient = HttpClient.create()
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, CONNECTION_TIMEOUT)
            .responseTimeout(Duration.ofMillis(READ_TIMEOUT))
            .doOnConnected(conn -> {
                conn.addHandlerLast(new ReadTimeoutHandler(READ_TIMEOUT, TimeUnit.MILLISECONDS))
                    .addHandlerLast(new WriteTimeoutHandler(WRITE_TIMEOUT, TimeUnit.MILLISECONDS));
            });

        return WebClient.builder()
            .exchangeStrategies(strategies)
            .clientConnector(new ReactorClientHttpConnector(httpClient))
            .defaultHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
            .defaultHeader(ACCEPT, APPLICATION_JSON_VALUE)
            .build();
    }
}