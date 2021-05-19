package it.polimi.kafkaloader;

import it.polimi.kafkaloader.adapter.DatasetAdapter;
import it.polimi.kafkaloader.adapter.KafkaAdapter;
import it.polimi.kafkaloader.service.ForwardingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KafkaLoader {

    private static final Logger logger = LoggerFactory.getLogger(KafkaLoader.class);

    public static void main(String[] args) {

        final String BOOTSTRAP_SERVER = System.getenv("BOOTSTRAP_SERVER");
        final String TOPIC = System.getenv("TOPIC");
        final String INPUT_FOLDER = System.getenv("INPUT_FOLDER");

        logger.info("BOOTSTRAP_SERVER: " + BOOTSTRAP_SERVER);
        logger.info("TOPIC: " + TOPIC);
        logger.info("INPUT_FOLDER: " + INPUT_FOLDER);

        DatasetAdapter datasetAdapter = new DatasetAdapter(INPUT_FOLDER);
        KafkaAdapter kafkaAdapter = new KafkaAdapter(BOOTSTRAP_SERVER, TOPIC);

        ForwardingService forwardingService = new ForwardingService(datasetAdapter, kafkaAdapter);

        forwardingService.run();

        logger.info("Done!");
    }
}
