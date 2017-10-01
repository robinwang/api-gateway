package edu.cmu.mis.iccfb.controller;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import edu.cmu.mis.iccfb.model.Author;
//import edu.cmu.mis.iccfb.model.Author;
import edu.cmu.mis.iccfb.model.Quote;

@RestController
public class QuoteController {

    @RequestMapping("/api/quote/random")
    public Quote random() {
    	RestTemplate restTemplate = new RestTemplate();
    	String randomUrl = "http://localhost:34128/random/quote";
        Quote randomQuote = restTemplate.getForObject(randomUrl, Quote.class);
        String authorUrl = "http://localhost:34127/authorId";
        String authorName = restTemplate.getForObject(authorUrl+"?authorId="+randomQuote.getAuthorId(), String.class);
        randomQuote.setAuthorName(authorName);
        return randomQuote;
    }
    
    @RequestMapping(value = "/api/author/{authorId}")
    public Author inquiry(@PathVariable("authorId") long authorId) {
    	RestTemplate restTemplate = new RestTemplate();
    	String quoteUrl = "http://localhost:34128/quote";
    	ParameterizedTypeReference<List<Quote>> responseType = new ParameterizedTypeReference<List<Quote>>(){};
        ResponseEntity<List<Quote>> quotes = restTemplate.exchange(quoteUrl+"?authorId="+authorId, HttpMethod.GET, null, responseType);
        String authorUrl = "http://localhost:34127/authorId";
        String authorName = restTemplate.getForObject(authorUrl+"?authorId="+authorId, String.class);
        Author a = new Author(authorName, authorId);
        System.out.println(authorName);
        System.out.println(quotes.getStatusCodeValue());
        //System.out.println(quotes.getHeaders().get);
        a.setQuotes(quotes.getBody());
        return a;
    }
    
    @RequestMapping(value = "/api/quote", method = RequestMethod.POST)
    public void saveQuote(@RequestBody Quote quote) {
    	System.out.println(quote.toString());
    	RestTemplate restTemplate = new RestTemplate();
        String authorUrl = "http://localhost:34127/authorName";
        //long authorId = restTemplate.getForObject(authorUrl+"?authorName="+quote.getAuthorName(), long.class);
        ResponseEntity<Long> response = restTemplate.exchange(authorUrl+"?authorName="+quote.getAuthorName(), HttpMethod.GET, null, Long.class);
    	int statusCode = response.getStatusCodeValue();
    	if (statusCode == 200) {
    		long authorId = response.getBody();
    		HttpEntity<Quote> quoteRequest = new HttpEntity<>(
    				new Quote(quote.getText(), 
    						  quote.getSource(),
    						  authorId));
        	String quoteUrl = "http://localhost:34128/quote";
        	ResponseEntity<Quote> qresponse = restTemplate.exchange(quoteUrl, HttpMethod.POST, quoteRequest, Quote.class);	    		    		
    	} else {
            HttpEntity<Author> authorRequest = new HttpEntity<>(new Author(quote.getAuthorName(), Integer.valueOf(quote.getAuthorName())));
    	    authorUrl = "http://localhost:34127/author";
    	    ResponseEntity<Author> resp = restTemplate.exchange(authorUrl, HttpMethod.POST, authorRequest, Author.class);
    	    statusCode = response.getStatusCodeValue();
    	    if (statusCode == 200) {
    		    HttpEntity<Quote> quoteRequest = new HttpEntity<>(
    				new Quote(quote.getText(), 
    						  quote.getSource(),
    						  quote.getAuthorId()));
        	String quoteUrl = "http://localhost:34128/quote";
        	ResponseEntity<Quote> qresponse = restTemplate.exchange(quoteUrl, HttpMethod.POST, quoteRequest, Quote.class);	    		
    	}
    	}

        System.out.println("Saving quote");
    }

}