package com.forohub.infra.errores;

public class UserAlreadyExistsException extends RuntimeException{
    public UserAlreadyExistsException(String s) {
        super(s);
    }
}
