package org.avesanties.rndgif.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public final class ExchangeRateUri {
	
	@NotEmpty
	private final String latestUrl;
	
	@NotEmpty
	private final String historicalUrl;
	
	@NotEmpty
	private final String app_id;
	
	@NotEmpty
	private final String symbols;
	
	@NotEmpty
	private final String prettyprint;
	
	public ExchangeRateUri(String latestUrl,
			String historicalUrl,
			String app_id,
			String symbols,
			String prettyprint) {
		this.latestUrl = latestUrl;
		this.historicalUrl = historicalUrl;
		this.app_id = app_id;
		this.symbols = symbols;
		this.prettyprint = prettyprint;
	}
	
	@NotNull
	public URI nowUri() {
		
		try {
			return new URI(latestUrl + "?" 
					+ "app_id=" + app_id 
					+ "&symbols=" + symbols 
					+ "&prettyprint=" + prettyprint);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	@NotNull
	public URI yesterdayUri() {
		LocalDate date = LocalDate.now().minusDays(1);
		
		try {
			return new URI(historicalUrl + "/" + date.toString() + ".json"
					+ "?app_id=" + app_id 
					+ "&symbols=" + symbols 
					+ "&prettyprint=" + prettyprint);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
}
