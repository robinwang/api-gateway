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
        Author author = restTemplate.getForObject(authorUrl+"?authorId="+randomQuote.getAuthorId(), Author.class);
        randomQuote.setAuthor(author);
        return randomQuote;
    }
    
    @RequestMapping(value = "/api/author/{authorId}")
    public Author inquiry(@PathVariable("authorId") long authorId) {
    	RestTemplate restTemplate = new RestTemplate();
    	String quoteUrl = "http://localhost:34128/quote";
    	ParameterizedTypeReference<List<Quote>> responseType = new ParameterizedTypeReference<List<Quote>>(){};
        ResponseEntity<List<Quote>> quotes = restTemplate.exchange(quoteUrl+"?authorId="+authorId, HttpMethod.GET, null, responseType);
        String authorUrl = "http://localhost:34127/authorId";
        Author author = restTemplate.getForObject(authorUrl+"?authorId="+authorId, Author.class);
        author.setQuotes(quotes.getBody());
        return author;
    }
    
    @RequestMapping(value = "/api/quote", method = RequestMethod.POST)
    public void saveQuote(@RequestBody Quote quote) {
    	//System.out.println(quote.getAuthorName());
    	//quote.setAuthorName(quote.getAuthor().getName());
    	RestTemplate restTemplate = new RestTemplate();
        String authorUrl = "http://localhost:34127/authorName";
        //long authorId = restTemplate.getForObject(authorUrl+"?authorName="+quote.getAuthorName(), long.class);
        ResponseEntity<Author> response = restTemplate.exchange(authorUrl+"?authorName="+quote.getAuthor().getName(), HttpMethod.GET, null, Author.class);
//    	int statusCode = response.getStatusCodeValue();
//    	System.out.println("#########"+statusCode);
        Author a = response.getBody();
    	if (a != null) {
    		//long authorId = response.getBody();
    		HttpEntity<Quote> quoteRequest = new HttpEntity<>(
    				new Quote(quote.getText(), 
    						  quote.getSource(),
    						  a.getId()));
        	String quoteUrl = "http://localhost:34128/quote";
        	ResponseEntity<Quote> qresponse = restTemplate.exchange(quoteUrl, HttpMethod.POST, quoteRequest, Quote.class);	    		    		
    	} else {
            HttpEntity<Author> authorRequest = new HttpEntity<>(new Author(quote.getAuthor().getName(), Long.valueOf(quote.getAuthor().getName())));
    	    authorUrl = "http://localhost:34127/author";
    	    ResponseEntity<Author> resp = restTemplate.exchange(authorUrl, HttpMethod.POST, authorRequest, Author.class);
    	    int statusCode = response.getStatusCodeValue();
    	    if (statusCode == 200) {
    		    HttpEntity<Quote> quoteRequest = new HttpEntity<>(
    				new Quote(quote.getText(), 
    						  quote.getSource(),
    						  Long.valueOf(quote.getAuthor().getName())));
        	String quoteUrl = "http://localhost:34128/quote";
        	ResponseEntity<Quote> qresponse = restTemplate.exchange(quoteUrl, HttpMethod.POST, quoteRequest, Quote.class);	    		
    	}
    	}
    }

}