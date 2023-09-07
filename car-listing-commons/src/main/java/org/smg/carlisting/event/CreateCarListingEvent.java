package org.smg.carlisting.event;

public record CreateCarListingEvent(String make, String model, int year) {
}
