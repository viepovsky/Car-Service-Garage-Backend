package com.viepovsky.exceptions;

public class WrongInputDataException extends IllegalArgumentException {
    public WrongInputDataException(String s) {
        super(s);
    }
}
