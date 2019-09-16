package se.solrike.books.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class CarComment extends AbstractEntity {
  private String mComment;
  
  // using eager to not get exception when fetching over REST
  @ManyToOne(fetch = FetchType.EAGER) 
  @JoinColumn(name = "mCarFk", nullable = false)
  private Car mCar;
  
  public CarComment() {
  }

  public CarComment(String comment) {
    mComment = comment;
  }
  
  public Car getPost() {
    return mCar;
  }
  
  public void setCar(Car car) {
    mCar = car;
  }
  

  public String getComment() {
    return mComment;
  }
  
  public void setComment(String comment) {
    mComment = comment;
  }

  @Override
  public String toString() {
    return "CarComment [mId=" + mId + ", mComment=" + mComment + "]";
  }
  
}
