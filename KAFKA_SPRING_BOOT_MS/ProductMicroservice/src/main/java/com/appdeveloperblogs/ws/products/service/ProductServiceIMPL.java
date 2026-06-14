package com.appdeveloperblogs.ws.products.service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import com.appdeveloperblogs.ws.products.event.ProductCreatedEvent;
import com.appdeveloperblogs.ws.products.model.CreateProductResModel;

@Service
public class ProductServiceIMPL implements ProductService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private KafkaTemplate<String, ProductCreatedEvent> kafkaTemplate;

	public ProductServiceIMPL(KafkaTemplate<String, ProductCreatedEvent> kafkaTemplate) {
		this.kafkaTemplate = kafkaTemplate;
	}

	@Override
	public String createProduct(CreateProductResModel product) {

		String productId = UUID.randomUUID().toString();

		ProductCreatedEvent eventData = new ProductCreatedEvent(productId, product.getTitle(), product.getQuantity(),
				product.getPrices());

		CompletableFuture<SendResult<String, ProductCreatedEvent>> dataEvent = kafkaTemplate
				.send("product-create-event-topic", productId, eventData);

		dataEvent.whenComplete((result, exception) -> {

			if (exception != null) {
				logger.error("******* Failed to send a message ==>" + exception.getMessage());
			} else {
				logger.info("******* Message Sent successfully " + result.getRecordMetadata());
			}
		});

		logger.info("**************** Returning Product Id");
		return productId;
	}

}
