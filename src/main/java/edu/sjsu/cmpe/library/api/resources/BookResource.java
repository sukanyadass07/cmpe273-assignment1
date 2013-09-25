package edu.sjsu.cmpe.library.api.resources;

import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.yammer.metrics.annotation.Timed;

import edu.sjsu.cmpe.library.domain.Author;
import edu.sjsu.cmpe.library.domain.Book;
import edu.sjsu.cmpe.library.domain.BookAuthorStore;
import edu.sjsu.cmpe.library.domain.BookReviewStore;
import edu.sjsu.cmpe.library.domain.BookStore;
import edu.sjsu.cmpe.library.domain.Review;
import edu.sjsu.cmpe.library.dto.AllBookAuthorsDto;
import edu.sjsu.cmpe.library.dto.AllBookReviewsDto;
import edu.sjsu.cmpe.library.dto.BookAuthorDto;
import edu.sjsu.cmpe.library.dto.BookDto;
import edu.sjsu.cmpe.library.dto.BookReviewDto;
import edu.sjsu.cmpe.library.dto.ErrorMessage;
import edu.sjsu.cmpe.library.dto.LinkDto;
import edu.sjsu.cmpe.library.dto.LinksDto;


@Path("/v1/books")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)

public class BookResource {

	static int isbnnum = 0;
	static int review_id = 0;
	static int globalAuthorId = 0;
    public BookResource() {
    }

    @Timed(name = "create-book")
    @POST 
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    
    public Response createbook(Book book) {
    	
    	
    	ArrayList<Author> authors = new ArrayList<Author>();
    	ErrorMessage em = new ErrorMessage();

    	if (book.getTitle() != null){
    		
    		if(book.getPublicationDate() != null){
    			
    			if(book.getLanguage() != null){
    				
    				if(book.getNumpages() != 0 ){
    					   					
    					if((book.getStatus().equalsIgnoreCase("available")) || 
    							(book.getStatus().equalsIgnoreCase("lost")) || 
    							(book.getStatus().equalsIgnoreCase("check-out")) ||
    							(book.getStatus().equalsIgnoreCase("hold"))) {
    					
    						isbnnum = isbnnum + 1;
    				    	book.setIsbn(isbnnum);

    						for (int i = 0; i < book.getAuthors().size(); i++ ) {
        			    		Author author = new Author();
        			    		globalAuthorId = globalAuthorId + 1;
        			    		author.setAuthorId(globalAuthorId);
        			    		author.setName(book.getAuthors().get(i).getName());
        			    		authors.add(author);
    						}
        			    		
        			    		/*if(book.getAuthors().get(i).getName().){
        			    			author.setName(book.getAuthors().get(i).getName());
            			    		authors.add(author);
        			    		}
        			    		else{
        			    			em.setStatusCode(400);
        	    					em.setMessage("Please enter Author of the Book in the request");
        	    					return Response.ok(em).build();
        			    		}*/
        			    		book.setAuthors(authors);
            			    	
            			    	BookAuthorStore bookAuthorStore = new BookAuthorStore();
            			    	bookAuthorStore.addAuthorToHashMap(isbnnum,authors);
            			    	
            			    	BookStore bs = new BookStore();
            			    	bs.addBookDetailstoHashmap (isbnnum, book);
            			    	
            			    	LinksDto links = new LinksDto();
            			    	links.addLink(new LinkDto("view-book", "/books/" + book.getIsbn(), "GET"));
            			    	links.addLink(new LinkDto("update-book", "/books/" + book.getIsbn() , "PUT"));
            			    	links.addLink(new LinkDto("delete-book", "/books/" + book.getIsbn() , "DELETE"));
            			    	links.addLink(new LinkDto("create-review", "/books/" + book.getIsbn() , "POST"));

            			    	return Response.status(201).entity(links).build();
    						
    					}
    					else {
    						em.setStatusCode(400);
        					em.setMessage("Acceptable Values for Status are AVAILABLE, LOST, HOLD or CHECK-OUT");
        					return Response.ok(em).build();
    					}
	
    				}
    				else{
    					em.setStatusCode(400);
    					em.setMessage("Please enter Number of Pages for the Book in the request");
    					return Response.ok(em).build();
    				}
    			}
    			else{
    				em.setStatusCode(400);
					em.setMessage("Please enter Language of the book in the request");
					return Response.ok(em).build();
    			}
    		}
    		else{
    			em.setStatusCode(400);
				em.setMessage("Please enter Publication Date of the book in the request");
				return Response.ok(em).build();
    		}
    	}
    	else{
    		em.setStatusCode(400);
			em.setMessage("Please enter Title of the book in the request");
			return Response.ok(em).build();
    	}
    }
    

