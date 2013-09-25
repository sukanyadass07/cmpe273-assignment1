package edu.sjsu.cmpe.library.domain;

import java.util.ArrayList;
import java.util.HashMap;

public class BookAuthorStore {
	
	public static HashMap <Integer, ArrayList<Author>> authorDetails = new HashMap <Integer, ArrayList<Author>> ();
	
	
	public void addAuthorToHashMap (int isbn, ArrayList<Author> authorList) {
		authorDetails.put(isbn, authorList);
	}


	public ArrayList<Author> searchAllBookAuthorDetailsHashmap(int isbn) {

		ArrayList<Author> allAuthorsList = new ArrayList<Author>();
		
		if (authorDetails.containsKey(isbn)) {
			allAuthorsList = authorDetails.get(isbn);
		}
		return allAuthorsList;
	}

}