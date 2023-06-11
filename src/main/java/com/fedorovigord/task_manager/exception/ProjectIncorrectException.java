package com.fedorovigord.task_manager.exception;

public class ProjectIncorrectException extends IllegalArgumentException{
    public ProjectIncorrectException(String message) {
        super(message);
    }
    public ProjectIncorrectException(String message, Throwable cause) {
        super(message, cause);
    }
}
