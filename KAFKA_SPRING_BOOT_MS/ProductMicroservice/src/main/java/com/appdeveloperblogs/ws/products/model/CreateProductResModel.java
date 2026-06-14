package com.appdeveloperblogs.ws.products.model;

import java.math.BigDecimal;

public class CreateProductResModel {

	private String title;
	private Integer quantity;
	private BigDecimal prices;

	public CreateProductResModel() {
		super();
	}

	public CreateProductResModel(String title, Integer quantity, BigDecimal prices) {
		super();
		this.title = title;
		this.quantity = quantity;
		this.prices = prices;
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

	@Override
	public String toString() {
		return "CreateProductResModel [title=" + title + ", quantity=" + quantity + ", prices=" + prices + "]";
	}

}
