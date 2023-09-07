package org.smg.carlisting.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.smg.carlisting.event.CreateCarListingEvent;
import org.smg.carlisting.event.UpdateCarListingEvent;
import org.smg.carlisting.model.CarListing;

@Mapper(componentModel = "spring")
public interface KafkaCarListingMapper {

    CarListing mapCarListingFromCreateEvent(CreateCarListingEvent createCarListingEvent);

    CarListing mapCarListingFromUpdateEvent(UpdateCarListingEvent createCarListingEvent);

}
