package org.smg.carlisting;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.smg.carlisting.event.CreateCarListingEvent;
import org.smg.carlisting.event.DeleteCarListingEvent;
import org.smg.carlisting.event.UpdateCarListingEvent;
import org.smg.carlisting.service.CarListingProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertWith;
import static org.testcontainers.shaded.org.awaitility.Awaitility.await;

class CarListingProducerServiceTest extends BaseIT {

    @Autowired
    CarListingProducerService carListingProducerService;

    @Autowired
    KafkaTemplate<String, Object> kafkaTemplate;

    @Autowired
    ConsumerFactory<String, Object> consumerFactory;

    @Autowired
    protected ObjectMapper objectMapper;

    @Value("${carlListingProducer.kafka.topics.carListing.action.create.name}")
    String createTopic;
    @Value("${carlListingProducer.kafka.topics.carListing.action.update.name}")
    String updateTopic;
    @Value("${carlListingProducer.kafka.topics.carListing.action.delete.name}")
    String deleteTopic;

    @BeforeEach
    public void init() {
        kafkaTemplate.setConsumerFactory(consumerFactory);
    }

    @Test
    public void testSendCreateCarListingEvent() {
        //given
        var carListingEvent = mockCreateCarListingEvent();

        //when
        carListingProducerService.sendCreateCarListingEvent(carListingEvent);

        //then
        Consumer<CreateCarListingEvent> requirements = response -> assertThat(response)
                .hasFieldOrPropertyWithValue("make", "BMW")
                .hasFieldOrPropertyWithValue("model", "X1")
                .hasFieldOrPropertyWithValue("year", 2010);

        awaitForEvent(createTopic, CreateCarListingEvent.class, requirements);

    }

    @Test
    public void testSendUpdateCarListingEvent() {
        //given
        var updateCarListingEvent = mockUpdateCarListingEvent();

        //when
        carListingProducerService.sendUpdateCarListingEvent(updateCarListingEvent);

        //then
        Consumer<UpdateCarListingEvent> requirements = response -> assertThat(response)
                .hasFieldOrPropertyWithValue("id", "c2AlZYoBYHW_4KU4mDmY")
                .hasFieldOrPropertyWithValue("make", "BMW")
                .hasFieldOrPropertyWithValue("model", "X1")
                .hasFieldOrPropertyWithValue("year", 2010);

        awaitForEvent(updateTopic, UpdateCarListingEvent.class, requirements);

    }

    @Test
    public void testSendDeleteCarListingEvent() {
        //given
        var deleteCarListingEvent = mockDeleteCarListingEvent();

        //when
        carListingProducerService.sendDeleteCarListingEvent(deleteCarListingEvent);

        //then
        Consumer<DeleteCarListingEvent> requirements = response -> assertThat(response)
                .hasFieldOrPropertyWithValue("id", "c2AlZYoBYHW_4KU4mDmY");

        awaitForEvent(deleteTopic, DeleteCarListingEvent.class, requirements);

    }

    private <T> void awaitForEvent(String topic, Class<T> responseClass, Consumer<T> requirements) {
        await()
                .pollInterval(Duration.ofSeconds(3))
                .atMost(10, TimeUnit.SECONDS)
                .untilAsserted(() -> {
                    ConsumerRecord<String, Object> record = kafkaTemplate.receive(topic, 0, 0l);
                    var response = objectMapper.readValue(record.value().toString(), responseClass);
                    assertWith(response, requirements);

                });
    }

    private CreateCarListingEvent mockCreateCarListingEvent() {
        return new CreateCarListingEvent("BMW", "X1", 2010);
    }

    private UpdateCarListingEvent mockUpdateCarListingEvent() {
        return new UpdateCarListingEvent("c2AlZYoBYHW_4KU4mDmY", "BMW", "X1", 2010);
    }

    private DeleteCarListingEvent mockDeleteCarListingEvent() {
        return new DeleteCarListingEvent("c2AlZYoBYHW_4KU4mDmY");
    }

}
