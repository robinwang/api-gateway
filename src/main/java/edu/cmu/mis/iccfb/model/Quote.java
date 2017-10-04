package edu.cmu.mis.iccfb.model;


public class Quote {
    private Long id;

    private String text;

    private String source;

    private String authorName;

    private Long authorId;
    
    private Author a;
    

    public Quote() {}

    public Quote(String text, String source, Long authorId) {
        this.text = text;
        this.source = source;
        this.authorId = authorId;
    }
    
    @Override
    public String toString() {
    	return String.format("Quote[id=%d,text='%s',by='%s',id=,source='%s']",this.id,this.text,this.authorName, this.source);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public Long getId() {
        return id;
    }
    
    public String getAuthorName() {
    	return this.authorName;
    }
    
    public void setAuthorName(String authorName) {
    	this.authorName = authorName;
    }
    
    public Author getAuthor() {
    	return this.a;
    }
    
    public void setAuthor(Author a) {
    	this.a = a;
    }
}