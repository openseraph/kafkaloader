package it.polimi.kafkaloader.adapter;

import it.polimi.kafkaloader.domain.Event;
import it.polimi.kafkaloader.port.OutputPort;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

public class KafkaAdapter implements OutputPort {

    private Producer<String, String> producer;
    private String topic;

    public KafkaAdapter(
            String bootstrapServers,
            String topic
    ) {
        Properties props = new Properties();
        props.put("bootstrap.servers", bootstrapServers);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        this.producer = new KafkaProducer<String, String>(props);
        this.topic = topic;
    }

    @Override
    public void publish(Event event) {
        ProducerRecord<String, String> record = new ProducerRecord<String, String>(
                this.topic,
                null,
                event.getTimestamp().toEpochMilli(),
                null,
                event.getBody());
        this.producer.send(record);
    }
}
