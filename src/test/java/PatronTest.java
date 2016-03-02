// import org.junit.*;
// import static org.junit.Assert.*;
// import java.util.List;
//
// public class AuthorTest {
//
//   @Rule
//   public DatabaseRule database = new DatabaseRule();
//
//
//   @Test
//   public void all_emptyAtFirst() {
//     assertEquals(Author.all().size(), 0);
//   }
//
//   @Test
//   public void equals_returnsTrueIfNamesAreTheSame() {
//     Author firstAuthor = new Author("Daren", "Schaad");
//     Author secondAuthor = new Author("Daren", "Schaad");
//     assertTrue(firstAuthor.equals(secondAuthor));
//   }
//
//   @Test
//   public void save_savesIntoDatabase() {
//     Author firstAuthor = new Author("Daren", "Schaad");
//     firstAuthor.save();
//     assertTrue(Author.all().get(0).equals(firstAuthor));
//   }
//
//   @Test
//   public void all_returnsAllAuthors() {
//     Author firstAuthor = new Author("Daren", "Schaad");
//     firstAuthor.save();
//     assertEquals(Author.all().size(), 1);
//   }
//
//   @Test
//   public void find_findAuthorInDatabase() {
//   Author firstAuthor = new Author("Daren", "Schaad");
//   firstAuthor.save();
//   Author savedAuthor = Author.find(firstAuthor.getId());
//   assertTrue(firstAuthor.equals(savedAuthor));
//   }
//
//   @Test
//   public void delete_deletesAuthorFromDatabase() {
//     Author myAuthor = new Author("Megan", "Fayer");
//     myAuthor.save();
//     myAuthor.deleteAuthor();
//     assertEquals(Author.all().size(), 0);
//   }
//
//   @Test
//   public void setEnrollmentDate_setsDateAuthorsEnrolled() {
//     String enrollment_date = "2016/05/30";
//     Author myAuthor = new Author("Megan", "Fayer");
//     myAuthor.save();
//     myAuthor.setEnrollmentDate(enrollment_date);
//     assertEquals(Author.all().get(0).getEnrollmentDate(), "2016/05/30");
//   }
//
//   @Test
//   public void addCourse_addsCourseToAuthorInJoinTable() {
//     Author myAuthor = new Author("Megan", "Fayer");
//     myAuthor.save();
//     Course testCourse = new Course("PE", "PE4567");
//     testCourse.save();
//     myAuthor.addCourse(testCourse);
//     Course savedCourse = myAuthor.getCourses().get(0);
//     assertTrue(testCourse.equals(savedCourse));
//     assertEquals(myAuthor.getCourses().get(0), testCourse);
//   }
//
//   @Test
//   public void addDepartment_addsDepartmentToAuthorInJoinTable() {
//     Author myAuthor = new Author("Megan", "Fayer");
//     myAuthor.save();
//     Department testDepartment = new Department("PE");
//     testDepartment.save();
//     myAuthor.addDepartment(testDepartment);
//     Department savedDepartment = myAuthor.getDepartments().get(0);
//     assertTrue(testDepartment.equals(savedDepartment));
//     assertEquals(savedDepartment, testDepartment);
//   }
//
//
// }
