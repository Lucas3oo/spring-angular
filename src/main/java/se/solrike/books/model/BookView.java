package se.solrike.books.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonGetter;

public interface BookView {
  
  // as long no @Value is used the projection is closed and spring-data can optimize what is fetched from the DB
  // but also collections forces the projection to be open which means no DB fetch optimizations.  
  
  @JsonGetter("name")
  String getmName();

  @JsonGetter("comments")
  List<BookCommentView> getmComments();

}
