package org.smg.carlisting.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smg.carlisting.event.CreateCarListingEvent;
import org.smg.carlisting.event.DeleteCarListingEvent;
import org.smg.carlisting.event.UpdateCarListingEvent;
import org.smg.carlisting.mapper.KafkaCarListingMapper;
import org.smg.carlisting.service.CarListingService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class CarListingEventHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(CarListingEventHandler.class);

    CarListingService carListingService;
    KafkaCarListingMapper kafkaCarListingMapper;

    public CarListingEventHandler(CarListingService carListingService, KafkaCarListingMapper kafkaCarListingMapper) {
        this.carListingService = carListingService;
        this.kafkaCarListingMapper = kafkaCarListingMapper;
    }

    @KafkaListener(topics = "${carlListingConsumer.kafka.topics.carListing.action.create.name}",
            groupId = "${spring.kafka.consumer.group-id}")
    public void listenForCreateCarListingEvent(CreateCarListingEvent createCarListingEvent) {
        LOGGER.info("Received Create Card listing event {}", createCarListingEvent.toString());
        var carListing = carListingService.save(kafkaCarListingMapper.mapCarListingFromCreateEvent(createCarListingEvent));
        LOGGER.info("Created Car listing with id {}", carListing.getId());

    }

    @KafkaListener(topics = "${carlListingConsumer.kafka.topics.carListing.action.update.name}", groupId = "${spring.kafka.consumer.group-id}")
    public void listen(UpdateCarListingEvent updateCarListingEvent) {
        LOGGER.info("Received Update Card listing event {}",  updateCarListingEvent.toString());
        carListingService.update(kafkaCarListingMapper.mapCarListingFromUpdateEvent(updateCarListingEvent));

    }

    @KafkaListener(topics = "${carlListingConsumer.kafka.topics.carListing.action.delete.name}", groupId = "${spring.kafka.consumer.group-id}")
    public void listen(DeleteCarListingEvent deleteCarListingEvent) {
        LOGGER.info("Received Delete Card listing event {}",  deleteCarListingEvent.toString());
        carListingService.delete(deleteCarListingEvent.id());
    }

}
