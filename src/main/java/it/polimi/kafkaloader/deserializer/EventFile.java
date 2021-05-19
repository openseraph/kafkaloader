package it.polimi.kafkaloader.deserializer;

import it.polimi.kafkaloader.domain.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.Instant;

public class EventFile {
    private static final Logger logger = LoggerFactory.getLogger(EventFile.class);

    private Event event;

    public EventFile(File file) {
        String timestamp = file.getParentFile().getName();
        String body = null;

        try {
            body = Files.readString(file.toPath());
        } catch (IOException e) {
            logger.error(e.getMessage());
        }

        this.event = new Event(Instant.ofEpochSecond(Long.valueOf(timestamp)), body);
    }

    public Event getEvent() {
        return event;
    }
}
