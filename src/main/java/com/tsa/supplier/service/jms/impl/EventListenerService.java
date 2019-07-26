package com.tsa.supplier.service.jms.impl;

import com.tsa.supplier.service.jms.IJMSListenerService;
import com.tsa.supplier.service.jms.kafka.listener.IEventListenerTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class EventListenerService implements IJMSListenerService {

    private final static Logger LOGGER = LoggerFactory.getLogger(EventListenerService.class);

    @Value("${kafka.consumer.termination.timeout-ms}")
    private long timeout;
    private AtomicBoolean running = new AtomicBoolean(false);
    private ApplicationContext context;
    private ExecutorService executorService;

    // TODO for better monitoring it's possible to keep information about each task.
    // TODO Look at Future<T> or CompletableFuture<T>
    private Collection<IEventListenerTask> tasks;

    public EventListenerService(@Autowired ApplicationContext context) {
        this.context = context;
    }

    @PostConstruct
    private void afterPropertiesSet() throws Exception {
        this.start();
    }

    @Override
    public synchronized void start() {
        if (!running.get()) {
            tasks = this.getTasks();
            if(!tasks.isEmpty()) {
                executorService = Executors.newFixedThreadPool(tasks.size());
                tasks.forEach(executorService::execute);
                running.set(true);
            }
        }
    }

    @PreDestroy
    @Override
    public synchronized void stop() {
        if (running.get()) {
            if (executorService != null) {
                // attempt to shut down consumers properly
                shutdownTasks();
                try {
                    executorService.shutdown();
                    if (!executorService.awaitTermination(timeout, TimeUnit.MILLISECONDS)) {
                        executorService.shutdownNow();
                    }
                    running.set(false);
                } catch (Exception e) {
                    LOGGER.error("Kafka consumer services. Termination issue", e);
                }
            }
        }
    }

    @Override
    public synchronized void refresh() {
        this.stop();
        this.start();
    }

    @Override
    public boolean isRunning() {
        return running.get();
    }

    private Collection<IEventListenerTask> getTasks() {
        if(context != null) {
            return context.getBeansOfType(IEventListenerTask.class).values();
        }
        return Collections.EMPTY_LIST;
    }

    private synchronized void shutdownTasks() {
        if (tasks != null) {
            for (IEventListenerTask task : tasks) {
                try {
                    task.shutdown();
                } catch (Exception e) {
                    LOGGER.error("MQ consumer services. Proper termination issue. Task:" + task, e);
                }
            }
        }
    }

}