package com.appdevelopers.blogs.emailnotification.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "product_event_entity")
public class ProductEventEntity {

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false,unique = true)
    private String messageId;

    @Column(nullable = false)
    private String productId;

    public ProductEventEntity() {
    }

    public ProductEventEntity(String messageId, String productId) {
        this.messageId = messageId;
        this.productId = productId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    @Override
    public String toString() {
        return "ProductEventEntity{" +
                "id=" + id +
                ", messageId='" + messageId + '\'' +
                ", productId='" + productId + '\'' +
                '}';
    }
}
