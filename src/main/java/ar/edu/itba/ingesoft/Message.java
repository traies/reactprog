package ar.edu.itba.ingesoft;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by traie_000 on 10/23/2017.
 */
@XmlRootElement
public class Message implements Serializable {
    public String author;
    public String text;
    public Date date;

    public Message() {
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Message(String author, String text) {
        this.author = author;
        this.text = text;
        this.date = new Date();
    }


    public String getAuthor() {
        return author;
    }

    public String getText() {
        return text;
    }

    public Date getDate() {
        return date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Message message = (Message) o;

        if (!author.equals(message.author)) return false;
        if (!text.equals(message.text)) return false;
        return date.equals(message.date);

    }

    @Override
    public int hashCode() {
        int result = author.hashCode();
        result = 31 * result + text.hashCode();
        result = 31 * result + date.hashCode();
        return result;
    }

    public String toString() {
        return getText();
    }


}
