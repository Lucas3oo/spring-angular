package se.solrike.books.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

// Bidirectional OneToMany (with EAGER so it can be sent to the GUI without issues)

@Entity
public class Car extends AbstractEntity {
  private String mName;

  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "mCar")
  @OrderBy("mComment desc")
  private List<CarComment> mComments = new ArrayList<>();

  public Car() {
  }

  /*
   * The parent entity, Post, features two utility methods (e.g. addComment and
   * removeComment) which are used to synchronize both sides of the
   * bidirectional association. You should always provide these methods whenever
   * you are working with a bidirectional association as, otherwise, you risk
   * very subtle state propagation issues.
   */

  public void addComment(CarComment comment) {
    mComments.add(comment);
    comment.setCar(this);
  }

  public void removeComment(CarComment comment) {
    mComments.remove(comment);
    comment.setCar(null);
  }

  public String getName() {
    return mName;
  }

  public void setName(String name) {
    mName = name;
  }

  public List<CarComment> getComments() {
    return mComments;
  }

  public void setComments(List<CarComment> comments) {
    mComments = comments;
  }

  @Override
  public String toString() {
    return "Post [mId=" + mId + ", mName=" + mName + "]";
  }

}
