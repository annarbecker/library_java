import java.util.List;
import java.util.ArrayList;
import org.sql2o.*;

public class Checkout {
  private int id;
  private int patron_id;
  private int book_id;
  private String checkout_date;
  private String due_date;
  private String return_date;

  public int getId() {
    return id;
  }

  public int getPatronId() {
    return patron_id;
  }

  public int getBookId() {
    return book_id;
  }

  public String getCheckoutDate() {
    return checkout_date;
  }

  public String getDueDate() {
    return due_date;
  }

  public String getReturnDate() {
    return return_date;
  }

  public Checkout(int patron_id, int book_id, String checkout_date, String due_date) {
    this.patron_id = patron_id;
    this.book_id = book_id;
    this.checkout_date = checkout_date;
    this.due_date = due_date;
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO checkouts (patron_id, book_id, checkout_date, due_date) VALUES (:patron_id, :book_id, :checkout_date, :due_date)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("patron_id", this.patron_id)
        .addParameter("book_id", this.book_id)
        .addParameter("checkout_date", this.checkout_date)
        .addParameter("due_date", this.due_date)
        .executeUpdate()
        .getKey();
    }
  }

  public static Checkout find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM checkouts where id = :id";
      return con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Checkout.class);
    }
  }


  public List<Checkout> all() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM checkouts ORDER BY due_date";
      return con.createQuery(sql).executeAndFetch(Checkout.class);
    }
  }

  @Override
  public boolean equals(Object otherCheckout){
    if (!(otherCheckout instanceof Checkout)) {
      return false;
    } else {
      Checkout newCheckout = (Checkout) otherCheckout;
      return this.getPatronId() == newCheckout.getPatronId() &&
      this.getBookId()== newCheckout.getBookId();
    }
  }

  public void bookCheckIn(String return_date) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE checkouts SET return_date = :return_date WHERE id = :id";
      con.createQuery(sql)
        .addParameter("return_date", return_date)
        .addParameter("id", id)
        .executeUpdate();
    }
  }

  public Book getBook() {
  try(Connection con = DB.sql2o.open()) {
    String sql = "SELECT books.* FROM checkouts JOIN books ON checkouts.book_id = books.id WHERE checkouts.book_id = :id";

    return con.createQuery(sql).addParameter("id", this.getBookId()).executeAndFetchFirst(Book.class);
    }
  }
}
