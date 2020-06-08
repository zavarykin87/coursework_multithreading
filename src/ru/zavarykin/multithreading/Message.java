package ru.zavarykin.multithreading;


import java.io.Serializable;
import java.time.LocalDateTime;

public class Message implements Serializable {
    private String text;
    private LocalDateTime dateTime;

    public Message(String text) {
        this.text = text;
    }

    public Message() {
    }

    public void setDateTime() {
        this.dateTime = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "SimpleMessage{" +
                ", text='" + text + '\'' +
                ", dateTime=" + dateTime +
                '}';
    }
}