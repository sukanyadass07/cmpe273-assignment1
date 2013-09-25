package edu.sjsu.cmpe.library.domain;

import edu.sjsu.cmpe.library.domain.Book;
import java.util.HashMap;

public class BookStore {
	
		
	public static HashMap <Integer, Book> bookDetails = new HashMap <Integer, Book> ();
	
		
	public void addBookDetailstoHashmap ( int isbnnum, Book book) {
		bookDetails.put(isbnnum, book);
	}
	
	public Book searchBookDetailsHashmap(int isbn) {
		Book bkdetails = new Book();
				
		if (bookDetails.containsKey(isbn)) {
			
			bkdetails = (Book) bookDetails.get(isbn);
		}
		return bkdetails;
	
	}
	
	
	public int countNumberOfBooksHashmap(int isbn) {
		
		int count =0;
				
		if (bookDetails.containsKey(isbn)) {
			
			@SuppressWarnings("unused")
			Book bkdetails = new Book();
			bkdetails = (Book) bookDetails.get(isbn);
			count= count +1;
		}
		return count;
	}
	
	public void deleteBookDetailsHashmap(int isbn) {
						
		if (bookDetails.containsKey(isbn)) {
			
			bookDetails.remove(isbn);
		}
	}
}