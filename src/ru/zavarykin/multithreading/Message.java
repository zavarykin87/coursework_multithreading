package ru.zavarykin.multithreading;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Message implements Serializable {
    private String text;
    private LocalDateTime dateTime;

    public String getText() {
        return text;
    }

    public Message(String text) {
        this.text = text;
    }

    public void setDateTime() {
        this.dateTime = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "Message{" +
                "text='" + text + '\'' +
                ", dateTime=" + dateTime +
                '}';
    }
}