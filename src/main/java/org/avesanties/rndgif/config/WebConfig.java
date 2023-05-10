package org.avesanties.rndgif.config;

import java.net.URISyntaxException;

import org.avesanties.rndgif.controller.ExchangeRateUri;
import org.avesanties.rndgif.controller.GifUri;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
@ComponentScan("org.avesanties.rndgif.controller")
@PropertySource("classpath:web.properties")
public class WebConfig implements WebMvcConfigurer{
	
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
	
	@Bean
	public ExchangeRateUri exchangeRateUri(@Value("${exchangeRateUri.latest.url}") String latestUrl,
			@Value("${exchangeRateUri.historical.url}") String historicalUrl,
			@Value("${exchangeRateUri.app_id}") String app_id,
			@Value("${exchangeRateUri.symbols}") String symbols,
			@Value("${exchangeRateUri.prettyprint}") String prettyprint) throws URISyntaxException {
		
		return new ExchangeRateUri(latestUrl, historicalUrl, app_id, symbols, prettyprint);
	}
	
	@Bean
	public GifUri gifUri(@Value("${gifUri.url}") String url
			, @Value("${gifUri.api_key}") String api_key
			, @Value("${gifUri.qHigher}") String qHigher
			, @Value("${gifUri.qLower}") String qLower
			, @Value("${gifUri.limit}") String limit
			, @Value("${gifUri.rating}") String rating
			, @Value("${gifUri.lang}") String lang
			, @Value("${gifUri.offsetLimit}") int offsetLimit) {
		
		return new GifUri(url, api_key, qHigher, qLower, rating, lang, offsetLimit);
	}
}
