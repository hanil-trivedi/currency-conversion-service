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
    /currency-conversion-service/src/main/java/com/in28minutes/microservices/currencyconversionservice/CurrencyConversionController.java New

    /currency-conversion-service/src/main/java/com/in28minutes/microservices/currencyconversionservice/CurrencyConversion.java New

* Invoking Currency Exchange Microservice from Currency Conversion Microservice

