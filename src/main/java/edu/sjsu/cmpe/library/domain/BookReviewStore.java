package edu.sjsu.cmpe.library.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class BookReviewStore {
	
	public static HashMap <Integer, Review> reviewDetails = new HashMap <Integer, Review> ();
	public static HashMap <Integer, Object> bookReviewDetails = new HashMap <Integer, Object> ();
	public HashMap <Integer, Review> reviewDetailsTemp = new HashMap <Integer, Review> ();
	
	
	@SuppressWarnings("unchecked")
	public void addReviewToHashMap (int isbn, int review_id, Review review){
		
		Book book = new Book();
		BookStore bs = new BookStore();
		HashMap<Integer, Object> allReviewDetails = null;
		book = bs.searchBookDetailsHashmap(isbn);
		
		if (book != null){
			// Contains Global List of Reviews to be used for future use of Review API
			reviewDetails.put(review_id, review); 
			// Contains Current Review details
			reviewDetailsTemp.put(review_id, review); 
			
			if (bookReviewDetails.containsKey(isbn)) {
				// To obtain existing Review list
				allReviewDetails = (HashMap<Integer, Object>) bookReviewDetails.get(isbn); 
				allReviewDetails.put(review_id, review); 
				// Append existing + current list of reviews
				bookReviewDetails.put(isbn, allReviewDetails); 
			} else {
				// Add the first review to book
				bookReviewDetails.put(isbn, reviewDetailsTemp); 
			}
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ArrayList<Review> searchAllBookReviewDetailsHashmap(int isbn) {

		HashMap<Integer, Object> allReviewDetails = null;
		ArrayList<Review> allReviewsList = new ArrayList<Review>();
		Review review = new Review();
		
		if (bookReviewDetails.containsKey(isbn)) {
			
			allReviewDetails = (HashMap<Integer, Object>) bookReviewDetails.get(isbn);
						
			Iterator it = allReviewDetails.entrySet().iterator();

			while(it.hasNext()) {
				Map.Entry pairs = (Map.Entry)it.next();
				review = (Review)pairs.getValue();
				allReviewsList.add(review);
			}
		}
		return allReviewsList;
	}
	
	
	@SuppressWarnings({ "unchecked" })
	public Review searchBookReviewDetailsHashmap(int isbn, int id) {
		Review review = new Review();
		
		HashMap<Integer, Object> allReviewDetails = null;
		
		if (bookReviewDetails.containsKey(isbn)) {
			
			allReviewDetails = (HashMap<Integer, Object>) bookReviewDetails.get(isbn);
			
			if(allReviewDetails.containsKey(id)) {
				review = (Review)allReviewDetails.get(id);
			}
		}
		return review;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public int countBookReviewDetailsHashmap(int isbn) {
		
		HashMap<Integer, Object> allReviewDetails = null;
		ArrayList<Review> allReviewsList = new ArrayList<Review>();
		Review review = new Review();
		int count =0;
		
		if (bookReviewDetails.containsKey(isbn)) {
			
			allReviewDetails = (HashMap<Integer, Object>) bookReviewDetails.get(isbn);
						
			Iterator it = allReviewDetails.entrySet().iterator();
			while(it.hasNext()) {
				Map.Entry pairs = (Map.Entry)it.next();
				review = (Review)pairs.getValue();
				allReviewsList.add(review);
				count = count + 1;
			}
		}
		return count;
	}

}