package ru.geekbrains.lesson6_spring_boot.exceptions;

public class NotFoundException extends  RuntimeException{
    private String field = "FOUND NOTHING";

    public String getField() {
        return field;
    }
}
