import java.util.List;
import java.lang.StringBuilder;
import java.util.ArrayList;
import org.sql2o.*;

public class Author {
  private int id;
  private String first_name;
  private String last_name;

  public int getId() {
    return id;
  }

  public String getFirstName() {
    return first_name;
  }

  public String getLastName() {
    return last_name;
  }


  public Author(String first_name, String last_name) {
    this.first_name = first_name;
    this.last_name = last_name;
  }

  public static List<Author> all() {
    String sql = "SELECT * FROM authors ORDER BY last_name, first_name";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Author.class);
    }
  }

  @Override
  public boolean equals(Object otherAuthor){
    if (!(otherAuthor instanceof Author)) {
      return false;
    } else {
      Author newAuthor = (Author) otherAuthor;
      return this.getFirstName().equals(newAuthor.getFirstName()) &&
      this.getLastName().equals(newAuthor.getLastName());
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO authors(first_name, last_name) VALUES (:first_name, :last_name)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("first_name", this.first_name)
        .addParameter("last_name", this.last_name)
        .executeUpdate()
        .getKey();
    }
  }

  public void deleteAuthor() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM authors WHERE id = :id";
      con.createQuery(sql)
        .addParameter("id", id)
        .executeUpdate();

      String authroBookDeleteQuery = "DELETE FROM authors_books WHERE author_id = :author_id";
      con.createQuery(authroBookDeleteQuery)
        .addParameter("author_id", id)
        .executeUpdate();
    }
  }

  public static Author find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM authors where id = :id";
      Author author = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Author.class);
      return author;
    }
  }

  public void addBook(Book book) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO authors_books (author_id, book_id) VALUES (:author_id, :book_id)";
      con.createQuery(sql)
        .addParameter("author_id", this.getId())
        .addParameter("book_id", book.getId())
        .executeUpdate();
    }
  }

  public List<Book> getBooks() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT books.* FROM authors JOIN authors_books ON authors.id = authors_books.author_id JOIN books ON authors_books.book_id = books.id WHERE author_id = :id";
      return con.createQuery(sql).addParameter("id", this.getId()).executeAndFetch(Book.class);
    }
  }

  public void update(String new_first_name, String new_last_name) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE authors SET first_name = :new_first_name, last_name = :new_last_name WHERE id = :id";
      con.createQuery(sql)
        .addParameter("new_first_name", new_first_name)
        .addParameter("new_last_name", new_last_name)
        .addParameter("id", this.getId())
        .executeUpdate();
    }
  }

  public static List<Author> searchAuthors(String userFirst, String userLast) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM authors WHERE first_name LIKE :userFirst OR last_name LIKE :userLast";
      return con.createQuery(sql)
        .addParameter("userFirst", userFirst)
        .addParameter("userLast", userLast)
        .executeAndFetch(Author.class);
    }
  }
  public static List<Author> searchAuthorsFirstName(String userFirst) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM authors WHERE first_name LIKE :userFirst ";
      return con.createQuery(sql)
        .addParameter("userFirst", userFirst)
        .executeAndFetch(Author.class);
    }
  }
  public static List<Author> searchAuthorsLastName(String userLast) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM authors WHERE last_name LIKE :userLast";
      return con.createQuery(sql)
        .addParameter("userLast", userLast)
        .executeAndFetch(Author.class);
    }
  }
  //
  // public static String toDisplayCase(String s) {
  //
  //   final String ACTIONABLE_DELIMITERS = " '-/"; // these cause the character following
  //                                                // to be capitalized
  //
  //   StringBuilder sb = new StringBuilder();
  //   boolean capNext = true;
  //
  //   for (char c : s.toCharArray()) {
  //       c = (capNext)
  //               ? Character.toUpperCase(c)
  //               : Character.toLowerCase(c);
  //       sb.append(c);
  //       capNext = (ACTIONABLE_DELIMITERS.indexOf((int) c) >= 0); // explicit cast not needed
  //   }
  //   return sb.toString();
  // }
}
