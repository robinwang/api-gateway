package edu.cmu.mis.iccfb.service;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import edu.cmu.mis.iccfb.model.Author;
import edu.cmu.mis.iccfb.model.Quote;

@Service
public class QuoteService {
	
	RestTemplate restTemplate = new RestTemplate();
	
	public Quote getRandomQuote() {
    	String randomUrl = "http://localhost:34128/random/quote";
        Quote randomQuote = restTemplate.getForObject(randomUrl, Quote.class);
        return randomQuote;
	}
	public List<Quote> getQuotes(Long authorId) {
    	String quoteUrl = "http://localhost:34128/quote";
    	ParameterizedTypeReference<List<Quote>> responseType = new ParameterizedTypeReference<List<Quote>>(){};
        ResponseEntity<List<Quote>> resp = restTemplate.exchange(quoteUrl+"?authorId="+authorId, HttpMethod.GET, null, responseType);
        return resp.getBody();
	}
	public void writeQuote(Quote quote, Author author) {
		HttpEntity<Quote> quoteRequest = new HttpEntity<>(
				new Quote(quote.getText(), 
						  quote.getSource(),
						  author.getId()));
    	String quoteUrl = "http://localhost:34128/quote";
    	ResponseEntity<Quote> resp = restTemplate.exchange(quoteUrl, HttpMethod.POST, quoteRequest, Quote.class);	    		    		
	}
}
