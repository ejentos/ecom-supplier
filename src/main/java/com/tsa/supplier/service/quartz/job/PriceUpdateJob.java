package com.tsa.supplier.service.quartz.job;

import com.tsa.supplier.service.quartz.QuartzPriceUpdateSetUp;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

//@DisallowConcurrentExecution
@PersistJobDataAfterExecution
public class PriceUpdateJob extends QuartzJobBean {

    private final static Logger LOGGER = LoggerFactory.getLogger(PriceUpdateJob.class);

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try {
            long providerId = jobExecutionContext.getJobDetail().getJobDataMap().getLong(QuartzPriceUpdateSetUp.PROVIDER_ID_KEY);
            // update good prices and availability
        } catch (Exception e) {
            LOGGER.error("Job exception", e);
        }
    }

}