    @GET
    @Path("/{isbn}")
    @Timed(name = "view-book")
    public Response getBookByIsbn(@PathParam("isbn") int isbn) {
        	
	    
    	Book book = new Book();
    	Review review = new Review();
	    BookStore bs = new BookStore();
	    BookReviewStore brs = new BookReviewStore();
	    ArrayList<Review> allReviewsList = new ArrayList<Review>();
	    ArrayList<Review> allReviewsListtemp = new ArrayList<Review>();
	    ErrorMessage em = new ErrorMessage();
	    int count = 0;
	    
	    if (isbn !=0){    	
	    	count = bs.countNumberOfBooksHashmap(isbn);  	
	    	allReviewsList = brs.searchAllBookReviewDetailsHashmap(isbn);
	    	
	    	if (count !=0 || (!allReviewsList.isEmpty()) ) {
	    		
	    		book = bs.searchBookDetailsHashmap(isbn);
	    		for(int i=0; i < allReviewsList.size() ; i++) {
	    			review = allReviewsList.get(i);
	    			allReviewsListtemp.add(review);
	    			book.setReview(allReviewsListtemp);
	    		}
	    		
				BookDto bookResponse = new BookDto(book);
				
				for (int i = 0; i < book.getAuthors().size(); i ++) {
			    	
			    	bookResponse.addLink(new LinkDto("view-author", "/books/"+ isbn + "/authors/" + book.getAuthors().get(i).getAuthorId(), "POST"));
			    	
			    }
				bookResponse.addLink(new LinkDto("view-book", "/books/" + book.getIsbn(), "GET"));
				bookResponse.addLink(new LinkDto("update-book", "/books/" + book.getIsbn(), "PUT"));
				bookResponse.addLink(new LinkDto("delete-book", "/books/" + book.getIsbn(), "DELETE"));
				bookResponse.addLink(new LinkDto("create-review", "/books/" + book.getIsbn() + "/reviews", "POST"));
				count = brs.countBookReviewDetailsHashmap(isbn);
				if(count > 0) {
					bookResponse.addLink(new LinkDto("view-all-reviews", "/books/" + book.getIsbn() + "/reviews", "GET"));
				}
				
				return Response.ok(bookResponse).build();
	    	}
	    	else{
	    		em.setStatusCode(400);
				em.setMessage("The book with the given isbn do not exist");
				return Response.ok(em).build();
	    	}
	    }
	    else{
	    	em.setStatusCode(400);
			em.setMessage("Please enter valid Isbn of book to view ");
			return Response.ok(em).build();
	    	
	    }
    }

    @Timed(name = "view-all-authors")
    @GET 
    @Path("/{isbn}/authors")
        
    public AllBookAuthorsDto getAllBookAuthorsByIsbn(@PathParam("isbn") int isbn) {
    	
    	ArrayList<Author> allAuthorsList = new ArrayList<Author>();
    	BookAuthorStore bookAuthorStore = new BookAuthorStore();
    	allAuthorsList = bookAuthorStore.searchAllBookAuthorDetailsHashmap(isbn);
	    
    	
    	AllBookAuthorsDto bookAuthorResponse = new AllBookAuthorsDto(allAuthorsList);
    	bookAuthorResponse.addLink(new LinkDto("view-authors", "/books/" + isbn + "/authors" , "GET"));
    		
    	return bookAuthorResponse;
    }
    
    
    @Timed(name = "view-author")
    @GET 
    @Path("/{isbn}/authors/{id}")
        
