package com.exchange.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.exchange.model.CurrencyConversion;

@RestController
@RequestMapping("/api")
public class ConversionController {
	
	@Autowired
	private RestTemplate restTemplate;
	@GetMapping("/currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversion getCurrencyExchange(@PathVariable String from, @PathVariable String to,@PathVariable BigDecimal quantity) {
		Map<String,String> uriVariables=new HashMap<>();
		uriVariables.put("from", from);
		uriVariables.put("to", to);
		ResponseEntity<CurrencyConversion>	currencyConversionEntity=restTemplate.getForEntity("http://localhost:8082/api//currency-exchange/from/{from}/to/{to}",CurrencyConversion.class, uriVariables);
		CurrencyConversion currencyConversion= currencyConversionEntity.getBody();
		currencyConversion.setQuantity(quantity);
		currencyConversion.setTotalCalculagedAmount(quantity.multiply(currencyConversion.getConversionMultiple()));
		return currencyConversion;
	}

}
