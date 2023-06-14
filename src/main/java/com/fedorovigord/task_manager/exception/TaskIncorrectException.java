package com.fedorovigord.task_manager.exception;

public class TaskIncorrectException extends IllegalArgumentException{
    public TaskIncorrectException(String message) {
        super(message);
    }

    public TaskIncorrectException(String message, Throwable cause) {
        super(message, cause);
    }
}
