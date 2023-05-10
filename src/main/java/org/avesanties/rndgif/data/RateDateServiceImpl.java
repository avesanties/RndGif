package org.avesanties.rndgif.data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;

import org.avesanties.rndgif.AppExceptions.NotInitializedDateOrRateException;

public class RateDateServiceImpl implements RateDateService {
	
	private RateDate rateDateStore;
	
	public RateDateServiceImpl(RateDate rateDate) {
		rateDateStore = rateDate;
	}

	@Override
	public String getCode() {
		return rateDateStore.getCode().toString();
	}
	
	@Override
	public LocalDate getDate() throws NotInitializedDateOrRateException{
		if(rateDateStore.isEmpty())
			throw new NotInitializedDateOrRateException("Date or rate have not been updated yet");
		
		return rateDateStore.getDate();
	}

	@Override
	public BigDecimal getRate() throws NotInitializedDateOrRateException{
		if(rateDateStore.isEmpty())
			throw new NotInitializedDateOrRateException("Date or rate have not been updated yet");
		
		return rateDateStore.getRate();
	}

	@Override
	public void updateRate(LocalDate newDate, BigDecimal newRate) {
		Objects.requireNonNull(newDate);
		Objects.requireNonNull(newRate);
				
		Period period = Period.between(rateDateStore.getDate(), newDate);
		
		if(rateDateStore.isEmpty() 
				|| (period.getDays() != 1
						|| period.getMonths() != 0
						|| period.getYears() != 0)) {
			rateDateStore.setDateAndRate(newDate, newRate);
		}
	}

	@Override
	public boolean isTodayRateHigher(BigDecimal todayRate) throws NotInitializedDateOrRateException{
		if(rateDateStore.isEmpty())
			throw new NotInitializedDateOrRateException("Date or rate have not been updated yet");
		
		Objects.requireNonNull(todayRate);
		
		return todayRate.compareTo(rateDateStore.getRate()) > 0;
	}

	@Override
	public boolean isEmptyOrOutdated() {
		if(rateDateStore.isEmpty())
			return true;
		
		Period period = Period.between(rateDateStore.getDate(), LocalDate.now());
		
		return period.getDays() != 1
				|| period.getMonths() != 0
				|| period.getYears() != 0;
	}
}
