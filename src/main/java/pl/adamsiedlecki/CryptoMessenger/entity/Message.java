package pl.adamsiedlecki.CryptoMessenger.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "messages")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private long id;

    @Column
    private String text;

    @Column
    private Date date;

    @Column
    private String room;

    @Column
    @Lob
    private String encryptedImage;

    public Message() {
    }

    public String getEncryptedImage() {
        return encryptedImage;
    }

    public void setEncryptedImage(String encryptedImage) {
        this.encryptedImage = encryptedImage;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public Message(String text, Date date, String room, String encryptedImage) {
        this.text = text;
        this.date = date;
        this.room = room;
        this.encryptedImage = encryptedImage;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
