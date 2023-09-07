package org.smg.carlisting.controller;

import org.smg.carlisting.controller.dto.CarListingDto;
import org.smg.carlisting.model.CarListingFilterRequest;
import org.smg.carlisting.service.CarListingService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("api/v1/car-listing")
public class CarListingConsumerController {
    CarListingService carListingService;

    public CarListingConsumerController(CarListingService carListingService) {
        this.carListingService = carListingService;
    }

    @GetMapping("/{id}")
    public CarListingDto findById(@PathVariable("id") String id) {
        return carListingService.findById(id);
    }

    @GetMapping
    public List<CarListingDto> findAll(@RequestParam(required = false) String id,
                                       @RequestParam(required = false) String make,
                                       @RequestParam(required = false) String model,
                                       @RequestParam(required = false) Integer year,
                                       @RequestParam(required = false) String sortBy,
                                       @RequestParam(required = false) Integer pageNo,
                                       @RequestParam(required = false) Integer pageSize) {
        return carListingService.findAll(new CarListingFilterRequest(id, make, model, year, sortBy, pageNo, pageSize));
    }
}
