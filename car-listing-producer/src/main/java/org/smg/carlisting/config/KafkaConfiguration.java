package org.smg.carlisting.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfiguration {

    @Value("${carlListingProducer.kafka.topics.carListing.action.create.name}")
    String createTopic;
    @Value("${carlListingProducer.kafka.topics.carListing.action.update.name}")
    String updateTopic;
    @Value("${carlListingProducer.kafka.topics.carListing.action.delete.name}")
    String deleteTopic;

    @Bean
    public NewTopic carListingCrete() {
        return TopicBuilder.name(createTopic)
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic carListingUpdate() {
        return TopicBuilder.name(updateTopic)
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic carListingDelete() {
        return TopicBuilder.name(deleteTopic)
                .partitions(1)
                .replicas(1)
                .build();
    }

}