package org.avesanties.rndgif.data;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.avesanties.rndgif.AppExceptions.NotInitializedDateOrRateException;

public interface RateDateService {
	String getCode();
	
	LocalDate getDate() throws NotInitializedDateOrRateException;
	
	BigDecimal getRate() throws NotInitializedDateOrRateException;
	
	void updateRate(LocalDate newDate, BigDecimal newRate);
	
	boolean isTodayRateHigher(BigDecimal todayRate) throws NotInitializedDateOrRateException;
	
	boolean isEmptyOrOutdated();
}
