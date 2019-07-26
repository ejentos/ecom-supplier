package com.tsa.supplier.service.jms.kafka.listener;

import com.tsa.supplier.service.entity.Brand;
import com.tsa.supplier.service.IBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class BrandKafkaEventListenerTaskImpl extends AbstractKafkaEventListenerTask<Brand> {

    @Value("${kafka.consumer.inventory.topic.brand.insert}")
    private String topicInsert;

    @Value("${kafka.consumer.inventory.topic.brand.update}")
    private String topicUpdate;

    @Value("${kafka.consumer.inventory.topic.brand.delete}")
    private String topicDelete;

    @Autowired
    private IBrandService brandService;

    @Override
    protected void init() {
        this.topics = Set.of(topicInsert, topicUpdate, topicDelete);
        this.topicConsumerMap = Map.of(
                topicInsert, brandService::merge,
                topicUpdate, brandService::merge,
                topicDelete, brandService::delete
        );
    }

    @Override
    protected Class<Brand> getGenericClass() {
        return Brand.class;
    }

}