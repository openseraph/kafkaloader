package it.polimi.kafkaloader.domain;

import java.time.Instant;

public class Event {
    private Instant timestamp;
    private String body;

    public Event(Instant timestamp, String body) {
        this.timestamp = timestamp;
        this.body = body;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public String getBody() {
        return body;
    }
}
