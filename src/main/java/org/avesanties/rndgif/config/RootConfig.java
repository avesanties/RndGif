package org.avesanties.rndgif.config;

import org.avesanties.rndgif.data.CURRENCIES;
import org.avesanties.rndgif.data.RateDate;
import org.avesanties.rndgif.data.RateDateService;
import org.avesanties.rndgif.data.RateDateServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

@Configuration
@PropertySource("classpath:root.properties")
public class RootConfig {
	
	@Value("${rateDate.code}")
	private String codeName; 
	
	@Bean
	public RateDate rateDate() {
		CURRENCIES code = CURRENCIES.valueOf(
				codeName.toUpperCase());
		return new RateDate(code);
	}
	
	@Bean
	public RateDateService rateDataService() {
		return new RateDateServiceImpl(rateDate());
	}
	
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}
	
	@Bean
	public Validator validator() {
		ValidatorFactory validatorFactory = 
				Validation.buildDefaultValidatorFactory();
		
		return validatorFactory.getValidator();
	}
}
