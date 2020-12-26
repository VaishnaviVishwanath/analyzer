package com.analyzer.analyzer.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

@Configuration
public class AppConfig {
	/* function to return mongo db client */
	/*Whenever a MongoClient bean will be @Autowired, this singleton instance will be used there.*/
	
	private static final String MONGO_SERVER_ADDRESS="mongodb://localhost:27017";
	
	@Bean
	public MongoClient mongo() {
		MongoClient mongoClient = MongoClients.create(MONGO_SERVER_ADDRESS);
		return mongoClient;
	}
	
	
}
