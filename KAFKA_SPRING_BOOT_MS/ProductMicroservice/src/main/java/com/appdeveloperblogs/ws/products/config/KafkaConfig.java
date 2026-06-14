package com.appdeveloperblogs.ws.products.config;

import java.util.Map;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {

	@Bean
	public NewTopic createTopic() {
		return TopicBuilder.name("product-created-event-topic").partitions(3).replicas(1)
				.configs(Map.of("min.insync.replicas", "2")).build();
	}
}
