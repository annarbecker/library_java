import java.util.List;
import java.util.Date;
import java.util.ArrayList;
import org.sql2o.*;

public class Book {
  private int id;
  private String title;
  private boolean status;

  public int getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public boolean getStatus() {
    return status;
  }


  public Book(String title) {
    this.title = title;
  }

  @Override
  public boolean equals(Object otherBook){
    if (!(otherBook instanceof Book)) {
      return false;
    } else {
      Book newBook = (Book) otherBook;
      return this.getTitle().equals(newBook.getTitle()) &&
             this.getId() == newBook.getId();
    }
  }

  public static List<Book> all() {
    String sql = "SELECT * FROM books ORDER BY title";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Book.class);
    }
  }

  public void save(int copies) {
    try(Connection con = DB.sql2o.open()) {
      for (int i = 0 ; i < copies ; i++ ){
      String sql = "INSERT INTO books (title, status) VALUES (:title, :status)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("title", title)
        .addParameter("status", true)
        .executeUpdate()
        .getKey();
      }
    }
  }

  public static Book find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM books where id=:id";
      Book book = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Book.class);
      return book;
    }
  }

  public void updateBook(String title) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE books SET title = :title WHERE id = :id";
      con.createQuery(sql)
        .addParameter("title", title)
        .addParameter("id", id)
        .executeUpdate();
    }
  }

  public void deleteBook() {
    String sql = "DELETE FROM books WHERE id = :id";
    try(Connection con = DB.sql2o.open()) {
      con.createQuery(sql)
        .addParameter("id", id)
        .executeUpdate();

    String booksAuthorsDeleteQuery = "DELETE FROM authors_books WHERE book_id = :book_id";
    con.createQuery(booksAuthorsDeleteQuery)
      .addParameter("book_id", id)
      .executeUpdate();
    }
  }


  public void addAuthor(Author author) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO authors_books (author_id, book_id) VALUES (:author_id, :book_id)";
      con.createQuery(sql)
        .addParameter("author_id", author.getId())
        .addParameter("book_id", this.getId())
        .executeUpdate();
    }
  }

  public List<Author> getAuthors() {
    try(Connection con = DB.sql2o.open()){
      String sql = "SELECT authors.* FROM books JOIN authors_books ON books.id = authors_books.book_id JOIN authors ON authors_books.author_id = authors.id WHERE book_id = :id";
      return con.createQuery(sql)
      .addParameter("id", this.getId())
      .executeAndFetch(Author.class);
    }
  }


  public Integer getCopyCount() {
    try(Connection con = DB.sql2o.open()){
      String sql = "SELECT COUNT (*) FROM books WHERE title = :title";
      Integer copyCount = con.createQuery(sql)
        .addParameter("title", this.getTitle())
        .executeAndFetchFirst(Integer.class);
      return copyCount;
    }
  }

  public void setStatusCheckOut() {
    try(Connection con = DB.sql2o.open()){
      String sql = "UPDATE books SET status = :status WHERE id = :id";
      con.createQuery(sql)
        .addParameter("id", this.getId())
        .addParameter("status", false)
        .executeUpdate();
    }
  }

  public void setStatusCheckIn() {
    try(Connection con = DB.sql2o.open()){
      String sql = "UPDATE books SET status = :status WHERE id = :id";
      con.createQuery(sql)
        .addParameter("id", this.getId())
        .addParameter("status", true)
        .executeUpdate();
    }
  }

  public static List<Book> searchBooks(String userTitle) {
    try(Connection con = DB.sql2o.open()){
      String sql = "SELECT * FROM books WHERE title LIKE :userTitle";
    return con.createQuery(sql)
      .addParameter("userTitle", userTitle)
      .executeAndFetch(Book.class);
    }
  }
}
