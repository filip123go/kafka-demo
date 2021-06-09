package com.github.filip123go.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class ProducerDemo {
    public static void main(String[] args) {

        //crate producer properties
        Properties properties = new Properties() ;
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9095");
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        //create producer
        KafkaProducer<String, String>  producer = new KafkaProducer<String, String>(properties);

        //create producer record
        ProducerRecord<String, String> record = new ProducerRecord<>("first_topic", "hello world");

        //send data - asynchronous
        producer.send(record);

        //send data
        producer.flush();

        //sends data and close producer
        producer.close();
    }
}
