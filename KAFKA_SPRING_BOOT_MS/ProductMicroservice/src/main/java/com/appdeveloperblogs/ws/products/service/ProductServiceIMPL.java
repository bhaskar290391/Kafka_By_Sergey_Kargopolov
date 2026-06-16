package com.appdeveloperblogs.ws.products.service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import com.appdeveloperblogs.core.event.ProductCreatedEvent;
import com.appdeveloperblogs.ws.products.model.CreateProductResModel;

@Service
public class ProductServiceIMPL implements ProductService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private KafkaTemplate<String, ProductCreatedEvent> kafkaTemplate;

	public ProductServiceIMPL(KafkaTemplate<String, ProductCreatedEvent> kafkaTemplate) {
		this.kafkaTemplate = kafkaTemplate;
	}

	@Override
	public String createProduct(CreateProductResModel product) throws Exception {

		String productId = UUID.randomUUID().toString();

		ProductCreatedEvent eventData = new ProductCreatedEvent(productId, product.getTitle(), product.getQuantity(),
				product.getPrices());

		/*
		 
		 	CompletableFuture<SendResult<String, ProductCreatedEvent>> dataEvent = kafkaTemplate
				.send("product-create-event-topic", productId, eventData);

			dataEvent.whenComplete((result, exception) -> {
	
				if (exception != null) {
					logger.error("******* Failed to send a message ==>" + exception.getMessage());
				} else {
					logger.info("******* Message Sent successfully " + result.getRecordMetadata());
				}
		});
		
		 */
	
		//for adding header for idempotent consumer
		ProducerRecord<String, ProductCreatedEvent> producerRecord=new ProducerRecord<String, ProductCreatedEvent>("product-created-event-topic", productId, eventData);
		producerRecord.headers().add("messageId", UUID.randomUUID().toString().getBytes());
		//producerRecord.headers().add("messageId", "bhaskar".toString().getBytes());
		
	 	SendResult<String, ProductCreatedEvent> dataEvent = kafkaTemplate
				.send(producerRecord).get();
	 	
	 	logger.info("Topics ==>"+dataEvent.getRecordMetadata().topic());
	 	logger.info("Offset ==>"+dataEvent.getRecordMetadata().offset());
	 	logger.info("Partitions ==>"+dataEvent.getRecordMetadata().partition());

		logger.info("**************** Returning Product Id");
		return productId;
	}

}
