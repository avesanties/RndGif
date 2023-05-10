package org.avesanties.rndgif.data;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

class RateDateTest {

	RateDate rateDate;
	
	CURRENCIES currency = CURRENCIES.RUB;
	
	BigDecimal rate = BigDecimal.valueOf(123.12345);
	
	LocalDate date = LocalDate.now().minusDays(1L);
	
	@BeforeEach
	void setUp() {
		rateDate = new RateDate(currency);
		rateDate.setDateAndRate(date, rate);
	}
	
	
	@ParameterizedTest
	@EnumSource(CURRENCIES.class)
	void testRateDate(CURRENCIES currency) {
		RateDate rateDateInContructor = new RateDate(currency);
		assertAll(
				() -> assertTrue(rateDateInContructor.isEmpty()),
				() -> assertEquals(currency, rateDateInContructor.getCode())
				);
	}
	
	@Test
	void testGetCode() {
		assertEquals(currency, rateDate.getCode());
	}

	@Test
	void testGetDate() {
		assertEquals(date, rateDate.getDate());
	}

	@Test
	void testGetRate() {
		assertEquals(rate, rateDate.getRate());
	}

	@Test
	void testSetDateAndRate() {
		RateDate rateDate = new RateDate(currency);
		BigDecimal rate = BigDecimal.valueOf(
				ThreadLocalRandom.current().nextDouble(1000.0));
		LocalDate date = LocalDate.now().minusDays(1L);
		rateDate.setDateAndRate(date, rate);
		
		assertAll(
				() -> assertEquals(rate, rateDate.getRate()),
				() -> assertEquals(date, rateDate.getDate())
				);
	}

	@Test
	void testIsEmptyFalse() {
		assertTrue(!rateDate.isEmpty());
	}
	
	@Test
	void testIsEmptyTrue() {
		assertTrue(new RateDate(currency).isEmpty());
	}

}
