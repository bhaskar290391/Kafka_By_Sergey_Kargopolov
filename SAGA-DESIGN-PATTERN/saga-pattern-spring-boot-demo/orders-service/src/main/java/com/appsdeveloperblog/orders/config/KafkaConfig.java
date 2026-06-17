package com.appsdeveloperblog.orders.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

@Configuration
public class KafkaConfig {

    @Value("${orders.event.topic.name}")
    private String orderEventTopicName;
    
    @Value("${products.event.topic.name}")
    private String productEventTopicName;
    
    private final static Integer Topic_Partitions=3;
    private final static Integer Topic_Replication_Factors=3;


    @Bean
    KafkaTemplate<String, Object> kafkaTemplate(ProducerFactory<String, Object> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }

    @Bean
    NewTopic createOrdersTopic(){
    return TopicBuilder.name(orderEventTopicName)
            .partitions(Topic_Partitions)
            .replicas(Topic_Replication_Factors)
            .build();
    }

    @Bean
    NewTopic createProductCommandTopic(){
    return TopicBuilder.name(orderEventTopicName)
            .partitions(Topic_Partitions)
            .replicas(Topic_Replication_Factors)
            .build();
    }

}
