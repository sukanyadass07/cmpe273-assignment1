package edu.sjsu.cmpe.library.dto;

import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import edu.sjsu.cmpe.library.domain.Review;

@JsonPropertyOrder(alphabetic = true)
public class AllBookReviewsDto extends LinksDto {

	private ArrayList<Review> reviews;

    public ArrayList<Review> getReviews() {
		return reviews;
	}

	public void setReviews(ArrayList<Review> reviews) {
		this.reviews = reviews;
	}

    public AllBookReviewsDto(ArrayList<Review> reviews) {
	super();
	this.reviews = reviews;
    }

}
