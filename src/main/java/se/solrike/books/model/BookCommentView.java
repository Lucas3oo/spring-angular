package se.solrike.books.model;

import com.fasterxml.jackson.annotation.JsonGetter;

public interface BookCommentView {
  
  @JsonGetter("comment")
  String getmComment();


}
