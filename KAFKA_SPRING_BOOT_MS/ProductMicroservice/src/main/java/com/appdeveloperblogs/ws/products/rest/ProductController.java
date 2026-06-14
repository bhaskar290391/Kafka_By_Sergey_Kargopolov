package com.appdeveloperblogs.ws.products.rest;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.appdeveloperblogs.ws.products.model.CreateProductResModel;
import com.appdeveloperblogs.ws.products.service.ProductService;

@RestController
@RequestMapping("/products")
public class ProductController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private ProductService service;
	
	public ProductController(ProductService service) {
		this.service = service;
	}

	@PostMapping()
	public ResponseEntity<Object> createProduct(@RequestBody CreateProductResModel product){
		String productId;
		try {
			productId = service.createProduct(product);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return ResponseEntity.status(HttpStatus.CREATED).body(new ErrorMessage(new Date(),HttpStatus.INTERNAL_SERVER_ERROR.toString(),"/products"));
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(productId);
	}
}
