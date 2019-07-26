package com.tsa.supplier.service.jms.kafka.listener;

public interface IEventListenerTask<T> extends Runnable {

    void shutdown();

}