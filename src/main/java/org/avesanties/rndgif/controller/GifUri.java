package org.avesanties.rndgif.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.ThreadLocalRandom;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotEmpty;

public final class GifUri {
	
	@NotEmpty
	private final String url;
	
	@NotEmpty
	private final String api_key;
	
	@NotEmpty
	private final String qHigher;
	
	@NotEmpty
	private final String qLower;
	
	@NotEmpty
	private final String rating;
	
	@NotEmpty
	private final String lang;
	
	@NotEmpty
	private final String limit = "1";
	
	@Digits(fraction = 0, integer = 4999)
	private final int offsetLimit;
	
	public GifUri(String url,
			String api_key,
			String qHigher,
			String qLower,
			String rating,
			String lang,
			int offsetLimit) {
		this.url = url;
		this.api_key = api_key;
		this.qHigher = qHigher;
		this.qLower = qLower;
		this.rating = rating;
		this.lang = lang;
		this.offsetLimit = offsetLimit;
	}
	
	public URI exchangeRateHiger() {
		int offset = ThreadLocalRandom.current().nextInt(offsetLimit);
		try {
			return new URI(url
					+ "?api_key=" + api_key
					+ "&q=" + qHigher
					+ "&limit=" + limit
					+ "&rating=" + rating
					+ "&lang=" + lang
					+ "&offset=" + offset);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public URI exchangeRateLower(){
		int offset = ThreadLocalRandom.current().nextInt(offsetLimit);
		
		try {
			return new URI(url
					+ "?api_key=" + api_key
					+ "&q=" + qLower
					+ "&limit=" + limit
					+ "&rating=" + rating
					+ "&lang=" + lang
					+ "&offset=" + offset);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
}
