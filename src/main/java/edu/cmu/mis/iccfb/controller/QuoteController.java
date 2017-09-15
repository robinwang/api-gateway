package edu.cmu.mis.iccfb.controller;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.cmu.mis.iccfb.model.Author;
import edu.cmu.mis.iccfb.model.Quote;
import edu.cmu.mis.iccfb.service.AuthorService;
import edu.cmu.mis.iccfb.service.QuoteService;

@RestController
public class QuoteController {
    
    @Autowired
    private QuoteService quoteService;
    
    @Autowired
    private AuthorService authorService;
    
    @RequestMapping("/api/quote/random")
    public Quote random() {
        return quoteService.randomQuote();
    }
    
    @RequestMapping(value = "/api/author/{authorId}")
    public Author inquiry(@PathVariable("authorId") long authorId) {
        Author a = authorService.findOne(authorId);
        return a;
    }
    
    @RequestMapping(value = "/api/quote", method = RequestMethod.POST)
    public void saveQuote(@RequestBody Quote quote) {
        System.out.println(quote);
        
        Author a = authorService.findByName(quote.getAuthor().getName());
        
        if (a == null) {
            System.out.println("Saving author");
            authorService.save(quote.getAuthor());
        }
        else {
        	quote.setAuthor(a);
        }

        
        System.out.println("Saving quote");
        quoteService.save(quote);
    }

}