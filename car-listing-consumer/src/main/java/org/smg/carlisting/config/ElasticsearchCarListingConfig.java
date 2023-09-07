package org.smg.carlisting.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;

@Configuration
public class ElasticsearchCarListingConfig extends ElasticsearchConfiguration {

    @Value("${spring.elasticsearch.uris}")
    String elasticsearchClient;

    @Override
    public ClientConfiguration clientConfiguration() {
        return ClientConfiguration.builder()
                .connectedTo(elasticsearchClient)
                .build();
    }

}
