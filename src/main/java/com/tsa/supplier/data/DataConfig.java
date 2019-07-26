package com.tsa.supplier.data;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
public class DataConfig {

    @Bean
    @Primary
    @Qualifier("supplierDataSourceProperties")
    @ConfigurationProperties("datasource")
    public DataSourceProperties dataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @Primary
    @Qualifier("supplierDataSource")
    public HikariDataSource dataSource(
            @Autowired @Qualifier("supplierDataSourceProperties") DataSourceProperties dataSourceProperties) {
        return dataSourceProperties.initializeDataSourceBuilder()
                .type(HikariDataSource.class).build();
    }

    @Bean
    @Primary
    public DataSourceTransactionManager dataSourceTransactionManager(
            @Autowired @Qualifier("supplierDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

}