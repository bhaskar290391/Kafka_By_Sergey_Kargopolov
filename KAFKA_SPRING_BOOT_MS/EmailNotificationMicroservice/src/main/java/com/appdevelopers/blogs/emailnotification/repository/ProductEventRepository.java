package com.appdevelopers.blogs.emailnotification.repository;

import com.appdevelopers.blogs.emailnotification.entity.ProductEventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductEventRepository extends JpaRepository<ProductEventEntity,Long> {
    ProductEventEntity findByMessageId(String messageId);
}
