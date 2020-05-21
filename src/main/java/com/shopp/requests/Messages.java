package com.shopp.requests;

import java.util.HashMap;
import java.util.Map;

public class Messages {

    private Map<String, String> messages = new HashMap<>();

    public void add(String key, String message) {
        this.messages.put(key, message);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for(String key : messages.keySet()){
            stringBuilder.append(" - ").append(key).append(" : ").append(messages.get(key));
        }
        return stringBuilder.toString();
    }

}