    public BookAuthorDto getBookAuthorsByIsbn(@PathParam("isbn") int isbn, @PathParam("id") int id) {
    	
    	Author author = new Author();
    	ArrayList<Author> allAuthorsList = new ArrayList<Author>();
    	BookAuthorStore bookAuthorStore = new BookAuthorStore();
    	allAuthorsList = bookAuthorStore.searchAllBookAuthorDetailsHashmap(isbn);
    	for (int i =0; i < allAuthorsList.size(); i ++ ){
    	
    		if(allAuthorsList.get(i).getAuthorId() == id){
    			author = allAuthorsList.get(i);
    			break;
    			
    		}
    	}
	        	
    	BookAuthorDto bookAuthorResponse = new BookAuthorDto(author);
    	bookAuthorResponse.addLink(new LinkDto("view-authors", "/books/" + isbn + "/authors/" + id , "GET"));
    		
    	return bookAuthorResponse;
    }
    
    @Timed(name = "update-book")
    @PUT
    @Path("/{isbn}")
	
    public Response updateBookStatus(@PathParam("isbn") int isbn, @QueryParam("status") String newstatus)
    {
    	Book book = new Book();
    	BookStore bs = new BookStore();
    	ErrorMessage em = new ErrorMessage();
    	BookReviewStore brs = new BookReviewStore();
    	int count =0;
	    
    	if((newstatus.equalsIgnoreCase("available")) || (newstatus.equalsIgnoreCase("hold")) || ((newstatus.equalsIgnoreCase("check-out")) ||
    		(newstatus.equalsIgnoreCase("lost")))){
    		
    		book = bs.searchBookDetailsHashmap(isbn);
    		if (book != null){
    			
    			book.setStatus(newstatus);
        	    BookDto bookResponse = new BookDto(book);
        		bookResponse.addLink(new LinkDto("view-book", "/books/" + book.getIsbn(), "GET"));
        		bookResponse.addLink(new LinkDto("update-book", "/books/" + book.getIsbn(), "PUT"));
        		bookResponse.addLink(new LinkDto("delete-book", "/books/" + book.getIsbn(), "DELETE"));
        		bookResponse.addLink(new LinkDto("create-review", "/books/" + book.getIsbn(), "POST"));
        		count = brs.countBookReviewDetailsHashmap(isbn);
				if(count > 0){
					bookResponse.addLink(new LinkDto("view-all-reviews", "/books/" + book.getIsbn() + "/reviews", "GET"));
				}
				
        	
        		return Response.ok(bookResponse).build();
    		}
    		else{
    			em.setStatusCode(400);
        		em.setMessage("Please enter Isbn of existing book)");
        					
        		return Response.ok(em).build();
        		    		
    		}
    	    
    	}
    	else{
    		em.setStatusCode(400);
    		em.setMessage("Please enter valid status (available, hold, check-out, lost)");
    					
    		return Response.ok(em).build();
    		    		
    	}
       	
    }
    
    @Timed(name = "delete-book")
    @DELETE
    @Path("/{isbn}")
	    
    public Response deletebook(@PathParam("isbn") int isbn)
    {
    	BookStore bs = new BookStore();
    	int count = 0;
    	if (isbn!=0) {
    		count = bs.countNumberOfBooksHashmap(isbn);
    	}
    	
    	if(count!=0) {
    		
    	    bs.deleteBookDetailsHashmap(isbn);
    	    LinksDto links = new LinksDto();
    		links.addLink(new LinkDto("create-book", "/books", "POST"));

    		return Response.ok(links).build();
    	}
    	else {
    		ErrorMessage em = new ErrorMessage();
    		em.setStatusCode(400);
    		em.setMessage("Book with the given isbn is not present");
    		return Response.ok(em).build();
    	}    
	    														
  }
	   
    @Timed(name = "create-review")
    @POST 
    @Path("/{isbn}/reviews")
       
