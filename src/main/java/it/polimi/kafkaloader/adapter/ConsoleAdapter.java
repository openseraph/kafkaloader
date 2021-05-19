package it.polimi.kafkaloader.adapter;

import it.polimi.kafkaloader.domain.Event;
import it.polimi.kafkaloader.port.OutputPort;

public class ConsoleAdapter implements OutputPort {
    @Override
    public void publish(Event event) {
        System.out.println(event.getTimestamp().toString());
    }
}
