package com.appsdeveloperblog.core.event;

import java.math.BigDecimal;
import java.util.UUID;

public class ProductReservedFailedEvent {

	private UUID orderId;
	private UUID productId;
	private Integer productQuantity;

	public ProductReservedFailedEvent() {
		super();
	}

	public ProductReservedFailedEvent(UUID orderId, UUID productId, Integer productQuantity) {
		super();
		this.orderId = orderId;
		this.productId = productId;
		this.productQuantity = productQuantity;

	}

	public UUID getOrderId() {
		return orderId;
	}

	public void setOrderId(UUID orderId) {
		this.orderId = orderId;
	}

	public UUID getProductId() {
		return productId;
	}

	public void setProductId(UUID productId) {
		this.productId = productId;
	}

	public Integer getProductQuantity() {
		return productQuantity;
	}

	public void setProductQuantity(Integer productQuantity) {
		this.productQuantity = productQuantity;
	}

}
