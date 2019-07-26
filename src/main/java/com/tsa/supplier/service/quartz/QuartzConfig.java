package com.tsa.supplier.service.quartz;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
public class QuartzConfig {

    @Bean
    @Qualifier("quartzDataSourceProperties")
    @ConfigurationProperties("quartz.datasource")
    public DataSourceProperties quartzDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @Qualifier("quartzDataSource")
    @ConfigurationProperties("quartz.datasource.configuration")
    public HikariDataSource quartzDataSource(
            @Autowired @Qualifier("quartzDataSourceProperties") DataSourceProperties dataSourceProperties) {
        return dataSourceProperties.initializeDataSourceBuilder()
                .type(HikariDataSource.class).build();
    }

    @Bean
    @Qualifier("quartzSourceTransactionManager")
    public PlatformTransactionManager quartzSourceTransactionManager(
            @Autowired @Qualifier("quartzDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    @Qualifier("quartzProperties")
    @ConfigurationProperties("quartz.properties")
    public Properties quartzProperties() {
        return new Properties();
    }

    @Bean
    @Qualifier("priceUploadSchedulerFactoryBean")
    public SchedulerFactoryBean getSchedulerFactoryBean(
            @Autowired @Qualifier("quartzProperties") Properties properties,
            @Autowired @Qualifier("quartzDataSource") DataSource dataSource,
            @Autowired @Qualifier("quartzSourceTransactionManager") PlatformTransactionManager transactionManager,
            @Autowired ApplicationContext applicationContext) {

        SchedulerFactoryBean quartzScheduler = new SchedulerFactoryBean();
        quartzScheduler.setOverwriteExistingJobs(true);
        quartzScheduler.setStartupDelay(10);

        quartzScheduler.setDataSource(dataSource);
        quartzScheduler.setTransactionManager(transactionManager);
        quartzScheduler.setWaitForJobsToCompleteOnShutdown(false);

        // custom job factory of spring with DI support for @Autowired
        QuartzAutowiringSpringBeanJobFactory jobFactory = new QuartzAutowiringSpringBeanJobFactory();
        jobFactory.setApplicationContext(applicationContext);
        quartzScheduler.setJobFactory(jobFactory);
        quartzScheduler.setQuartzProperties(properties);
        return quartzScheduler;
    }

}