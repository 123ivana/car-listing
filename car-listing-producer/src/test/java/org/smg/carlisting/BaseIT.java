package org.smg.carlisting;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;

@SpringBootTest
public class BaseIT {

    private static final String IMAGE_NAME =
            "confluentinc/cp-kafka:7.3.3";

    @Container
    static final KafkaContainer kafka = new KafkaContainer(
            DockerImageName.parse(IMAGE_NAME)
    );

    @BeforeAll
    static void setUp() {
        kafka.start();
    }

    @AfterAll
    static void destroy() {
        kafka.stop();
    }

    @DynamicPropertySource
    static void overrideProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.kafka.bootstrap-servers", kafka::getBootstrapServers);
    }

}
