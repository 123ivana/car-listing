package org.smg.carlisting;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.smg.carlisting.model.CarListing;
import org.smg.carlisting.service.CarListingService;
import org.springframework.beans.factory.annotation.Autowired;

class CarListingServiceTest extends BaseIT {

    @Autowired
    CarListingService carListingService;


    @Test
    public void testCreate() {
        //given
        var carListing = createCarListing();

        //when
        carListing = carListingService.save(carListing);

        //then
        Assertions.assertNotNull(carListing.getId());
    }

    private CarListing createCarListing() {
        return new CarListing(null, "BMW", "X1", 2010);
    }


}
