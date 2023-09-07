package org.smg.carlisting.controller.dto;

public record RestApiErrorResponseDto(
        String status,
        String developerMessage) {
}
