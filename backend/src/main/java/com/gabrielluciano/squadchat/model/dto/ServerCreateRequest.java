package com.gabrielluciano.squadchat.model.dto;

import java.io.Serializable;

public class ServerCreateRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;

    public  ServerCreateRequest() {
    }

    public ServerCreateRequest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
