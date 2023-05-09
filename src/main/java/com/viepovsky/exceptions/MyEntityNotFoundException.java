package com.viepovsky.exceptions;

import jakarta.persistence.EntityNotFoundException;
import lombok.Getter;

@Getter
public class MyEntityNotFoundException extends EntityNotFoundException {
    private Long recordId;
    private String text;

    public MyEntityNotFoundException(String text, Long recordId) {
        super(text + " of id: " + recordId);
        this.recordId = recordId;
        this.text = text;
    }

    public MyEntityNotFoundException(String text) {
        super(text);
        this.text = text;
    }
}
