package org.avesanties.rndgif.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;

import org.avesanties.rndgif.data.RateDateService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;

class RateDateControllerTest {
	
	private static RestTemplate restTemplate = new RestTemplate();
	
	private static ExchangeRateUri exchangeRateUri = mock(ExchangeRateUri.class);
	
	private static GifUri gifUri = mock(GifUri.class);
	
	private RateDateService rateDateService = mock(RateDateService.class);

	MockMvc mockMvc;
	

    @BeforeEach
    void setup() throws URISyntaxException {
    	
    	when(exchangeRateUri.yesterdayUri())
    	// here must be exchange api uri for YESTERDAY specified
    	.thenReturn(new URI(""));
    	when(exchangeRateUri.nowUri())
    	// here must be exchange api uri for LATEST specified
    	.thenReturn(new URI(""));
    	
    	when(gifUri.exchangeRateHiger())
    	// here must be gif api uri when today exchange rate is HIGHER than yesterday one
    	.thenReturn(new URI(""));
    	when(gifUri.exchangeRateLower())
    	// here must be gif api uri when today exchange rate is LOWER than yesterday one
    	.thenReturn(new URI(""));
        
    	when(rateDateService.getCode()).thenReturn("RUB");
    	
    	this.mockMvc = MockMvcBuilders.standaloneSetup(
        		new RateDateController(
        				restTemplate,
        				exchangeRateUri,
        				gifUri,
        				rateDateService))
    			.defaultRequest(get("/").accept(MediaType.APPLICATION_JSON))
    			.build();
    }
    
    @Test
    void testGetStatus() throws Exception {
    	when(rateDateService.isEmptyOrOutdated()).thenReturn(true);
    	mockMvc.perform(get("/")).andExpect(status().isOk());
    	
    	when(rateDateService.isEmptyOrOutdated()).thenReturn(false);
    	when(rateDateService.isTodayRateHigher(any(BigDecimal.class))).thenReturn(false);
    	mockMvc.perform(get("/")).andExpect(status().isOk());
    	
    	when(rateDateService.isTodayRateHigher(any(BigDecimal.class))).thenReturn(true);
    	mockMvc.perform(get("/")).andExpect(status().isOk());
    }

}
