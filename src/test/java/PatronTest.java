import org.junit.*;
import static org.junit.Assert.*;
import java.util.List;

public class PatronTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();


  @Test
  public void all_emptyAtFirst() {
    assertEquals(Patron.all().size(), 0);
  }

  @Test
  public void equals_returnsTrueIfNamesAreTheSame() {
    Patron firstPatron = new Patron("Daren", "Schaad", "123");
    Patron secondPatron = new Patron("Daren", "Schaad", "123");
    assertTrue(firstPatron.equals(secondPatron));
  }

  @Test
  public void save_savesIntoDatabase() {
    Patron firstPatron = new Patron("Daren", "Schaad", "123");
    firstPatron.save();
    assertTrue(Patron.all().get(0).equals(firstPatron));
  }

  @Test
  public void all_returnsAllPatrons() {
    Patron firstPatron = new Patron("Daren", "Schaad", "123");
    firstPatron.save();
    assertEquals(Patron.all().size(), 1);
  }

  @Test
  public void find_findPatronInDatabase() {
  Patron firstPatron = new Patron("Daren", "Schaad", "123");
  firstPatron.save();
  Patron savedPatron = Patron.find(firstPatron.getId());
  assertTrue(firstPatron.equals(savedPatron));
  }

  @Test
  public void delete_deletesPatronFromDatabase() {
    Patron myPatron = new Patron("Megan", "Fayer", "123");
    myPatron.save();
    myPatron.deletePatron();
    assertEquals(Patron.all().size(), 0);
  }

  @Test
  public void addBook_addsBookToPatronInJoinTable() {
    Patron myPatron = new Patron("Megan", "Fayer", "123");
    myPatron.save();
    Book testBook = new Book("PE");
    testBook.save(1);
    myPatron.addBook(testBook);
    Book savedBook = myPatron.getBooks().get(0);
    assertTrue(testBook.equals(savedBook));
    assertEquals(myPatron.getBooks().get(0), testBook);
  }
}
