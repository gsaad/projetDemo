package fr.service.impl;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.ClasspathPropertiesFileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3Client;

@Configuration
public class AmazonConfiguration {
    
	/**
     * creer un client Amazon S3 
     * @return AmazonS3Client
     */
    @Bean
    public AmazonS3Client amazonS3Client() {
    	return new AmazonS3Client(
    			new ClasspathPropertiesFileCredentialsProvider());
    }
}