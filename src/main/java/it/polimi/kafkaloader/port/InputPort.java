package it.polimi.kafkaloader.port;

import it.polimi.kafkaloader.domain.Event;

import java.io.IOException;

public interface InputPort {
    boolean hasNext();

    Event next() throws IOException;
}
