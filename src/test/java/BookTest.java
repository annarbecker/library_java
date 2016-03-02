import org.junit.*;
import static org.junit.Assert.*;
import java.util.Arrays;
import java.util.List;

public class BookTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void all_emptyAtFirst() {
    assertEquals(Book.all().size(), 0);
  }

  @Test
  public void save_savesTheBookIntoTheDatabase() {
    Book testBook = new Book("Goodnight Moon");
    testBook.save(1);
    assertEquals(Book.all().size(), 1);
  }

  @Test
  public void find_findsBookFromDatabase() {
    Book testBook = new Book("Goodnight Moon");
    testBook.save(1);
    Book savedBook = Book.find(testBook.getId());
    assertTrue(testBook.equals(savedBook));
  }

  @Test
  public void updateBook_updatedTheNameOfTheBook() {
    Book myBook = new Book("Goodnight Moon");
    myBook.save(1);
    Book savedBook = Book.find(myBook.getId());
    savedBook.updateBook("Cat in the Hat");
    assertEquals(Book.all().get(0).getTitle(), "Cat in the Hat");
  }

  @Test
  public void delete_deletesBookFromDatabase() {
    Book myBook = new Book("Goodnight Moon");
    myBook.save(1);
    myBook.deleteBook();
    assertEquals(Book.all().size(), 0);
  }

  @Test
  public void addAuthor_addsAuthorToBookInJoinTable() {
    Author myAuthor = new Author("Megan", "Fayer");
    myAuthor.save();
    Book myBook = new Book("Goodnight Moon");
    myBook.save(1);
    myBook.addAuthor(myAuthor);
    Author savedAuthor = myBook.getAuthors().get(0);
    assertTrue(myAuthor.equals(savedAuthor));
  }

  @Test
  public void getCopyCount_returnsNumberOfCopiesOfBook() {
    Book myBook = new Book("Goodnight Moon");
    myBook.save(3);
    assertEquals((int)myBook.getCopyCount(), 3);
  }

  @Test
  public void setStatusCheckOut() {
    Book myBook = new Book("Goodnight Moon");
    myBook.save(3);
    Book book = Book.all().get(0);
    book.setStatusCheckOut();
    assertEquals(Book.find(book.getId()).getStatus() , false);
  }
}
