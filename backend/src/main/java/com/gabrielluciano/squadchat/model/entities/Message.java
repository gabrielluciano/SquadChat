package com.gabrielluciano.squadchat.model.entities;

import java.io.Serializable;

import com.gabrielluciano.squadchat.model.snowflake.Snowflake;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "messages")
public class Message implements Comparable<Message>, Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(columnDefinition = "bigint")
    private Long id;
    private String content;
    private Boolean edited;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User author;

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    public Message() {
    }

    public Message(Snowflake snowflake, String content, Boolean edited, User author, Room room) {
        this.id = snowflake.getRawId();
        this.content = content;
        this.edited = edited;
        this.author = author;
        this.room = room;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean getEdited() {
        return edited;
    }

    public void setEdited(Boolean edited) {
        this.edited = edited;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Message other = (Message) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public int compareTo(Message other) {
        return id.compareTo(other.id);
    }
}
