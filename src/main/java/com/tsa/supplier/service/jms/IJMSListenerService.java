package com.tsa.supplier.service.jms;

public interface IJMSListenerService {

    void start();

    void stop();

    void refresh();

    boolean isRunning();

}