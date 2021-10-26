package it.polimi.kafkaloader.service;

import it.polimi.kafkaloader.domain.Event;
import it.polimi.kafkaloader.port.InputPort;
import it.polimi.kafkaloader.port.OutputPort;

import java.io.IOException;
import java.util.List;

public class ForwardingService {
    private InputPort input;
    private OutputPort output;

    public ForwardingService(InputPort input, OutputPort output) {
        this.input = input;
        this.output = output;
    }

    public void run() throws IOException {
        while (this.input.hasNext()) {
            List<Event> events = this.input.next();
            for (Event event : events) {
                this.output.publish(event);
            }
        }
    }
}
