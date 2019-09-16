package se.solrike.books;

import java.util.stream.Stream;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import se.solrike.books.controller.BookController;
import se.solrike.books.model.Book;
import se.solrike.books.model.BookComment;
import se.solrike.books.model.BookRepository;
import se.solrike.books.model.Car;
import se.solrike.books.model.CarComment;
import se.solrike.books.model.CarRepository;

@SpringBootApplication(scanBasePackageClasses = { BookController.class, BooksApplication.class })
@EnableJpaRepositories(basePackages = { "se.solrike.books" })
public class BooksApplication {

  public static void main(String[] args) {
    SpringApplication.run(BooksApplication.class, args);
  }

  @Bean
  ApplicationRunner initBooks(BookRepository repository) {
    return args -> {
      Stream.of("Lord of the rings", "Hobbit", "Silmarillion", "Unfinished Tales and The History of Middle-earth")
          .forEach(name -> {
            Book book = new Book();
            book.setName(name);
            book.setAuthor("J.R.R. Tolkien");
            BookComment bookComment = new BookComment("First comment");
            book.getComments().add(bookComment);
            bookComment = new BookComment("Second comment");
            book.getComments().add(bookComment);
            repository.save(book);
          });
      repository.findAll().forEach(System.out::println);
    };
  }

  @Bean
  ApplicationRunner initCars(CarRepository repository) {
    return args -> {
      Stream.of("Ferrari", "Jaguar", "Porsche", "Lamborghini", "Bugatti", "AMC Gremlin", "Triumph Stag", "Ford Pinto",
          "Yugo GV").forEach(name -> {
            Car car = new Car();
            car.setName(name);
            CarComment carComment = new CarComment("First comment");
            car.addComment(carComment);
            carComment = new CarComment("Second comment");
            car.addComment(carComment);
            repository.save(car);
          });
      repository.findAll().forEach(System.out::println);
    };
  }

}
