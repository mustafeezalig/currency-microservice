package com.exchange.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.exchange.model.CurrencyConversion;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;

@RestController
@RequestMapping("/api")
public class ConversionController {
	private Logger LOGGER = LoggerFactory.getLogger(ConversionController.class);
	private static int count = 1;
	@Autowired
	private RestTemplate restTemplate;

	@Retry(name = "conversion-service")
	@CircuitBreaker(name = "conversion-service", fallbackMethod = "exchangeFallback")
	@GetMapping("/currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
	public ResponseEntity<?> getCurrencyExchange(@PathVariable String from, @PathVariable String to,
			@PathVariable BigDecimal quantity) {
		LOGGER.info("Calling ConversionController.....................");
		Map<String, String> uriVariables = new HashMap<>();
		uriVariables.put("from", from);
		uriVariables.put("to", to);
		LOGGER.info("Attempt : {}", count++);
		ResponseEntity<CurrencyConversion> currencyConversionEntity = restTemplate.getForEntity(
				"http://currency-exchanges/api/currency-exchange/from/{from}/to/{to}", CurrencyConversion.class,
				uriVariables);

		CurrencyConversion currencyConversion = currencyConversionEntity.getBody();
		currencyConversion.setQuantity(quantity);
		currencyConversion.setTotalCalculagedAmount(quantity.multiply(currencyConversion.getConversionMultiple()));
		return ResponseEntity.status(HttpStatus.SC_OK).body(currencyConversion);

	}

	public ResponseEntity<?> exchangeFallback(String from, String to, BigDecimal quantity, Exception ex) {
		return ResponseEntity.status(HttpStatus.SC_SERVICE_UNAVAILABLE)
				.body("Currency Exchange service is unavailable. Please try again later.");
	}

}
