package com.tsa.supplier.service.jms.kafka.listener;

import com.tsa.supplier.service.serialization.JsonDeserializerJMS;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

public abstract class AbstractKafkaEventListenerTask<T> implements IEventListenerTask<T> {

    private final static Logger LOGGER = LoggerFactory.getLogger(AbstractKafkaEventListenerTask.class);

    @Value("${kafka.consumer.inventory.poll.duration}")
    private long duration;

    @Autowired
    @Qualifier("kafkaConsumerProperties")
    private Map<String, String> properties;

    private KafkaConsumer<String, T> kafkaConsumer;
    private volatile boolean opened = true;

    Set<String> topics;
    Map<String, Consumer<T>> topicConsumerMap;

    protected abstract Class<T> getGenericClass();

    protected abstract void init();

    @PostConstruct
    private void initKafkaConsumer() {
        this.kafkaConsumer = new KafkaConsumer<>(new HashMap<>(properties),
                new StringDeserializer(),
                new JsonDeserializerJMS<>(getGenericClass()));
        init();
    }

    @Override
    public final void run() {
        try {
            kafkaConsumer.subscribe(getTopics());
            Duration pollDuration = Duration.ofMillis(duration);
            while (opened) {
                ConsumerRecords<String, T> records = kafkaConsumer.poll(pollDuration);
                consume(records);
                kafkaConsumer.commitSync();
            }
        } catch (WakeupException e) {
            // Ignore exception if closing
            if (opened) {
                LOGGER.error("Consumer termination. Finishing the listener", e);
                throw e;
            }
        } catch (Throwable e) {
            LOGGER.error("Not able to process topic. Finishing the listener", e);
            throw e;
        } finally {
            kafkaConsumer.close();
        }
    }

    private void consume(ConsumerRecords<String, T> records) {
        for (ConsumerRecord<String, T> record : records) {
            T object = record.value();
            String topic = record.topic();
            Consumer<T> consumer = getConsumer(topic);
            if (consumer != null) {
                try {
                    consumer.accept(object);
                } catch (Exception e) {
                    LOGGER.error("Object can't be consumed properly: topic " + topic, e);
                    throw e;
                }
            }
        }
    }

    @Override
    public void shutdown() {
        opened = false;
        kafkaConsumer.wakeup();
    }

    private Set<String> getTopics() {
        return topics;
    }

    private Consumer<T> getConsumer(String topic) {
        return topicConsumerMap.get(topic);
    }

}