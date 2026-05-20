package com.exchange.model;

import java.math.BigDecimal;

public class CurrencyConversion {

	private Long id;
	private String from;

	public Long getId() {
		return id;
	}

	public CurrencyConversion() {
		super();
	}

	public void setId(Long id) {
		this.id = id;
	}

	public CurrencyConversion(Long id, String from, String to, BigDecimal conversionMultiple,
			BigDecimal totalCalculagedAmount, BigDecimal quantity) {
		super();
		this.id = id;
		this.from = from;
		this.to = to;
		this.conversionMultiple = conversionMultiple;
		this.totalCalculagedAmount = totalCalculagedAmount;
		this.quantity = quantity;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public BigDecimal getConversionMultiple() {
		return conversionMultiple;
	}

	public void setConversionMultiple(BigDecimal conversionMultiple) {
		this.conversionMultiple = conversionMultiple;
	}

	public BigDecimal getTotalCalculagedAmount() {
		return totalCalculagedAmount;
	}

	public void setTotalCalculagedAmount(BigDecimal totalCalculagedAmount) {
		this.totalCalculagedAmount = totalCalculagedAmount;
	}

	public BigDecimal getQuantity() {
		return quantity;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	private String to;
	private BigDecimal conversionMultiple;
	private BigDecimal totalCalculagedAmount;
	private BigDecimal quantity;
	private String env;

	public String getEnv() {
		return env;
	}

	public void setEnv(String env) {
		this.env = env;
	}
}
