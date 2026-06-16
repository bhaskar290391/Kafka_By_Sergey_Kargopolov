package com.appdevelopers.blogs.emailnotification.listeners;

import com.appdeveloperblogs.core.event.ProductCreatedEvent;
import com.appdevelopers.blogs.emailnotification.entity.ProductEventEntity;
import com.appdevelopers.blogs.emailnotification.exception.NotRetryableException;
import com.appdevelopers.blogs.emailnotification.exception.RetryableException;
import com.appdevelopers.blogs.emailnotification.repository.ProductEventRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

@Component
@KafkaListener(topics = "product-created-event-topic",containerFactory = "concurrentKafkaListenerContainerFactory")
public class ProductCreatedhandler {


    private final Logger logger= LoggerFactory.getLogger(this.getClass());

    private RestTemplate template ;
    private ProductEventRepository repository;

    public ProductCreatedhandler(RestTemplate template,ProductEventRepository repository) {
        this.template = template;
        this.repository=repository;
    }

    //NotRetryableException

   /**

            @KafkaHandler
            public void handle(ProductCreatedEvent product){
                if(true){
                    throw  new NotRetryableException("Exception occured ! Do Not consumed Again !!!");
                }
                logger.info("Event received from Producer ==> "+product.getTitle());
            }
    */

    //RetryableException
    @KafkaHandler
    public void handle(@Payload ProductCreatedEvent product, @Header("messageId") String messageId, @Header(KafkaHeaders.RECEIVED_KEY) String messageKey){
        logger.info("Event received from Producer ==> "+product.getTitle()+" with product Id "+ product.getProductId());

        ProductEventEntity existingMessage=repository.findByMessageId(messageId);

        if(existingMessage !=null){
            logger.error("Found the duplicate message in the database !!!");
            return;
        }

        try{
            String requestUrl="http://localhost:8082/response/200";

            ResponseEntity<String> response= template.exchange(requestUrl, HttpMethod.GET,null,String.class);

            if(response.getStatusCode().value() == HttpStatus.OK.value()){
                logger.info("Response Received ==> "+response.getBody());
            }

        }catch (ResourceAccessException ex){
            logger.error("===> ResourceAccessException "+ex.getMessage());
            throw new RetryableException(ex);
        }
        catch (HttpServerErrorException exception){
            logger.error("===> HttpServerErrorException "+exception.getMessage());
            throw new NotRetryableException(exception);
        }
        catch (Exception e) {
            logger.error(e.getMessage());
            throw new NotRetryableException(e);
        }

        try{
            repository.save(new ProductEventEntity(messageId,product.getProductId()));
        }catch (Exception exception){
            logger.error("Exception occurred while saving product Event");
            throw new NotRetryableException(exception);
        }


    }
}
