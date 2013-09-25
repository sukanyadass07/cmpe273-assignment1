package edu.sjsu.cmpe.library.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import edu.sjsu.cmpe.library.domain.Author;

@JsonPropertyOrder(alphabetic = true)
public class BookAuthorDto extends LinksDto {
    private Author author;

    /**
     * @param book
     */
    public BookAuthorDto(Author author) {
	super();
	this.author = author;
    }

    /**
     * @return the author
     */
    public Author getAuthor() {
	return author;
    }

    /**
     * @param author
     *            the author to set
     */
    public void setBook(Author author) {
	this.author = author;
    }
}
