package com.github.filip123go.kafka;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class ProducerDemoKeys {
    public static void main(String[] args) {

        Logger logger = LoggerFactory.getLogger(ProducerDemoKeys.class);

        //crate producer properties
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9095");
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        //create producer
        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(properties);

        for (int i = 0; i < 10; i++) {
            String topic = "first_topic";
            String value = "Hello world" + i;
            String key = "id_" + i;

            //create producer record
            ProducerRecord<String, String> record = new ProducerRecord<>(topic, key, value);
            //send data - asynchronous
            producer.send(record, new Callback() {
                @Override
                public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                    ///executes everytime a record is successfully send or there is an error.
                    if (e == null) {
                        //the record successfully send
                        logger.info("\n-------New MetaData:---------- \n" +
                                "Topic:" + recordMetadata.topic() + "\n" +
                                "Partition:" + recordMetadata.partition() + "\n" +
                                "Offset:" + recordMetadata.offset() + "\n" +
                                "TImestamp:" + recordMetadata.timestamp() + "\n");
                    } else {
                        logger.error("Error while producing", e);

                    }
                }
            });
        }

        //send data
        producer.flush();

        //sends data and close producer
        producer.close();
    }
}
