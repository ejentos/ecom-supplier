package com.tsa.supplier.service.jms.kafka.listener;

import com.tsa.supplier.service.entity.Good;
import com.tsa.supplier.service.IGoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class GoodKafkaEventListenerTaskImpl extends AbstractKafkaEventListenerTask<Good> {

    @Value("${kafka.consumer.inventory.topic.good.insert}")
    private String topicInsert;

    @Value("${kafka.consumer.inventory.topic.good.update}")
    private String topicUpdate;

    @Value("${kafka.consumer.inventory.topic.good.delete}")
    private String topicDelete;

    @Autowired
    private IGoodService goodService;

    @Override
    protected void init() {
        this.topics = Set.of(topicInsert, topicUpdate, topicDelete);
        this.topicConsumerMap = Map.of(
                topicInsert, goodService::merge,
                topicUpdate, goodService::merge,
                topicDelete, goodService::delete
        );
    }

    @Override
    protected Class<Good> getGenericClass() {
        return Good.class;
    }

}