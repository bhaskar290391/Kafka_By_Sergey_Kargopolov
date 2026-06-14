package com.appdeveloperblogs.ws.products.rest;

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

	private ProductService service;
	
	public ProductController(ProductService service) {
		this.service = service;
	}

	@PostMapping()
	public ResponseEntity<String> createProduct(@RequestBody CreateProductResModel product){
		String productId= service.createProduct(product);
		return ResponseEntity.status(HttpStatus.CREATED).body(productId);
	}
}
