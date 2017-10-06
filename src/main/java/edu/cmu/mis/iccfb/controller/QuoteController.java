package edu.cmu.mis.iccfb.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import edu.cmu.mis.iccfb.model.Author;
import edu.cmu.mis.iccfb.model.Quote;
import edu.cmu.mis.iccfb.service.AuthorService;
import edu.cmu.mis.iccfb.service.QuoteService;

@RestController
public class QuoteController {

	@Autowired
	AuthorService authorservice;
	
	@Autowired
	QuoteService quoteservice;
	
    @RequestMapping("/api/quote/random")
    public Quote random() {
    	Quote randomQuote = quoteservice.getRandomQuote();
    	Author author = authorservice.getAuthorById(randomQuote.getAuthorId());
    	randomQuote.setAuthor(author);
        return randomQuote;
    }
    
    @RequestMapping(value = "/api/author/{authorId}")
    public Author inquiry(@PathVariable("authorId") long authorId) {
    	List<Quote> quotes = quoteservice.getQuotes(authorId);
        Author author = authorservice.getAuthorById(authorId);
        author.setQuotes(quotes);
        return author;
    }
    
    @RequestMapping(value = "/api/quote", method = RequestMethod.POST)
    public void saveQuote(@RequestBody Quote quote) {
    	String authorName = quote.getAuthor().getName();
    	Author a = authorservice.getAuthorByName(authorName);
    	if (a != null) {
    		quoteservice.writeQuote(quote, a);	    		    		
    	} else {
    	    Long newAuthorId = authorservice.writeAuthor(authorName);
    	    a = new Author(authorName, newAuthorId);
    	    quoteservice.writeQuote(quote, a);
    	}
    }

}