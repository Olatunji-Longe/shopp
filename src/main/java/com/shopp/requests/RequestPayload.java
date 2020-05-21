package com.shopp.requests;

public abstract class RequestPayload {

    protected transient Messages messages = new Messages();

    public abstract boolean isValid();
    public abstract String toString();

    public Messages getMessages() {
        return messages;
    }

}
