import org.junit.*;
import static org.junit.Assert.*;
import java.util.List;

public class AuthorTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();


  @Test
  public void all_emptyAtFirst() {
    assertEquals(Author.all().size(), 0);
  }

  @Test
  public void equals_returnsTrueIfNamesAreTheSame() {
    Author firstAuthor = new Author("Daren", "Schaad");
    Author secondAuthor = new Author("Daren", "Schaad");
    assertTrue(firstAuthor.equals(secondAuthor));
  }

  @Test
  public void save_savesIntoDatabase() {
    Author firstAuthor = new Author("Daren", "Schaad");
    firstAuthor.save();
    assertTrue(Author.all().get(0).equals(firstAuthor));
  }

  @Test
  public void all_returnsAllAuthors() {
    Author firstAuthor = new Author("Daren", "Schaad");
    firstAuthor.save();
    assertEquals(Author.all().size(), 1);
  }

  @Test
  public void find_findAuthorInDatabase() {
  Author firstAuthor = new Author("Daren", "Schaad");
  firstAuthor.save();
  Author savedAuthor = Author.find(firstAuthor.getId());
  assertTrue(firstAuthor.equals(savedAuthor));
  }

  @Test
  public void delete_deletesAuthorFromDatabase() {
    Author myAuthor = new Author("Megan", "Fayer");
    myAuthor.save();
    myAuthor.deleteAuthor();
    assertEquals(Author.all().size(), 0);
  }

  @Test
  public void addBook_addsBookToAuthorInJoinTable() {
    Author myAuthor = new Author("Megan", "Fayer");
    myAuthor.save();
    Book testBook = new Book("Goodnight Moon");
    testBook.save(1);
    myAuthor.addBook(testBook);
    Book savedBook = myAuthor.getBooks().get(0);
    assertTrue(testBook.equals(savedBook));
    assertEquals(myAuthor.getBooks().get(0), testBook);
  }
}
