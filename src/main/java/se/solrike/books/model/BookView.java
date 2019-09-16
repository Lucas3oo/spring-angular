package se.solrike.books.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonGetter;

public interface BookView {
  
  @JsonGetter("name")
  String getmName();

  @JsonGetter("comments")
  List<BookCommentView> getmComments();

}
