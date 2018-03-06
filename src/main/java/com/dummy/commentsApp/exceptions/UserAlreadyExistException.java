package com.dummy.commentsApp.exceptions;

import org.springframework.security.core.AuthenticationException;

public final class UserAlreadyExistException extends AuthenticationException {

    private static final long serialVersionUID = 5861310537366287163L;

    public UserAlreadyExistException(final String message) {
        super(message);
    }

    public UserAlreadyExistException(final String message, final Throwable cause) {
        super(message, cause);
    }

}