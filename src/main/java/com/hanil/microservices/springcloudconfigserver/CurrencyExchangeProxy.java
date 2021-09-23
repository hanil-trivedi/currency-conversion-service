package com.hanil.microservices.springcloudconfigserver;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//@FeignClient(name="currency-exchange", url="localhost:8000")
@FeignClient(name = "currency-exchange") // remove the base url (ip and port)
										//and the feign will automatically identify the port/ip as per 
										//eureka load balancing using Spring Cloud LoadBalancer)
public interface CurrencyExchangeProxy {

	@GetMapping("/currency-exchange/from/{from}/to/{to}")
	public CurrencyConversion retrieveExchangeValue(@PathVariable("from") String from, @PathVariable("to") String to);

}
