package com.zuzex.spark.mapper;

import com.zuzex.spark.config.props.AppProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.shaded.org.apache.commons.lang3.StringUtils;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

// https://spark.apache.org/docs/latest/structured-streaming-kafka-integration.html
@Slf4j
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "app.startType", havingValue = "READ_KAFKA")
public class KafkaSaveDatabaseMapper {
    private final SparkSession sparkSession;
    private final AppProperties appProperties;

    @PostConstruct
    void init() {
        log.info("Mapper started: {}", this.getClass());
    }

    @EventListener(ApplicationReadyEvent.class)
    public void event() {
        final Map<String, String> options = this.fillMap(new HashMap<>());
        final Dataset<Row> readSet = sparkSession.read()
                .format("kafka")
                .options(options)
                .load();
        readSet
                .selectExpr("CAST(key AS STRING)", "CAST(value AS STRING)")
                .write()
                .option("checkpointLocation", "/data")
                .format("com.databricks.spark.csv")
                .save("hdfs://localhost:9000/data/kafka");
    }

    private Map<String, String> fillMap(final Map<String, String> options) {
        options.put("kafka.bootstrap.servers",  StringUtils.join(this.appProperties.getKafkaServers(), ","));
        options.put("subscribe",  StringUtils.join(this.appProperties.getKafkaTopics(), ","));
        options.put("includeHeaders", "true");
        return options;
    }
}
