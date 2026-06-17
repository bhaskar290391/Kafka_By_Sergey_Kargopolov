package com.appdeveloperblogs.ws.products;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

@DirtiesContext
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
@EmbeddedKafka(
    partitions = 3,
    count = 1,
    controlledShutdown = true
)
@SpringBootTest(properties = {
    "spring.kafka.producer.bootstrap-servers=${spring.embedded.kafka.brokers}",
    "spring.kafka.consumer.bootstrap-servers=${spring.embedded.kafka.brokers}"
})
public class ProductServiceIntegrationTest {

	@Test
	public void testCreateProduct_whenGivenValidProductDetails_successfulSendKafkaMessage(){
		
	}
}
