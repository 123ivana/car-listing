package org.smg.carlisting.controller;

import org.smg.carlisting.event.CreateCarListingEvent;
import org.smg.carlisting.event.DeleteCarListingEvent;
import org.smg.carlisting.event.UpdateCarListingEvent;
import org.smg.carlisting.service.CarListingProducerService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/car-listing")
public class CarListingProducerController {

    CarListingProducerService carListingProducerService;

    public CarListingProducerController(CarListingProducerService carListingProducerService) {
        this.carListingProducerService = carListingProducerService;
    }

    @PostMapping
    public void createCarListing(@RequestBody CreateCarListingEvent createCarListingDto) {
        carListingProducerService.sendCreateCarListingEvent(createCarListingDto);
    }

    @PatchMapping
    public void updateCarListing(@RequestBody UpdateCarListingEvent updateCarListingDto) {
        carListingProducerService.sendUpdateCarListingEvent(updateCarListingDto);
    }

    @DeleteMapping("{id}")
    public void deleteCarListing(@PathVariable String id) {
        carListingProducerService.sendDeleteCarListingEvent(new DeleteCarListingEvent(id));
    }
}
