package com.zuzex.spark.config;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SparkSession;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SparkConfig {

    @Bean
    public JavaSparkContext sparkContext() {
        return new JavaSparkContext(
                new SparkConf()
                        .setAppName("saveDbApp")
                        .setMaster("local[*]")
        );
    }

    @Bean
    public SparkSession sparkSession(JavaSparkContext ctx) {
        return SparkSession.builder()
                .sparkContext(ctx.sc())
                .getOrCreate();
    }
}
