import org.junit.rules.ExternalResource;
import org.sql2o.*;

public class DatabaseRule extends ExternalResource {

  protected void before() {
    DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/library_test", null, null);
   }

  protected void after() {
    try(Connection con = DB.sql2o.open()) {
      String deleteAuthorsQuery = "DELETE FROM authors *;";
      String deleteAuthorsBooksQuery = "DELETE FROM authors_books *;";
      String deleteBooksQuery = "DELETE FROM books *;";
      String deleteCheckoutsQuery = "DELETE FROM checkouts *;";
      String deleteCopiesQuery = "DELETE FROM copies *;";
      String deletePatronsQuery = "DELETE FROM patrons *;";
      con.createQuery(deleteAuthorsQuery).executeUpdate();
      con.createQuery(deleteAuthorsBooksQuery).executeUpdate();
      con.createQuery(deleteBooksQuery).executeUpdate();
      con.createQuery(deleteCheckoutsQuery).executeUpdate();
      con.createQuery(deleteCopiesQuery).executeUpdate();
      con.createQuery(deletePatronsQuery).executeUpdate();
    }
  }
}
