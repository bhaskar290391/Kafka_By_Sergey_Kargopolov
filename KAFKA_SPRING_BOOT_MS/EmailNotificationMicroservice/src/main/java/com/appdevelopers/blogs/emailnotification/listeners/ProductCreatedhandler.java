package com.appdevelopers.blogs.emailnotification.listeners;

import com.appdeveloperblogs.core.event.ProductCreatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@KafkaListener(topics = "product-created-event-topic",containerFactory = "concurrentKafkaListenerContainerFactory")
public class ProductCreatedhandler {

    private final Logger logger= LoggerFactory.getLogger(this.getClass());
    @KafkaHandler
    public void handle(ProductCreatedEvent product){
        logger.info("Event received from Producer ==> "+product.getTitle());
    }
}
