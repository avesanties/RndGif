package org.avesanties.rndgif.data;

import java.math.BigDecimal;
import java.time.LocalDate;

public final class RateDate {
	
	private final CURRENCIES code;
	
	private LocalDate date;
	
	private BigDecimal rate;
	
	private boolean isEmpty;
	
	public RateDate(CURRENCIES code) {
		this.code = code;
		this.date = LocalDate.now();
		this.rate = BigDecimal.ZERO;
		
		isEmpty = true;
	}
	
	public CURRENCIES getCode() {
		return code;
	}
	
	public LocalDate getDate() {
		return date;
	}

	public BigDecimal getRate() {
		return rate;
	}
	
	public void setDateAndRate(LocalDate date,
								BigDecimal rate) {
		this.date = date;
		this.rate = rate;
		isEmpty = false;
	}

	public boolean isEmpty() {
		return isEmpty;
	}

	@Override
	public String toString() {
		return "RateDate [code=" + code + ", date=" + date + ", rate=" + rate + "]";
	}
	
	
}
