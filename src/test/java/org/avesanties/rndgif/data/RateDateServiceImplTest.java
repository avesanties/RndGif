package org.avesanties.rndgif.data;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Random;

import org.avesanties.rndgif.AppExceptions.NotInitializedDateOrRateException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RateDateServiceImplTest {
	
	private RateDate rateDate;
	
	private static CURRENCIES currency = CURRENCIES.RUB;
	
	private static BigDecimal rate = BigDecimal.valueOf(123.12345);
	
	private static LocalDate date = LocalDate.now().minusDays(1L);
	
	private RateDateService rateDateServiceImpl;
	
	private static RateDate notInitializedRateDate = new RateDate(currency);
	
	private static RateDate oldRateDate;
	
	@BeforeEach
	void setUp() {
		rateDate = new RateDate(currency);
		rateDate.setDateAndRate(date, rate);
		
		rateDateServiceImpl = new RateDateServiceImpl(rateDate);
		
		oldRateDate = new RateDate(currency);
		LocalDate oldDate = LocalDate.now().minusDays(2L);
		BigDecimal oldRate = BigDecimal.valueOf(new Random().nextDouble(1000.0));
		oldRateDate.setDateAndRate(oldDate, oldRate);
	}

	@Test
	void testGetCode() {
		assertEquals(currency.toString(), rateDateServiceImpl.getCode());
	}

	@Test
	void testGetDate() throws NotInitializedDateOrRateException {
		assertEquals(date, rateDateServiceImpl.getDate());
		
		assertThrows(NotInitializedDateOrRateException.class, 
				() -> new RateDateServiceImpl(notInitializedRateDate).getDate());
	}

	@Test
	void testGetRate() throws NotInitializedDateOrRateException {
		assertEquals(rate, rateDateServiceImpl.getRate());
		
		assertThrows(NotInitializedDateOrRateException.class, 
				() -> new RateDateServiceImpl(notInitializedRateDate).getRate());
	}

	@Test
	void testUpdateRate() throws NotInitializedDateOrRateException {
		BigDecimal twoDaysBackRate = BigDecimal.valueOf(new Random().nextDouble(1000.0));
		LocalDate twoDaysBackDate = LocalDate.now().minusDays(2L);
		
		RateDate twoDaysBackRateDate = new RateDate(currency);
		twoDaysBackRateDate.setDateAndRate(twoDaysBackDate, twoDaysBackRate);
		
		assertThrows(NullPointerException.class, 
				() -> rateDateServiceImpl.updateRate(twoDaysBackDate, null));
		assertThrows(NullPointerException.class, 
				() -> rateDateServiceImpl.updateRate(null, twoDaysBackRate));
		
		rateDateServiceImpl.updateRate(date, rate);
		
		assertEquals(date, rateDateServiceImpl.getDate());
		assertEquals(rate, rateDateServiceImpl.getRate());
	}

	@Test
	void testIsTodayRateHigher() throws NotInitializedDateOrRateException {
		BigDecimal todayRate = BigDecimal.valueOf(new Random().nextDouble(1000.0));
		
		assertEquals(todayRate.compareTo(rateDateServiceImpl.getRate()) > 0, 
				rateDateServiceImpl.isTodayRateHigher(todayRate));
		
		assertThrows(NotInitializedDateOrRateException.class, 
				() -> new RateDateServiceImpl(notInitializedRateDate).isTodayRateHigher(todayRate));
	}

	@Test
	void testIsEmptyOrOutdated() {
		assertFalse(rateDateServiceImpl.isEmptyOrOutdated());
		assertTrue(new RateDateServiceImpl(notInitializedRateDate).isEmptyOrOutdated());
		assertTrue(new RateDateServiceImpl(oldRateDate).isEmptyOrOutdated());
	}

}
