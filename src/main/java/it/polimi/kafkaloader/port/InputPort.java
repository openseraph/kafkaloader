package it.polimi.kafkaloader.port;

import it.polimi.kafkaloader.domain.Event;

public interface InputPort {
    boolean hasNext();

    Event next();
}
