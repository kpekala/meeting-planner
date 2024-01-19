package com.kpekala.meetingplanner.domain.auth.exception;

public class UserExistsException extends RuntimeException{

    public UserExistsException(String message) {
        super(message);
    }
}
