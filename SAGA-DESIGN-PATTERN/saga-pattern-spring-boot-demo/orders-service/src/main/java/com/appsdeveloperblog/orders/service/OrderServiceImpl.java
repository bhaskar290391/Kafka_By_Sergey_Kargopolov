package com.appsdeveloperblog.orders.service;

import com.appsdeveloperblog.core.dto.Order;
import com.appsdeveloperblog.core.event.OrderCreatedEvent;
import com.appsdeveloperblog.core.types.OrderStatus;
import com.appsdeveloperblog.orders.dao.jpa.entity.OrderEntity;
import com.appsdeveloperblog.orders.dao.jpa.repository.OrderRepository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

	@Value("${orders.event.topic.name}")
	private String orderEventTopicName;

	private final OrderRepository orderRepository;
	private final KafkaTemplate<String, Object> template;

	public OrderServiceImpl(OrderRepository orderRepository, KafkaTemplate<String, Object> template) {
		this.orderRepository = orderRepository;
		this.template = template;
	}

	@Override
	public Order placeOrder(Order order) {
		OrderEntity entity = new OrderEntity();
		entity.setCustomerId(order.getCustomerId());
		entity.setProductId(order.getProductId());
		entity.setProductQuantity(order.getProductQuantity());
		entity.setStatus(OrderStatus.CREATED);
		orderRepository.save(entity);

		OrderCreatedEvent eveent = new OrderCreatedEvent(entity.getId(), entity.getCustomerId(), entity.getProductId(),
				entity.getProductQuantity());
		
		template.send(orderEventTopicName, eveent);

		return new Order(entity.getId(), entity.getCustomerId(), entity.getProductId(), entity.getProductQuantity(),
				entity.getStatus());
	}

}
