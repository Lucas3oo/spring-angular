package se.solrike.books.model;

import javax.persistence.Entity;

@Entity
public class BookComment extends AbstractEntity {
  private String mComment;
  
  public BookComment() {
  }
  
  public BookComment(String comment) {
    mComment = comment;
  }

  public String getComment() {
    return mComment;
  }
  
  public void setComment(String comment) {
    mComment = comment;
  }

  @Override
  public String toString() {
    return "BookComment [mId=" + mId + ", mComment=" + mComment + "]";
  }
  
}
