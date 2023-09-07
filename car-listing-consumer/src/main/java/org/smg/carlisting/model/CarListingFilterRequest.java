package org.smg.carlisting.model;

public record CarListingFilterRequest(
        String id,
        String make,
        String model,
        Integer year,
        String sortBy,
        Integer pageNo,
        Integer pageSize) {
}