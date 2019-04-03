package com.saaolheart.mumbai.configuration.datasource;

import java.beans.PropertyVetoException;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.saaolheart.mumbai.repository")
public class DatasourceConfig {
	
	@Bean
	@Qualifier("datasource") 
    public DataSource dataSource(){
       DriverManagerDataSource dataSource = new DriverManagerDataSource();
//       dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
       
       /**
        * Off
        */
       dataSource.setDriverClassName("org.mariadb.jdbc.Driver");
       dataSource.setUrl("jdbc:mysql://localhost:3306/test1");
       dataSource.setUsername( "root" );
       dataSource.setPassword( "admin" );
       
       
       /**
        * loc
        */
//       dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
//       dataSource.setUrl("jdbc:mysql://localhost:3306/SAAOL_HEART_ERP");
//       dataSource.setUsername( "root" );
//       dataSource.setPassword( "root" );
//       
       
       /**
        * UAT
        */
//       dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
//       dataSource.setUrl("jdbc:mysql://localhost:3306/SAAOL_HEART_ERP");
//       dataSource.setUsername( "protechnic" );
//       dataSource.setPassword( "protechnic" );
       return dataSource;
    }
	
	@Bean
	public JpaProperties jpaProperties(){
		JpaProperties jpa = new JpaProperties();
		Map<String,String> prop = new HashMap<>();
		prop.put("spring.jpa.show-sql", "true");
		prop.put("spring.jpa.hibernate.ddl-auto", "create");
		jpa.setProperties(prop);
		
		return jpa;
		
		
	}

	 @Bean
	    public LocalContainerEntityManagerFactoryBean entityManagerFactory(@Qualifier("datasource") DataSource ds) throws PropertyVetoException{
	        LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
	        entityManagerFactory.setDataSource(ds);
	        entityManagerFactory.setPackagesToScan(new String[]{"com.saaolheart.mumbai"});
	        JpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
	        entityManagerFactory.setJpaVendorAdapter(jpaVendorAdapter);
	        return entityManagerFactory;
	    }

	    @Bean
	    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory){
	        JpaTransactionManager transactionManager = new JpaTransactionManager();
	        transactionManager.setEntityManagerFactory(entityManagerFactory);
	        return transactionManager;
	    }
}


