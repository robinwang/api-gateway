package edu.cmu.mis.iccfb.model;

import java.util.List;

public class Author {
    
    private Long id = 0L;

    private String name;
    
    private List<Quote> quotes;
    
	protected Author() {}

    public Author(String name, long authorId) {
        this.id = authorId;
    	this.name = name;
    }

    @Override
    public String toString() {
        return String.format("Author[id=%d, name='%s']", id, this.name);
    }
    
    public Long getId () {
        return this.id;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
    
    public List<Quote> getQuotes() {
    	return quotes;
    }
    
    public void setQuotes(List<Quote> quotes) {
    	this.quotes = quotes;
    }
}
