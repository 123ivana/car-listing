package org.smg.carlisting.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.smg.carlisting.controller.dto.CarListingDto;
import org.smg.carlisting.model.CarListing;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CarListingMapper {

    CarListing mapCarListing(CarListingDto carListingDto);

    CarListingDto mapCarListingDto(CarListing carListing);

}
