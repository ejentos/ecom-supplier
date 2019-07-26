package com.tsa.supplier.service.serialization;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.tsa.supplier.service.view.JMSView;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

public class JsonSerializerJMS<T> implements Serializer<T> {

    private final ObjectMapper objectMapper;

    public JsonSerializerJMS() {
        objectMapper = new ObjectMapper();
        SerializationConfig config = objectMapper.getSerializationConfig().withView(JMSView.KView.class);
        objectMapper.setConfig(config);
        objectMapper.configure(MapperFeature.DEFAULT_VIEW_INCLUSION, false);
    }

    @Override
    public void configure(Map<String, ?> map, boolean b) {
    }

    @Override
    public byte[] serialize(String topic, T data) {
        if(data != null) {
            try {
                return objectMapper.writeValueAsBytes(data);
            } catch (JsonProcessingException e) {
                throw new SerializationException("Can't serialize data [" + data + "] for topic [" + topic + "]", e);
            }
        }
        return null;
    }

    @Override
    public void close() {
    }

    @Override
    public byte[] serialize(String topic, Headers headers, T data) {
        return serialize(topic, data);
    }

}