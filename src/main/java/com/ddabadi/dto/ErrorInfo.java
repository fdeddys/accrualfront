package com.ddabadi.dto;

/**
 * Created by deddy on 5/10/16.
 */
public class ErrorInfo {

    private String url;
    private String message;

    public ErrorInfo(String url, String message) {
        this.url = url;
        this.message = message;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
