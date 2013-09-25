package edu.sjsu.cmpe.library.dto;

import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import edu.sjsu.cmpe.library.domain.Author;

@JsonPropertyOrder(alphabetic = true)
public class AllBookAuthorsDto extends LinksDto {

	private ArrayList<Author> authors;

    public ArrayList<Author> getAuthors() {
		return authors;
	}

	public void setAuthors(ArrayList<Author> authors) {
		this.authors = authors;
	}

    public AllBookAuthorsDto(ArrayList<Author> authors) {
	super();
	this.authors = authors;
    }

}
