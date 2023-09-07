package org.smg.carlisting.event;

public record UpdateCarListingEvent(String id, String make, String model, int year) {
}
