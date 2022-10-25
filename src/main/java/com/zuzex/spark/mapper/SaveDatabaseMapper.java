package com.zuzex.spark.mapper;

import com.zuzex.spark.config.props.AppProperties;
import com.zuzex.spark.model.DataModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.spark.api.java.JavaSparkContext;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.UUID;
import java.util.concurrent.*;

import static com.zuzex.spark.utils.InputUtils.inputConvert;
import static com.zuzex.spark.utils.InputUtils.sqlInsert;

@Slf4j
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "name.startType", havingValue = "READ")
public class SaveDatabaseMapper {
    private static final ConcurrentLinkedQueue<DataModel> NON_BLOCKING_QUEUE = new ConcurrentLinkedQueue<>();
    private final JdbcTemplate jdbcTemplate;
    private final AppProperties appProperties;
    private final JavaSparkContext sparkContext;

    @PostConstruct
    void init() {
        log.info("Mapper started: {}", this.getClass());
    }

    @EventListener(ApplicationReadyEvent.class)
    public void event() {
        long initTime = System.nanoTime();
        new Thread(() -> {
            while (true) {
                try {
                    if (!NON_BLOCKING_QUEUE.isEmpty()) {
                        final DataModel data = NON_BLOCKING_QUEUE.poll();
                        CompletableFuture.runAsync(() -> {
                            jdbcTemplate.execute(sqlInsert(data));
                        });
                    } else {
                        TimeUnit.SECONDS.sleep(1);
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();

        this.sparkContext
                .textFile(this.appProperties.getFilePath())
                .foreach(inputRecord -> NON_BLOCKING_QUEUE.add(new DataModel(UUID.randomUUID(), inputConvert(inputRecord))));

        long endTime = System.nanoTime() - initTime;
        System.out.println("Time save: " + endTime + "ns " + TimeUnit.NANOSECONDS.toMillis(endTime) + " ms");
    }

}
