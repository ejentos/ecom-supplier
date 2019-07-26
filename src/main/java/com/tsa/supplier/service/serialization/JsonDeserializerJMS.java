package com.tsa.supplier.service.serialization;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.tsa.supplier.service.view.JMSView;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

import java.io.IOException;
import java.util.Map;

public class JsonDeserializerJMS<T> implements Deserializer<T> {

    private final ObjectMapper objectMapper;
    private final Class<T> classObj;

    public JsonDeserializerJMS(Class<T> classObj) {
        if(classObj == null) throw new IllegalArgumentException("Invalid deserialization class");
        this.objectMapper = new ObjectMapper();
        SerializationConfig config = this.objectMapper.getSerializationConfig().withView(JMSView.KView.class);
        this.objectMapper.setConfig(config);
        this.objectMapper.configure(MapperFeature.DEFAULT_VIEW_INCLUSION, false);
        this.objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        this.classObj = classObj;
    }

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
    }

    @Override
    public T deserialize(String topic, byte[] data) {
        T obj = null;
        try {
            obj = objectMapper.readValue(data, classObj);
        } catch (IOException e) {
            throw new SerializationException("Can't deserialize data [" + data + "] for topic [" + topic + "]", e);
        }
        return obj;
    }

    @Override
    public void close() {
    }

}