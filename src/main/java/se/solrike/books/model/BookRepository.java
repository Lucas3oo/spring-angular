package se.solrike.books.model;

import org.springframework.data.jpa.repository.JpaRepository;


public interface BookRepository extends JpaRepository<Book, Long> {
  
  BookView findByMName(String name);
 
}
