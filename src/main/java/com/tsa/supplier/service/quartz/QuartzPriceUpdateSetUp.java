package com.tsa.supplier.service.quartz;

import com.tsa.supplier.service.entity.ProviderPriceUploadSettings;
import com.tsa.supplier.service.IProviderPriceUploadSettingsService;
import com.tsa.supplier.service.quartz.job.PriceUpdateJob;
import org.quartz.CronTrigger;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class QuartzPriceUpdateSetUp {

    private final static Logger LOGGER = LoggerFactory.getLogger(QuartzPriceUpdateSetUp.class);
    private final static String jobGroupName = "priceUploader";
    public final static String PROVIDER_ID_KEY = "providerId";

    @Autowired
    private IProviderPriceUploadSettingsService providerPriceUploadSettingsService;

    @Autowired
    @Qualifier("priceUploadSchedulerFactoryBean")
    private SchedulerFactoryBean schedulerFactoryBean;

    @PostConstruct
    private void afterPropertiesSet() throws Exception {
        List<ProviderPriceUploadSettings> settings = providerPriceUploadSettingsService.getSchedulingProviderSettings();
        if (!settings.isEmpty()) {
            List<Trigger> triggers = new LinkedList<>();

            for (ProviderPriceUploadSettings setting : settings) {
                long providerId = setting.getProviderId();

                JobDetailFactoryBean jobDetailFactory = new JobDetailFactoryBean();
                jobDetailFactory.setName(jobGroupName + providerId);
                jobDetailFactory.setJobClass(PriceUpdateJob.class);
                jobDetailFactory.setGroup(jobGroupName);
                jobDetailFactory.setDurability(true);
                jobDetailFactory.setJobDataAsMap(Map.of(PROVIDER_ID_KEY, providerId));
                jobDetailFactory.afterPropertiesSet();

                CronTriggerFactoryBean cronTriggerFactoryBean = new CronTriggerFactoryBean();
                cronTriggerFactoryBean.setName(jobGroupName + providerId);
                cronTriggerFactoryBean.setJobDetail(jobDetailFactory.getObject());
                cronTriggerFactoryBean.setCronExpression(setting.getJobCronTime());
                cronTriggerFactoryBean.setGroup(jobGroupName);
                cronTriggerFactoryBean.setMisfireInstruction(CronTrigger.MISFIRE_INSTRUCTION_DO_NOTHING);
                cronTriggerFactoryBean.afterPropertiesSet();

                triggers.add(cronTriggerFactoryBean.getObject());
            }

            if (!triggers.isEmpty()) {
                schedulerFactoryBean.setTriggers(triggers.stream().toArray(Trigger[]::new));
                schedulerFactoryBean.afterPropertiesSet();
            }
        }

    }

    @PreDestroy
    public void shutdown() {
        if (schedulerFactoryBean != null) {
            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            if (scheduler != null) {
                try {
                    scheduler.shutdown(true);
                } catch (SchedulerException e) {
                    LOGGER.error("Can't shutdown scheduler properly", e);
                }
            }
        }
    }

}