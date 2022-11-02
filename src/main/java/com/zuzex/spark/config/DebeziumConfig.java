package com.zuzex.spark.config;

import com.zuzex.spark.config.props.AppProperties;
import com.zuzex.spark.utils.JsonUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Configuration
@RequiredArgsConstructor
public class DebeziumConfig {

    private final WebClient webClient;
    private final AppProperties appProperties;
    private final DataSourceProperties dataSourceProperties;

    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        this.webClient
                .post()
                .uri(this.appProperties.getDebeziumUrl())
                .bodyValue(JsonUtils.getDebeziumJson(dataSourceProperties, appProperties))
                .exchangeToMono(clientResponse -> {
                    if (clientResponse.rawStatusCode() != 200) {
                       return clientResponse.bodyToMono(String.class)
                                .handle((body, sink) -> sink.next(new RuntimeException("Debezium error: " + body)));
                    }
                    return Mono.empty();
                })
                .block();
    }
}
