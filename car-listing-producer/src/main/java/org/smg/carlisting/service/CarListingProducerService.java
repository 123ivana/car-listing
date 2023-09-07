package org.smg.carlisting.service;

import org.smg.carlisting.event.CreateCarListingEvent;
import org.smg.carlisting.event.DeleteCarListingEvent;
import org.smg.carlisting.event.UpdateCarListingEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class CarListingProducerService {

    KafkaTemplate<String, Object> kafkaTemplate;
    String createTopic;
    String updateTopic;
    String deleteTopic;

    @Autowired
    CarListingProducerService(KafkaTemplate<String, Object> kafkaTemplate,
                              @Value("${carlListingProducer.kafka.topics.carListing.action.create.name}") String createTopic,
                              @Value("${carlListingProducer.kafka.topics.carListing.action.update.name}") String updateTopic,
                              @Value("${carlListingProducer.kafka.topics.carListing.action.delete.name}") String deleteTopic) {
        this.kafkaTemplate = kafkaTemplate;
        this.createTopic = createTopic;
        this.updateTopic = updateTopic;
        this.deleteTopic = deleteTopic;
    }

    public void sendCreateCarListingEvent(CreateCarListingEvent createCarListingDto) {
        kafkaTemplate.send(createTopic, createCarListingDto);
    }

    public void sendUpdateCarListingEvent(UpdateCarListingEvent updateCarListingDto) {
        kafkaTemplate.send(updateTopic, updateCarListingDto);
    }

    public void sendDeleteCarListingEvent(DeleteCarListingEvent deleteCarListingRequestDto) {
        kafkaTemplate.send(deleteTopic, deleteCarListingRequestDto);
    }

}
