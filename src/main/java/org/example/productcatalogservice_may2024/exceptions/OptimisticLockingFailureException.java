package org.example.productcatalogservice_may2024.exceptions;

public class OptimisticLockingFailureException extends RuntimeException {
    public OptimisticLockingFailureException(String message, Throwable cause) {
        super(message, cause);
    }
}

