package com.example.transaction_service.handler;

import jakarta.persistence.PersistenceException;

public class FailedTransactionException extends PersistenceException{
    public FailedTransactionException(String message) {
        super(message);
    }
}
