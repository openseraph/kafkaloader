package it.polimi.kafkaloader.adapter;

import it.polimi.kafkaloader.domain.Event;
import it.polimi.kafkaloader.port.OutputPort;
import org.apache.kafka.clients.admin.*;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.config.TopicConfig;

import java.util.List;
import java.util.Map;
import java.util.Properties;

public class KafkaAdapter implements OutputPort {

    private Producer<String, String> producer;
    private String topicName;

    public KafkaAdapter(
            String bootstrapServers,
            String topicName
    ) {
        Properties adminClientProps = new Properties();
        adminClientProps.put("bootstrap.servers", bootstrapServers);
        adminClientProps.put(TopicConfig.RETENTION_MS_CONFIG, "-1");

        AdminClient adminClient = KafkaAdminClient.create(adminClientProps);

        NewTopic kafkaTopic = new NewTopic(topicName, 1, (short) 1);
        kafkaTopic.configs(Map.of(TopicConfig.RETENTION_MS_CONFIG, "-1"));

        try {
            DeleteTopicsResult deleteTopicsResult = adminClient.deleteTopics(List.of(topicName));
            while (!deleteTopicsResult.all().isDone()) {};
            CreateTopicsResult createTopicsResult = adminClient.createTopics(List.of(kafkaTopic));
            while(!createTopicsResult.all().isDone()) {};
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        Properties producerProps = new Properties();
        producerProps.put("bootstrap.servers", bootstrapServers);
        producerProps.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        producerProps.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        producerProps.put("batch.size", 200000);
        producerProps.put("linger.ms", 100);
        producerProps.put("compression.type", "lz4");

        this.producer = new KafkaProducer<String, String>(producerProps);
        this.topicName = topicName;
    }

    @Override
    public void publish(Event event) {
        ProducerRecord<String, String> record = new ProducerRecord<String, String>(
                this.topicName,
                null,
                event.getTimestamp().toEpochMilli(),
                null,
                event.getBody());
        this.producer.send(record);
    }
}
