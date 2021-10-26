package it.polimi.kafkaloader.port;

import it.polimi.kafkaloader.domain.Event;

import java.io.IOException;
import java.util.List;

public interface InputPort {
    boolean hasNext();

    List<Event> next() throws IOException;
}
