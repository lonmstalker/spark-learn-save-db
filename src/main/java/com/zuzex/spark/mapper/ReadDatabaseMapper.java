package com.zuzex.spark.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zuzex.spark.config.props.AppProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

// https://spark.apache.org/docs/latest/structured-streaming-programming-guide.html
// https://docs.databricks.com/external-data/jdbc.html#language-scala
@Slf4j
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "app.startType", havingValue = "WRITE")
public class ReadDatabaseMapper {
    private final SparkSession sparkSession;
    private final DataSourceProperties dataSourceProperties;

    @PostConstruct
    void init() {
        log.info("Mapper started: {}", this.getClass());
    }

    @EventListener(ApplicationReadyEvent.class)
    public void event() {
        final Map<String, String> options = this.fillMap(new HashMap<>());
        final Dataset<Row> readSet = sparkSession.read()
                .format("jdbc")
                .options(options)
                .load();
        readSet
                .write()
                .option("checkpointLocation", "/data")
                .format("com.databricks.spark.csv")
                .save("hdfs://localhost:9000/data/migrated_from_jdbc.csv");
    }

    private Map<String, String> fillMap(final Map<String, String> options) {
        options.put("url", this.dataSourceProperties.getUrl());
        options.put("dbtable", "data");
        options.put("driver", this.dataSourceProperties.getDriverClassName());
        options.put("offsetColumn", "id");
        options.put("user", this.dataSourceProperties.getUsername());
        options.put("password", this.dataSourceProperties.getPassword());
        options.put("fetchSize", "1000");
        return options;
    }
}
