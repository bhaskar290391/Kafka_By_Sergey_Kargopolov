package com.appdevelopers.blogs.emailnotification.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JacksonJsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaListenerConfigurations {

    @Value("${spring.kafka.consumer.bootstrap-servers}")
    private String bootstrapServer;

    @Value("${spring.kafka.consumer.key-deserializer}")
    private String keyDeserialization;

    @Value("${spring.kafka.consumer.value-deserializer}")
    private String valueDeserialiazation;

    @Value("${spring.kafka.consumer.group-id}")
    private String consumerGroupId;

    @Value("${spring.kafka.consumer.properties.spring.json.trusted.packages}")
    private String trustedPackage;

    @Bean
    public ConsumerFactory<String,Object> consumerFactory(){

        Map<String, Object> config=new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,bootstrapServer);
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,keyDeserialization);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,valueDeserialiazation);
        config.put(JacksonJsonDeserializer.TRUSTED_PACKAGES,trustedPackage);
        config.put(ConsumerConfig.GROUP_ID_CONFIG,consumerGroupId);
        return  new DefaultKafkaConsumerFactory<>(config);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String,Object> concurrentKafkaListenerContainerFactory
            (ConsumerFactory<String,Object> consumerFactory){
        ConcurrentKafkaListenerContainerFactory<String,Object> data=new ConcurrentKafkaListenerContainerFactory<>();
        data.setConsumerFactory(consumerFactory);

        return  data;
    }
}
