package com.code2learn.opentelemetry;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, properties = {"spring.kafka.bootstrap-servers=localhost:19092"})
@EmbeddedKafka(partitions = 1, brokerProperties = { "listeners=PLAINTEXT://localhost:19092", "port=19092" })
class SpringContextTest {
    
    @Test
    void whenSpringContextIsBootstrapped_thenNoExceptions() {
    }
}
