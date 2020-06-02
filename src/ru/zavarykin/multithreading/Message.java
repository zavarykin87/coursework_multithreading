package ru.zavarykin.multithreading;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Message implements Serializable {
    private String sender;
    private String text;
    private LocalDateTime dateTime;

    // гетеры и сетеры
    public String getSender() {
        return sender;
    }
    public void setSender(String sender) {
        this.sender = sender;
    }
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
    public void setDateTime() {
        this.dateTime = LocalDateTime.now();
    }
    // конструкторы
    public Message(String sender, String text) {
        this.sender = sender;
        this.text = text;
    }
    public Message(){
    }

    @Override
    public String toString() {
        return "Message{" +
                "sender='" + sender + '\'' +
                ", text='" + text + '\'' +
                ", dateTime=" + dateTime +
                '}';
    }

    public static Message getInstance(String sender, String text){
        return new Message(sender, text);
    }
}
