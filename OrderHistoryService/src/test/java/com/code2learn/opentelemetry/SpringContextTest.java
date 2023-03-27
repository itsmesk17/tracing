package com.code2learn.opentelemetry;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class SpringContextTest {
    
    @Test
    void whenSpringContextIsBootstrapped_thenNoException() {
    }
}
