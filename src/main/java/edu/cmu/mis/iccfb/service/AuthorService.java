package edu.cmu.mis.iccfb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import edu.cmu.mis.iccfb.model.Author;

@Service
public class AuthorService {
	  //@Value("${AUTHOR_SERVICE_URL}")
	  //private String authorUrl;
	  private String authorUrl = "http://author-service/";
	  private String reqUrl;
	  @Autowired
	  @LoadBalanced
	  RestTemplate restTemplate;
	  
      public Author getAuthorById(Long authorId) {
    	  reqUrl = authorUrl+"authorId";
          Author author = restTemplate.getForObject(reqUrl+"?authorId="+authorId, Author.class);
          return author;
      }
      
      public Author getAuthorByName(String authorName) {
    	  reqUrl = authorUrl+"authorName";
          Author author = restTemplate.getForObject(reqUrl+"?authorName="+authorName, Author.class);
          return author;
      }
      
      public Long writeAuthor(String authorName) {
    	  HttpEntity<Author> authorRequest = new HttpEntity<>(new Author(authorName, 0L));
          System.out.println(authorName);
          reqUrl = authorUrl+"author";
  	      ResponseEntity<Long> resp = restTemplate.exchange(reqUrl, HttpMethod.POST, authorRequest, Long.class);
  	      Long newAuhorId = resp.getBody();
  	      return newAuhorId;
      }
}
