package edu.cmu.mis.iccfb.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import edu.cmu.mis.iccfb.model.Author;

@Service
public class AuthorService {
	  String authorUrl;
	  RestTemplate restTemplate = new RestTemplate();
      public Author getAuthorById(Long authorId) {
    	  String authorUrl = "http://localhost:34127/authorId";
          Author author = restTemplate.getForObject(authorUrl+"?authorId="+authorId, Author.class);
          return author;
      }
      
      public Author getAuthorByName(String authorName) {
    	  String authorUrl = "http://localhost:34127/authorName";
          Author author = restTemplate.getForObject(authorUrl+"?authorName="+authorName, Author.class);
          return author;
      }
      
      public Long writeAuthor(String authorName) {
    	  HttpEntity<Author> authorRequest = new HttpEntity<>(new Author(authorName, 0L));
          System.out.println(authorName);
  	      authorUrl = "http://localhost:34127/author";
  	      ResponseEntity<Long> resp = restTemplate.exchange(authorUrl, HttpMethod.POST, authorRequest, Long.class);
  	      Long newAuhorId = resp.getBody();
  	      return newAuhorId;
      }
}
