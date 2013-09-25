package edu.sjsu.cmpe.library.domain;

import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonProperty;


public class Book {
	
	
	private int isbn;
    private String title;
	@JsonProperty("publication-date")
    private String publicationDate;
    private String language;
    @JsonProperty("num-pages")
    private int numpages;
    private String status;
    private ArrayList <Review> review;
    private ArrayList <Author> authors;
    
	public ArrayList<Author> getAuthors() {
		return authors;
	}

	public void setAuthors(ArrayList<Author> authors) {
		this.authors = authors;
	}
	
	public ArrayList<Review> getReview() {
		return review;
	}

	public void setReview(ArrayList<Review> review) {
		this.review = review;
	}

	public long getIsbn() {
		return isbn;
	}
	
	public void setIsbn(int isbn) {
		this.isbn = isbn;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getPublicationDate() {
		return publicationDate;
	}
	
	public void setPublicationDate(String publicationDate) {
		this.publicationDate = publicationDate;
	}
	
	public String getLanguage() {
		return language;
	}
	
	public void setLanguage(String language) {
		this.language = language;
	}
	
	public int getNumpages() {
		return numpages;
	}
	
	public void setNumpages(int numpages) {
		this.numpages = numpages;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}

}

