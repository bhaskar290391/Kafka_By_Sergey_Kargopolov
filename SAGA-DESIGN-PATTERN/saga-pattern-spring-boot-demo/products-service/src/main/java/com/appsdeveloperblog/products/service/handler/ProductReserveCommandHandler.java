package com.appsdeveloperblog.products.service.handler;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.appsdeveloperblog.core.commands.ReserveProductCommand;
import com.appsdeveloperblog.core.dto.Product;
import com.appsdeveloperblog.core.event.ProductReservedEvent;
import com.appsdeveloperblog.core.event.ProductReservedFailedEvent;
import com.appsdeveloperblog.products.service.ProductService;

@Component
@KafkaListener(topics = { "${products.commands.topic.name}" })
public class ProductReserveCommandHandler {

	private static Logger logger = org.slf4j.LoggerFactory.getLogger(ProductReserveCommandHandler.class);
	private KafkaTemplate<String, Object> kafkaTemplate;
	private ProductService productService;
	private String productEventTopicName;

	public ProductReserveCommandHandler(KafkaTemplate<String, Object> kafkaTemplate, ProductService productService,
			@Value("${products.event.topic.name}") String productEventTopicName) {

		this.kafkaTemplate = kafkaTemplate;
		this.productService = productService;
		this.productEventTopicName = productEventTopicName;
	}

	@KafkaHandler
	public void handle(@Payload ReserveProductCommand command) {

		try {
			Product desiredProduct = new Product(command.getProductId(), command.getProductQuantity());

			Product reservedProduct = productService.reserve(desiredProduct, command.getOrderId());
			ProductReservedEvent productReservedEvent = new ProductReservedEvent(command.getOrderId(),
					command.getProductId(), command.getProductQuantity(), reservedProduct.getPrice());
			kafkaTemplate.send(productEventTopicName, productReservedEvent);
		} catch (Exception e) {
			logger.error("Exception ==>");
			ProductReservedFailedEvent failed = new ProductReservedFailedEvent(command.getOrderId(),
					command.getProductId(), command.getProductQuantity());
			kafkaTemplate.send(productEventTopicName, failed);
		}
	}
}
