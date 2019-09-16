package se.solrike.books.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

// unidirectional OneToMany (with EAGER so it can be sent to the GUI without issues, default i LAZY)
// in practice you might fetch to many in OneToMany, works best when there are few child items.
// If @JoinColumn isn't used then Hibernate will create an extra relation table
// @JoinColumn(name = "book_id", nullable = false) means that the FK column isn't allowed to have null which is cleaner.
// And the generated SQL is more effective (fewer statements).

@Entity
public class Book extends AbstractEntity {
  private String mName;
  private String mAuthor;
  
  @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "mBookFk", nullable = false)
  private List<BookComment> mComments = new ArrayList<>();

  public Book() {
  }

  public String getName() {
    return mName;
  }

  public void setName(String name) {
    mName = name;
  }
  
  public String getAuthor() {
    return mAuthor;
  }
  
  public void setAuthor(String author) {
    mAuthor = author;
  }

  public List<BookComment> getComments() {
    return mComments;
  }
  
  public void setComments(List<BookComment> comments) {
    mComments = comments;
  }

  @Override
  public String toString() {
    return "Book [mId=" + mId + ", mName=" + mName + "]";
  }

}
