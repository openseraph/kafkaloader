package it.polimi.kafkaloader.service;

import it.polimi.kafkaloader.port.InputPort;
import it.polimi.kafkaloader.port.OutputPort;

import java.io.IOException;

public class ForwardingService {
    private InputPort input;
    private OutputPort output;

    public ForwardingService(InputPort input, OutputPort output) {
        this.input = input;
        this.output = output;
    }

    public void run() throws IOException {
        while (this.input.hasNext()) {
            this.output.publish(this.input.next());
        }
    }
}