    public Response createReview(@PathParam("isbn") int isbn, Review review) {
    	
    	BookStore bs = new BookStore();
		int count=0;
    	
    	if ( isbn !=0) {
    		count = bs.countNumberOfBooksHashmap(isbn);
    		if(count !=0){
    			if(review.getComment() != null) {
        			if((review.getRating() !=0) && (review.getRating() >=1) && (review.getRating() <=5) ) {
        				
        				review_id = review_id + 1;
        		    	review.setId(review_id);
        				BookReviewStore bookReviewStore = new BookReviewStore();
                    	bookReviewStore.addReviewToHashMap(isbn, review_id, review);
                    	
                    	LinksDto links = new LinksDto();
                		links.addLink(new LinkDto("view-reviews", "/books/"+isbn+"/reviews/"+review.getId(), "GET"));

                		return Response.status(201).entity(links).build(); 
        			}
        			else{
        				ErrorMessage em = new ErrorMessage();
                		em.setStatusCode(400);
                		em.setMessage("Please enter review rating between (1-5)");
                		return Response.ok(em).build();
        			}
    		}
    			else{
    				ErrorMessage em = new ErrorMessage();
            		em.setStatusCode(400);
            		em.setMessage("Review Comment field is missing");
            		return Response.ok(em).build();
    			}
    	
    		
    		}	
    		else{
    			ErrorMessage em = new ErrorMessage();
        		em.setStatusCode(400);
        		em.setMessage("Book with the given Isbn does not exist");
        		return Response.ok(em).build();
    		}
    	}
    	else{
    		ErrorMessage em = new ErrorMessage();
    		em.setStatusCode(400);
    		em.setMessage("Please enter valid Isbn");
    		return Response.ok(em).build();
    	}  	 
    }
    
    @Timed(name = "view-reviews")
    @GET 
    @Path("/{isbn}/reviews/{id}")
        
    public Response getBookReviewByIsbn(@PathParam("isbn") int isbn, @PathParam("id") int id) {
    	
    	Review review = new Review();
    	BookStore bs = new BookStore();
    	BookReviewStore brs = new BookReviewStore();
    	int count = 0;
    	if((isbn !=0) && (id != 0)) {
    		
    		count = bs.countNumberOfBooksHashmap(isbn);
    		if(count !=0) {
    			count = brs.countReviewDetailsHashmap(id);
    			if (count != 0) {
    				review = brs.searchBookReviewDetailsHashmap(isbn, id);
	    			BookReviewDto bookReviewResponse = new BookReviewDto(review);
	            	bookReviewResponse.addLink(new LinkDto("view-book", "/books/" + isbn + "/reviews/" + id, "GET"));
	            	return Response.ok(bookReviewResponse).build();
    			}
    			else {
    				ErrorMessage em = new ErrorMessage();
            		em.setStatusCode(400);
            		em.setMessage("Review ID does not exits");
            		return Response.ok(em).build();
    			}
    		}
    		else {
    			ErrorMessage em = new ErrorMessage();
        		em.setStatusCode(400);
        		em.setMessage("Book with the given Isbn does not exits");
        		return Response.ok(em).build();
    		}
	
    	}
    	else {
    		ErrorMessage em = new ErrorMessage();
    		em.setStatusCode(400);
    		em.setMessage("Please enter valid Isbn and Review ID");
    		return Response.ok(em).build();
    	}
	    
    }
    
    
    @Timed(name = "view-all-reviews")
    @GET 
    @Path("/{isbn}/reviews")
        
    public Response getAllBookReviewsByIsbn(@PathParam("isbn") int isbn) {
    	
    	ArrayList<Review> allReviewsList = new ArrayList<Review>();
    	BookReviewStore brs = new BookReviewStore();
    	BookStore bs = new BookStore();
    	int count = 0;
    	if(isbn !=0) {
    		count = bs.countNumberOfBooksHashmap(isbn);
    		if(count !=0) {
    			allReviewsList = brs.searchAllBookReviewDetailsHashmap(isbn);
	    
    	
    			AllBookReviewsDto bookReviewResponse = new AllBookReviewsDto(allReviewsList);
    			bookReviewResponse.addLink(new LinkDto("view-book", "/books/" + isbn + "/reviews" , "GET"));
    		
    			return Response.ok(bookReviewResponse).build();
    		}
    		else {
    			ErrorMessage em = new ErrorMessage();
        		em.setStatusCode(400);
        		em.setMessage("Book does not exists");
        		return Response.ok(em).build();
    		}
    	}
    	else {
    		ErrorMessage em = new ErrorMessage();
    		em.setStatusCode(400);
    		em.setMessage("Please enter valid Isbn");
    		return Response.ok(em).build();
    	}
  	
    }
}

