import java.util.List;
import java.util.ArrayList;
import org.sql2o.*;

public class Patron {
  private int id;
  private String first_name;
  private String last_name;
  private String phone_number;

  public int getId() {
    return id;
  }

  public String getFirstName() {
    return first_name;
  }

  public String getLastName() {
    return last_name;
  }

  public String getPhoneNumber() {
    return phone_number;
  }


  public Patron(String first_name, String last_name, String phone_number) {
    this.first_name = first_name;
    this.last_name = last_name;
    this.phone_number = phone_number;
  }

  public static List<Patron> all() {
    String sql = "SELECT * FROM patrons ORDER BY last_name, first_name";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Patron.class);
    }
  }

  @Override
  public boolean equals(Object otherPatron){
    if (!(otherPatron instanceof Patron)) {
      return false;
    } else {
      Patron newPatron = (Patron) otherPatron;
      return this.getFirstName().equals(newPatron.getFirstName()) &&
      this.getLastName().equals(newPatron.getLastName()) &&
      this.getPhoneNumber().equals(newPatron.getPhoneNumber());
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO patrons (first_name, last_name, phone_number) VALUES (:first_name, :last_name, :phone_number)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("first_name", this.first_name)
        .addParameter("last_name", this.last_name)
        .addParameter("phone_number", this.phone_number)
        .executeUpdate()
        .getKey();
    }
  }

  public void deletePatron() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM patrons WHERE id = :id";
      con.createQuery(sql)
        .addParameter("id", id)
        .executeUpdate();
    }
  }

  public static Patron find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM patrons where id = :id";
      Patron patron = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Patron.class);
      return patron;
    }
  }

  public void addBook(Book book) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO checkouts (patron_id, book_id) VALUES (:patron_id, :book_id)";
      con.createQuery(sql)
        .addParameter("patron_id", this.getId())
        .addParameter("book_id", book.getId())
        .executeUpdate();
    }
  }

  public List<Book> getBooks() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT books.* FROM patrons JOIN checkouts ON patrons.id = checkouts.patron_id JOIN books ON checkouts.book_id = books.id WHERE patron_id = :id";
      return con.createQuery(sql).addParameter("id", this.getId()).executeAndFetch(Book.class);
    }
  }

  public void update(String new_first_name, String new_last_name, String new_phone_number) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE authors SET first_name = :new_first_name, last_name = :new_last_name, phone_number = :new_phone_number WHERE id = :id";
      con.createQuery(sql)
        .addParameter("new_first_name", new_first_name)
        .addParameter("new_last_name", new_last_name)
        .addParameter("new_phone_number", new_phone_number)
        .addParameter("id", this.getId())
        .executeUpdate();
    }
  }
}
