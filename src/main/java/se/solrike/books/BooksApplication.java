package se.solrike.books;

import java.util.UUID;
import java.util.stream.Stream;

import javax.sql.DataSource;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.jdbc.lock.DefaultLockRepository;
import org.springframework.integration.jdbc.lock.JdbcLockRegistry;
import org.springframework.integration.jdbc.lock.LockRepository;
import org.springframework.integration.support.locks.LockRegistry;
import org.springframework.scheduling.annotation.EnableScheduling;

import se.solrike.books.controller.BookController;
import se.solrike.books.model.Book;
import se.solrike.books.model.BookComment;
import se.solrike.books.model.BookRepository;
import se.solrike.books.model.Car;
import se.solrike.books.model.CarComment;
import se.solrike.books.model.CarRepository;

@SpringBootApplication(scanBasePackageClasses = { BookController.class, BooksApplication.class })
@EnableJpaRepositories(basePackages = { "se.solrike.books" })
@EnableIntegration
@EnableScheduling
public class BooksApplication {

  // client unique id like IP address or hostname
  public static String CLIENT_ID = ("myClientId-" + UUID.randomUUID()).substring(0, 35);

  public static void main(String[] args) {
    SpringApplication.run(BooksApplication.class, args);
  }

  @Bean 
  LockRepository lockRepository(DataSource dataSource) {
    LockRepository lockRepository = new DefaultLockRepository(dataSource, CLIENT_ID);
    return lockRepository;
  }

  @Bean
  LockRegistry lockRegistry(LockRepository lockRepository) {
    return new JdbcLockRegistry(lockRepository);
  }

  /*
   * 
   * CREATE TABLE INT_LOCK ( LOCK_KEY CHAR(36) NOT NULL, REGION VARCHAR(100) NOT
   * NULL, CLIENT_ID CHAR(36), CREATED_DATE DATETIME(6) NOT NULL, constraint
   * INT_LOCK_PK primary key (LOCK_KEY, REGION) ) ENGINE=InnoDB;
   * 
   */

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
