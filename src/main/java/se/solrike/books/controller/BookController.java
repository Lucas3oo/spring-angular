package se.solrike.books.controller;

import java.util.Collection;
import java.util.stream.Collectors;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import io.swagger.annotations.Api;
import se.solrike.books.model.Book;
import se.solrike.books.model.BookRepository;
import se.solrike.books.model.BookView;


// curl http:/localhost:9443/app/api/1.0/books

@Component
@Path("/api/1.0/books")
@Api()
public class BookController {

  private BookRepository mBookRepository;

  @Autowired
  public BookController(BookRepository bookRepository) {
    mBookRepository = bookRepository;
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
  public Collection<Book> getBooks() {
    return mBookRepository.findAll().stream().filter(this::isCool).collect(Collectors.toList());
  }

  @GET
  @Path("{name}")
  @Produces(MediaType.APPLICATION_JSON)
  @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
  public BookView getBook(@PathParam("name") String name) {
    return mBookRepository.findByMName(name);
  }
 
  
  private boolean isCool(Book book) {
    return !book.getName().equals("AMC Gremlin") && !book.getName().equals("Triumph Stag")
        && !book.getName().equals("Ford Pinto") && !book.getName().equals("Yugo GV");
  }

  @DELETE
  @Path("{bookId}")
  @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
  public void deleteBook(@PathParam("bookId") Long bookId) {
    mBookRepository.deleteById(bookId);
  }
}
