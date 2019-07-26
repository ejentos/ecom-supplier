package com.tsa.supplier.service.jms.kafka;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfig {

    @Value("${kafka.producer.transaction-id-prefix}")
    private String transactionIdPrefix;

//    @Bean
//    @Qualifier("kafkaProducerProperties")
//    @ConfigurationProperties("kafka.producer.factory.config")
//    public Map<String, String> producerKafkaProperties() {
//        return new HashMap<>();
//    }
//
//    @Bean(destroyMethod = "close")
//    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
//    public Producer producerFactory(@Autowired @Qualifier("kafkaProducerProperties") Map<String, String> properties) {
//        Producer producer = new KafkaProducer(properties);
//        producer.initTransactions();
//        return producer;
//    }

    @Bean
    @Qualifier("kafkaConsumerProperties")
    @ConfigurationProperties("kafka.consumer.factory.config")
    public Map<String, String> consumerKafkaProperties() {
        return new HashMap<>();
    }

//    @Bean
//    @Qualifier("kafkaConsumer")
//    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
//    public KafkaConsumer getKafkaConsumer(@Autowired @Qualifier("kafkaConsumerProperties") Map<String, Object> properties) {
//        return new KafkaConsumer(properties);
//    }

}