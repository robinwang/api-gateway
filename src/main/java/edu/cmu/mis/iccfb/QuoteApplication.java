package edu.cmu.mis.iccfb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@EnableDiscoveryClient
@SpringBootApplication
public class QuoteApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(QuoteApplication.class, args);
	}
    
    @Bean
    @LoadBalanced
    public RestTemplate getRestTemplate() {
    	return new RestTemplate();
    }
}
