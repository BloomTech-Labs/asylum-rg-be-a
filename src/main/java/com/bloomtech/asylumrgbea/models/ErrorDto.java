package com.bloomtech.asylumrgbea.models;

import lombok.Data;

/**
 * Defines a POJO for an error object that is sent to the client from the
 * controller layer if an exception occurs.
 */
@Data
public class ErrorDto {

    private final String message;
}
