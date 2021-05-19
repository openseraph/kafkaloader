package it.polimi.kafkaloader.port;

import it.polimi.kafkaloader.domain.Event;

public interface OutputPort {
    void publish(Event event);
}
