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
    testBook.save();
    assertEquals(Book.all().size(), 1);
  }

  @Test
  public void find_findsBookFromDatabase() {
    Book testBook = new Book("Goodnight Moon");
    testBook.save();
    Book savedBook = Book.find(testBook.getId());
    assertTrue(testBook.equals(savedBook));
  }

  @Test
  public void updateBook_updatedTheNameOfTheBook() {
    Book myBook = new Book("Goodnight Moon");
    myBook.save();
    Book savedBook = Book.find(myBook.getId());
    savedBook.updateBook("Cat in the Hat");
    assertEquals(Book.all().get(0).getBookName(), "Cat in the Hat");
  }

  @Test
  public void delete_deletesBookFromDatabase() {
    Book myBook = new Book("Goodnight Moon");
    myBook.save();
    myBook.deleteBook();
    assertEquals(Book.all().size(), 0);
  }

  @Test
  public void addAuthor_addsAuthorToBookInJoinTable() {
    Author myAuthor = new Author("Megan", "Fayer");
    myAuthor.save();
    Book myBook = new Book("Goodnight Moon");
    myBook.save();
    myBook.addAuthor(myAuthor);
    Author savedAuthor = myBook.getAuthors().get(0);
    assertTrue(myAuthor.equals(savedAuthor));
  }

  // @Test
  // public void addDepartment_addsDepartmentToBookInJoinTable() {
  //   Department myDepartment = new Department("Test");
  //   myDepartment.save();
  //   Book myBook = new Book("PE", "PE4567");
  //   myBook.save();
  //   myBook.addDepartment(myDepartment);
  //   Department savedDepartment = myBook.getDepartments().get(0);
  //   assertTrue(myDepartment.equals(savedDepartment));
  // }
}
