package com.zuzex.spark.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.zuzex.spark.config.props.AppProperties;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;

import java.net.URI;

@UtilityClass
public class JsonUtils {

    public static JsonNode getDebeziumJson(final DataSourceProperties dataSourceProperties, final AppProperties appProperties) {
        final var config = JsonNodeFactory.instance.objectNode();
        final var dbUrl = URI.create(dataSourceProperties.getUrl());
        final var dbName = dbUrl.getPath().split("/")[0];
        final var kafkaTopic = appProperties.getKafkaTopics().isEmpty() ? "event" : appProperties.getKafkaTopics().get(0);

        config.put("connector.class", "io.debezium.connector.postgresql.PostgresConnector");
        config.put("tasks.max", "1");
        config.put("database.hostname", dbUrl.getHost());
        config.put("database.port", dbUrl.getPort());
        config.put("database.user", dataSourceProperties.getUsername());
        config.put("database.allowPublicKeyRetrieval", "true");
        config.put("database.password", dataSourceProperties.getPassword());
        config.put("database.server.id", "42");
        config.put("database.dbname", dbName);
        config.put("database.server.name", "postgres_server");
        config.put("database.include.list", dbName);
        config.put("topic.prefix", "debez");
        config.put("database.history.kafka.bootstrap.servers", StringUtils.join(appProperties.getKafkaServers(), ","));
        config.put("database.history.kafka.topic", kafkaTopic);
        config.put("include.schema.changes", "false");
        config.put("transforms", "unwrap");
        config.put("transforms.unwrap.type", "io.debezium.transforms.ExtractNewRecordState");
        config.put("transforms.unwrap.add.headers", "db");
        config.put("transforms.unwrap.add.fields", "op,table");
        config.put("transforms.unwrap.drop.tombstones", "true");
        config.put("transforms.unwrap.delete.handling.mode", "rewrite");

        return JsonNodeFactory.instance.objectNode()
                .put("name", "data-connector")
                .set("config", config);
    }
}
