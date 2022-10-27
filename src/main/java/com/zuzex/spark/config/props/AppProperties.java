package com.zuzex.spark.config.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import java.util.List;

@Data
@ConstructorBinding
@ConfigurationProperties(prefix = "app")
public class AppProperties {
    private String filePath;
    private StartType startType;
    private List<String> kafkaTopics;
    private List<String> kafkaServers;
}
