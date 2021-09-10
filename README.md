# currency-conversion-service
    part of Currency Application currency-exchange-service , currency-conversion-service , git-localconfig-repo and spring-cloud-config-server


*Setting up Currency Conversion Microservice

On Spring Initializr, choose:

    Group Id: com.hanil.microservices
    Artifact Id: currency-conversion-service
    Dependencies
    Web
    DevTools
    Actuator
    Config Client
    Create Currency Conversion Microservice using Spring Initializr.

* /currency-conversion-service/src/main/resources/application.properties Modified
    spring.application.name=currency-conversion
    server.port=8100

* Creating a service for currency conversion

    URL-  http://localhost:8100/currency-conversion/from/USD/to/INR/quantity/10
    
    {
    "id": 10001,
    "from": "USD",
    "to": "INR",
    "conversionMultiple": 65.00,
    "quantity": 10,
    "totalCalculatedAmount": 650.00,
    "environment": "8000 instance-id"
    }

* New files added  
    /currency-conversion-service/src/main/java/com/hanil/microservices/currencyconversionservice/CurrencyConversionController.java New

    /currency-conversion-service/src/main/java/com/hanil/microservices/currencyconversionservice/CurrencyConversion.java New

* Invoking Currency Exchange Microservice from Currency Conversion Microservice
    in controller , instead of hardcoding , call the exchange microservice as below 

    	HashMap<String, String> uriVariables = new HashMap<>();
		uriVariables.put("from",from);
		uriVariables.put("to",to);
		
		ResponseEntity<CurrencyConversion> responseEntity = new RestTemplate().getForEntity
		("http://localhost:8000/currency-exchange/from/{from}/to/{to}", 
				CurrencyConversion.class, uriVariables);
		
		CurrencyConversion currencyConversion = responseEntity.getBody();

* Using Feign REST Client for Service Invocation

    URLs:
        http://localhost:8100/currency-conversion/from/USD/to/INR/quantity/10
        http://localhost:8100/currency-conversion-feign/from/USD/to/INR/quantity/10

    /currency-conversion-service/pom.xml Modified
    New Lines added

		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-openfeign</artifactId>
		</dependency>

* add file - /currency-conversion-service/src/main/java/com/hanil/microservices/currencyconversionservice/CurrencyExchangeProxy.java 

    @FeignClient(name="currency-exchange", url="localhost:8000")
    public interface CurrencyExchangeProxy {
        
        @GetMapping("/currency-exchange/from/{from}/to/{to}")
        public CurrencyConversion retrieveExchangeValue(
                @PathVariable("from") String from,
                @PathVariable("to") String to);

    }

* Ensure that you have the annotation @EnableFeignClients with right packages on the class public class CurrencyConversionServiceApplication @EnableFeignClients("com.hanil.microservices.currencyconversionservice")

* /currency-conversion-service/src/main/java/com/hanil/microservices/currencyconversionservice/CurrencyConversionController.java Modified

    @GetMapping("/currency-conversion-feign/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversion calculateCurrencyConversionFeign(
			@PathVariable String from,
			@PathVariable String to,
			@PathVariable BigDecimal quantity
			) {
				
		CurrencyConversion currencyConversion = proxy.retrieveExchangeValue(from, to);
		
		return new CurrencyConversion(currencyConversion.getId(), 
				from, to, quantity, 
				currencyConversion.getConversionMultiple(), 
				quantity.multiply(currencyConversion.getConversionMultiple()), 
				currencyConversion.getEnvironment() + " " + "feign");
		
	}
	
* Step 20 - Connect Currency Conversion Microservice & Currency Exchange Microservice to Eureka

/currency-conversion-service/src/main/resources/application.properties Modified
New Lines

eureka.client.serviceUrl.defaultZone=http://localhost:8761/eureka


/currency-conversion-service/pom.xml Modified
New Lines

		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
		</dependency>
