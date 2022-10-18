package com.example.microservtutorial1.Exceptions;

import org.springframework.validation.FieldError;

import java.util.List;

public class FieldErrorMessage {
    private List<FieldError> errorFieldsWithMessage;

    public FieldErrorMessage(List<FieldError> errorFieldsWithMessage) {
        this.errorFieldsWithMessage = errorFieldsWithMessage;
    }

    @Override
    public String toString() {
        int errorsCount = errorFieldsWithMessage.size();

        StringBuilder stringBuilder= new StringBuilder();
        errorFieldsWithMessage.forEach(
                (e)->{
                    String fieldMessage = e.getField()+": "+e.getDefaultMessage()+", ";
                    stringBuilder.append(fieldMessage);
                });

        return errorsCount + " errors: { " + stringBuilder + " }";
    }

    public List<FieldError> getErrorFieldsWithMessage() {
        return errorFieldsWithMessage;
    }

    public void setErrorFieldsWithMessage(List<FieldError> errorFieldsWithMessage) {
        this.errorFieldsWithMessage = errorFieldsWithMessage;
    }
}
