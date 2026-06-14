package com.appdeveloperblogs.ws.products.event;

import java.math.BigDecimal;

public class ProductCreatedEvent {

	private String productId;
	private String title;
	private Integer quantity;
	private BigDecimal prices;

	public ProductCreatedEvent() {
	}

	public ProductCreatedEvent(String productId, String title, Integer quantity, BigDecimal prices) {

		this.productId = productId;
		this.title = title;
		this.quantity = quantity;
		this.prices = prices;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getPrices() {
		return prices;
	}

	public void setPrices(BigDecimal prices) {
		this.prices = prices;
	}

}
