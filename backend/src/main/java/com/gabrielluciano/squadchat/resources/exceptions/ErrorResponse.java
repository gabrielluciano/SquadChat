package com.gabrielluciano.squadchat.resources.exceptions;

import java.time.Instant;

public class ErrorResponse {
    private String error;
    private String message;
    private String path;
    private Integer status;
    private Instant timestamp;

    public ErrorResponse() {

    }

    public ErrorResponse(String error, String message, String path, Integer status, Instant timestamp) {
        this.error = error;
        this.message = message;
        this.path = path;
        this.status = status;
        this.timestamp = timestamp;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }
}
