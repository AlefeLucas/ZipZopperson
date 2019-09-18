package model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Message implements Serializable {

    private User author;
    private String content;
    private Date date;

    public Message(User author) {
        this.author = author;
    }

    public Message(User author, String content, Date date) {
        this.author = author;
        this.content = content;
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("[dd/MM, hh:mm]");

        return String.format("%s %s: %s", dateFormat.format(date), author, content);
    }
}
