package ru.zavarykin.multithreading;


import java.io.Serializable;
import java.time.LocalDateTime;

public class Message implements Serializable {
    private String sender;
    private String text;
    private LocalDateTime dateTime;

    public Message(String sender, String text) {
        this.sender = sender;
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
                "sender='" + sender + '\'' +
                ", text='" + text + '\'' +
                ", dateTime=" + dateTime +
                '}';
    }
    public static Message getInstance(String sender, String text){
        return new Message(sender, text);
    }
}