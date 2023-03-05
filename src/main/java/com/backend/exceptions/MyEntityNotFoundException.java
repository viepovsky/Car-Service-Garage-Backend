package com.backend.exceptions;

import lombok.Getter;

@Getter
public class MyEntityNotFoundException extends Exception {
    private Long recordId;
    private String text;
    public MyEntityNotFoundException(String text, Long recordId) {
        super(text + " of id: " + recordId);
        this.recordId = recordId;
        this.text = text;
    }
}
