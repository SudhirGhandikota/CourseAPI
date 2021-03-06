package com.Test.CourseAPI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.guava.GuavaCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Main Application Class
 *
 */
@SpringBootApplication
@EnableTransactionManagement
@EnableCaching
public class Application 
{
    public static void main( String[] args ) throws Exception
    {
        SpringApplication.run(Application.class,args);
    }
    @Bean
    public CacheManager cacheManager()
    {
    	GuavaCacheManager cacheManager = new GuavaCacheManager("cached_courses");
    	return cacheManager;
    }
}
