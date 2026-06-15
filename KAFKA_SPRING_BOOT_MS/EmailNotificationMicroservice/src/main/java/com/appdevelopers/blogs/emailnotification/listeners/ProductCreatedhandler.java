package com.appdevelopers.blogs.emailnotification.listeners;

import com.appdeveloperblogs.core.event.ProductCreatedEvent;
import com.appdevelopers.blogs.emailnotification.exception.NotRetryableException;
import com.appdevelopers.blogs.emailnotification.exception.RetryableException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

@Component
@KafkaListener(topics = "product-created-event-topic",containerFactory = "concurrentKafkaListenerContainerFactory")
public class ProductCreatedhandler {


    private final Logger logger= LoggerFactory.getLogger(this.getClass());

    private RestTemplate template ;

    public ProductCreatedhandler(RestTemplate template) {
        this.template = template;
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
    public void handle(ProductCreatedEvent product){
        logger.info("Event received from Producer ==> "+product.getTitle());
        try{
            String requestUrl="http://localhost:8082/response/500";

            ResponseEntity<String> response= template.exchange(requestUrl, HttpMethod.GET,null,String.class);


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

    }
}
