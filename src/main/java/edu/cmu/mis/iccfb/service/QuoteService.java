package edu.cmu.mis.iccfb.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
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
	//@Value("${QUOTE_SERVICE_URL}")
	//private String quoteUrl;
	private String quoteUrl = "http://quote-service/";
	private String reqUrl;
	@Autowired
    @LoadBalanced
	RestTemplate restTemplate;
	
	public Quote getRandomQuote() {
		reqUrl = quoteUrl+"random/quote";
        Quote randomQuote = restTemplate.getForObject(reqUrl, Quote.class);
        return randomQuote;
	}
	public List<Quote> getQuotes(Long authorId) {
		reqUrl = quoteUrl+"quote";
    	ParameterizedTypeReference<List<Quote>> responseType = new ParameterizedTypeReference<List<Quote>>(){};
        ResponseEntity<List<Quote>> resp = restTemplate.exchange(reqUrl+"?authorId="+authorId, HttpMethod.GET, null, responseType);
        return resp.getBody();
	}
	public void writeQuote(Quote quote, Author author) {
		HttpEntity<Quote> quoteRequest = new HttpEntity<>(
				new Quote(quote.getText(), 
						  quote.getSource(),
						  author.getId()));
		reqUrl = quoteUrl+"quote";
    	ResponseEntity<Quote> resp = restTemplate.exchange(reqUrl, HttpMethod.POST, quoteRequest, Quote.class);	    		    		
	}
}
