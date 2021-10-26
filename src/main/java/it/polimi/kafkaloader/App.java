package it.polimi.kafkaloader;

import it.polimi.kafkaloader.adapter.DatasetAdapter;
import it.polimi.kafkaloader.adapter.KafkaAdapter;
import it.polimi.kafkaloader.service.ForwardingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Objects;

public class App {

    private static final Logger logger = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {

        final String KAFKA_BOOTSTRAP_SERVER = System.getenv("KAFKA_BOOTSTRAP_SERVER");
        final String KAFKA_TOPIC = System.getenv("KAFKA_TOPIC");
        final String INPUT_FOLDER = System.getenv("INPUT_FOLDER");

        try {
            Objects.requireNonNull(KAFKA_BOOTSTRAP_SERVER, "Please set KAFKA_BOOTSTRAP_SERVER env variable.");
            Objects.requireNonNull(KAFKA_TOPIC, "Please set KAFKA_TOPIC env variable.");
            Objects.requireNonNull(INPUT_FOLDER, "Please set INPUT_FOLDER env variable.");

        } catch (NullPointerException e) {
            logger.error(e.getMessage());
            System.exit(1);
        }

        logger.info("KAFKA_BOOTSTRAP_SERVER: " + KAFKA_BOOTSTRAP_SERVER);
        logger.info("KAFKA_TOPIC: " + KAFKA_TOPIC);
        logger.info("INPUT_FOLDER: " + INPUT_FOLDER);


        DatasetAdapter datasetAdapter = new DatasetAdapter(INPUT_FOLDER);
        KafkaAdapter kafkaAdapter = new KafkaAdapter(KAFKA_BOOTSTRAP_SERVER, KAFKA_TOPIC);

        ForwardingService forwardingService = new ForwardingService(datasetAdapter, kafkaAdapter);

        try {
            forwardingService.run();
        } catch (IOException e) {
            logger.error(e.getMessage());
            System.exit(1);
        }

        logger.info("Done!");
    }
}
