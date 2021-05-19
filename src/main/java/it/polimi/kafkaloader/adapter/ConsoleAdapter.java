package it.polimi.kafkaloader.adapter;

import it.polimi.srbench.domain.Event;
import it.polimi.srbench.port.OutputPort;

public class ConsoleAdapter implements OutputPort {
    @Override
    public void publish(Event event) {
        System.out.println(event.getTimestamp().toString());
    }
}
