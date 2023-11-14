package edu.hw5.task3;

import java.time.LocalDate;

public abstract class DateHandler {
    protected DateHandler next;

    public void setNext(DateHandler next) {
        this.next = next;
    }

    public abstract LocalDate handleRequest(String request);
}
