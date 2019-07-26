package com.tsa.supplier.service.jms.kafka.listener;

import com.tsa.supplier.service.entity.Category;
import com.tsa.supplier.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class CategoryKafkaEventListenerTaskImpl extends AbstractKafkaEventListenerTask<Category> {

    @Value("${kafka.consumer.inventory.topic.category.insert}")
    private String topicInsert;

    @Value("${kafka.consumer.inventory.topic.category.update}")
    private String topicUpdate;

    @Value("${kafka.consumer.inventory.topic.category.delete}")
    private String topicDelete;

    @Autowired
    private ICategoryService categoryService;

    @Override
    protected void init() {
        this.topics = Set.of(topicInsert, topicUpdate, topicDelete);
        this.topicConsumerMap = Map.of(
                topicInsert, categoryService::merge,
                topicUpdate, categoryService::merge,
                topicDelete, categoryService::delete
        );
    }

    @Override
    protected Class<Category> getGenericClass() {
        return Category.class;
    }

}