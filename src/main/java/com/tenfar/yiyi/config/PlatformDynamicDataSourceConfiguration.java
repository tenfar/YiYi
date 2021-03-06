package com.tenfar.yiyi.config;

import com.tenfar.yiyi.database.DynamicDataSource;
import com.tenfar.yiyi.database.DynamicDataSourceTransactionManager;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

@Configuration
@EnableTransactionManagement(proxyTargetClass = true)
public class PlatformDynamicDataSourceConfiguration {

    @Bean
    public SqlSessionFactoryBean sqlSessionFactory(@Qualifier(value = "dynamicDataSource") DynamicDataSource dataSource) throws SQLException, IOException {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        Properties properties = new Properties();
        properties.setProperty("sqlType", "mysql");
        sqlSessionFactoryBean.setConfigurationProperties(properties);
        return sqlSessionFactoryBean;
    }

    @Bean
    public DynamicDataSourceTransactionManager transactionManager(@Qualifier(value = "dynamicDataSource") DynamicDataSource dataSource) {
        DynamicDataSourceTransactionManager dynamicDataSourceTransactionManager = new DynamicDataSourceTransactionManager();
        dynamicDataSourceTransactionManager.setDataSource(dataSource);
        return dynamicDataSourceTransactionManager;
    }

}
