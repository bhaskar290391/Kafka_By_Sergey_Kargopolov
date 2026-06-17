package com.appsdeveloperblog.orders.saga;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.appsdeveloperblog.core.commands.ProductCommandsEvent;
import com.appsdeveloperblog.core.event.OrderCreatedEvent;
import com.appsdeveloperblog.core.types.OrderStatus;
import com.appsdeveloperblog.orders.service.OrderHistoryService;

@Component
@KafkaListener(topics = { "${orders.event.topic.name}" })
public class OrderSaga {

	@Value("${products.event.topic.name}")
	private String productEventTopicName;

	private OrderHistoryService orderHistoryServicel;

	private KafkaTemplate<String, Object> kafkaTemplate;

	public OrderSaga(KafkaTemplate<String, Object> kafkaTemplate, OrderHistoryService orderHistoryServicel) {
		this.kafkaTemplate = kafkaTemplate;
		this.orderHistoryServicel = orderHistoryServicel;
	}

	@KafkaHandler
	public void handleEvent(@Payload OrderCreatedEvent orderCreatedEvent) {

		ProductCommandsEvent productCommands = new ProductCommandsEvent(orderCreatedEvent.getProductId(),
				orderCreatedEvent.getOrderId(), orderCreatedEvent.getProductQuantity());

		kafkaTemplate.send(productEventTopicName, productCommands);

		orderHistoryServicel.add(orderCreatedEvent.getOrderId(), OrderStatus.CREATED);

	}
}
