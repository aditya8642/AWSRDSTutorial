package com.awsrds.config;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "factoryBean",transactionManagerRef = "platformTransactionManager",
						basePackages = {"com.awsrds.repository"})

public class DBConfig {

	@Autowired
	SecretsManagerConfig managerConfig;
	
	@Bean(name="primarydatasource")
	@Primary
	@ConfigurationProperties(prefix = "spring.datasource")
	public DataSource getDataSource() {
		return DataSourceBuilder.create()
				.driverClassName("com.mysql.jdbc.Driver")
				.url("jdbc:mysql://database-1.c8rd92p5ocmn.us-east-2.rds.amazonaws.com:3306/testdb")
				.username(managerConfig.getProperties("username"))
				.password(managerConfig.getProperties("password"))
				.build();
	}
	
	@Primary
	@Bean(name="factoryBean")
	public LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean(@Qualifier("primarydatasource") DataSource dataSource,EntityManagerFactoryBuilder entityManagerFactoryBuilder) {
		return entityManagerFactoryBuilder.dataSource(dataSource)
		.packages("com.awsrds.domain")
		.persistenceUnit("springbootdb")
		.build();
	}
	
	@Primary
	@Bean(name="platformTransactionManager")
	public PlatformTransactionManager platformTransactionManager(@Qualifier("factoryBean") EntityManagerFactory entityManagerFactory)
	{
		return new JpaTransactionManager(entityManagerFactory);
	}

}
