package org.avesanties.rndgif.controller;

import java.math.BigDecimal;
import java.net.URI;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

import org.avesanties.rndgif.AppExceptions.NotInitializedDateOrRateException;
import org.avesanties.rndgif.data.RateDateService;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping("/")
public class RateDateController {
	
	RestTemplate restTemplate;
	
	RateDateService rateDateService;
	
	ExchangeRateUri exchangeRateUri;
	
	GifUri gifUri;
	
	public RateDateController(RestTemplate restTemplate,
			ExchangeRateUri exchangeRateUri,
			GifUri gifUri,
			RateDateService rateDateService) {
		
		this.restTemplate = restTemplate;
		this.rateDateService = rateDateService;
		this.exchangeRateUri = exchangeRateUri;
		this.gifUri = gifUri;
	}
	
	@GetMapping
	public ResponseEntity<?> getStatus() {
		
		if(rateDateService.isEmptyOrOutdated()) {
			
			BigDecimal yesterdayRate = getExchangeRate(exchangeRateUri.yesterdayUri());
			
			if(yesterdayRate == null) {
				
				return new ResponseEntity<String>("exchange rate api is unavailiable", HttpStatusCode.valueOf(404));
			}
			
			LocalDate yesterdayDate = LocalDate.now().minusDays(1L);
			
			rateDateService.updateRate(yesterdayDate, yesterdayRate);
		}
		BigDecimal todayRate = getExchangeRate(exchangeRateUri.nowUri());
		
		ResponseEntity<byte[]> response = null;
		try {
			if(rateDateService.isTodayRateHigher(todayRate)) 
				response = getGif(gifUri.exchangeRateHiger());
			else 
				response = getGif(gifUri.exchangeRateLower());
		} catch (NotInitializedDateOrRateException e) {
			e.printStackTrace();
		}
		
		return response;
	}
	
	public BigDecimal getExchangeRate(URI uri) {	
		
		try {
			String response = restTemplate.getForObject(uri, String.class);
			
			JsonNode root = new ObjectMapper().readTree(response);
			
			//get rate
			BigDecimal rate = new BigDecimal( 
					root.findValue(rateDateService.getCode()).asText());
			
			//get date
			LocalDate date = LocalDate.ofInstant(
					Instant.ofEpochSecond( 
					root.findValue("timestamp").asLong()), ZoneId.systemDefault());
			
			rateDateService.updateRate(date, rate);
			return rate;
					
		} catch(RestClientException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public ResponseEntity<byte[]> getGif(URI uri) {
		try {
			String response = restTemplate.getForObject(uri, String.class);
			
			JsonNode root = new ObjectMapper().readTree(response);
			
			//get Url of gif with original size 
			String gifUrl = root.findValue("original").findValue("url").asText();
			ResponseEntity<byte[]> responEntity = restTemplate.getForEntity(gifUrl, byte[].class);
			return responEntity;
			
		} catch(RestClientException e) {
			e.printStackTrace();
		} catch(JsonProcessingException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return ResponseEntity.status(404).build();
	}
}
