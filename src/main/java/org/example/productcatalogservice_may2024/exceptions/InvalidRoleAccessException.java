package org.example.productcatalogservice_may2024.exceptions;

public class InvalidRoleAccessException extends RuntimeException {
    public InvalidRoleAccessException(String message) {
        super(message);
    }
}
