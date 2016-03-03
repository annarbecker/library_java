import java.util.HashMap;
import java.util.Date;
import java.lang.StringBuilder;
import java.util.List;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

import static spark.Spark.*;

public class App {
  public static void main(String[] args) {
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";


    get("/", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      String userFirst = request.queryParams("inputtedFirstName");
      String userLast = request.queryParams("inputtedLastName");
      List<Author> searchedAuthors = Author.searchAuthors("%" + userFirst + "%", "%" + userLast + "%");
      String userTitle = request.queryParams("inputtedTitle");
      List<Book> searchedBooks = Book.searchBooks("%" + userTitle + "%");
      model.put("searchedAuthors", searchedAuthors);
      model.put("searchedBooks", searchedBooks);
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/authors", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("authors", Author.all());
      model.put("template", "templates/authors.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/authors", (request, response) -> {
      String firstName = request.queryParams("firstName");
      String lastName = request.queryParams("lastName");
      Author newAuthor = new Author(firstName, lastName);
      newAuthor.save();
      response.redirect("/authors");
      return null;
    });

    get("/books", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("books", Book.all());
      model.put("template", "templates/books.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/books", (request, response) -> {
      String title = request.queryParams("title");
      Integer copies = Integer.parseInt(request.queryParams("copies"));
      Book newBook = new Book(title);
      newBook.save(copies);
      response.redirect("/books");
      return null;
    });

    get("/book/:id", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      int bookId = Integer.parseInt(request.params("id"));
      Book book = Book.find(bookId);
      model.put("allPatrons", Patron.all());
      model.put("book", book);
      model.put("template", "templates/book.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/book/:id/delete", (request, response) -> {
      int bookId = Integer.parseInt(request.params("id"));
      Book book = Book.find(bookId);
      book.deleteBook();
      response.redirect("/");
      return null;
    });


    post("/author/:id/delete", (request, response) -> {
      int authorId = Integer.parseInt(request.params("id"));
      Author author = Author.find(authorId);
      author.deleteAuthor();
      response.redirect("/");
      return null;
    });

    get("/patrons", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("patrons", Patron.all());
      model.put("template", "templates/patrons.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/patrons", (request, response) -> {
      String firstName = request.queryParams("firstName");
      String lastName = request.queryParams("lastName");
      String phoneNumber = request.queryParams("phoneNumber");
      Patron newPatron = new Patron(firstName, lastName, phoneNumber);
      newPatron.save();
      response.redirect("/patrons");
      return null;
    });

    post("/checkout", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      int patronId = Integer.parseInt(request.queryParams("patron_id"));
      Patron patron = Patron.find(patronId);
      int bookId = Integer.parseInt(request.queryParams("book_id"));
      Book book = Book.find(bookId);
      book.setStatusCheckOut();
      String checkout_date = request.queryParams("checkout_date");
      String due_date = request.queryParams("due_date");
      Checkout newCheckout = new Checkout(patronId, bookId, checkout_date, due_date);
      newCheckout.save();
      model.put("patron", patron);
      model.put("book", book);
      model.put("checkout", newCheckout);
      model.put("template", "templates/checkout.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());
    //
    // get("/student/:id", (request, response) -> {
    //   HashMap<String, Object> model = new HashMap<String, Object>();
    //   int studentId = Integer.parseInt(request.params("id"));
    //   Student student = Student.find(studentId);
    //   model.put("student", student);
    //   model.put("enrolledCourses", student.getCourses());
    //   model.put("enrolledDepartments", student.getDepartments());
    //   model.put("allCourses", Course.all());
    //   model.put("allDepartments", Department.all());
    //   model.put("template", "templates/student.vtl");
    //   return new ModelAndView(model, layout);
    // }, new VelocityTemplateEngine());
    //
    // post("/student/:id", (request, response) -> {
    //   int studentId = Integer.parseInt(request.params("id"));
    //   Student student = Student.find(studentId);
    //   String enrollmentDate = request.queryParams("enrollmentDate");
    //   int courseId = Integer.parseInt(request.queryParams("course_id"));
    //   int departmentId = Integer.parseInt(request.queryParams("department_id"));
    //   if (enrollmentDate != null){
    //     student.setEnrollmentDate(enrollmentDate);
    //   }
    //   if (courseId > 0) {
    //     Course course = Course.find(courseId);
    //     student.addCourse(course);
    //   }
    //   if (departmentId > 0) {
    //     Department department = Department.find(departmentId);
    //     student.addDepartment(department);
    //   }
    //   response.redirect("/student/" + student.getId());
    //   return null;
    // });
    //
    // get("/courses", (request, response) -> {
    //   HashMap<String, Object> model = new HashMap<String, Object>();
    //   model.put("allCourses", Course.all());
    //   model.put("template", "templates/courses.vtl");
    //   return new ModelAndView(model, layout);
    // }, new VelocityTemplateEngine());
    //
    // post("/courses", (request, response) -> {
    //   String courseName = request.queryParams("courseName");
    //   String courseNumber = request.queryParams("courseNumber");
    //   Course newCourse = new Course(courseName, courseNumber);
    //   newCourse.save();
    //   response.redirect("/courses");
    //   return null;
    // });
    //
    // post("/course/:id/delete", (request, response) -> {
    //   int courseId = Integer.parseInt(request.params("id"));
    //   Course course = Course.find(courseId);
    //   course.deleteCourse();
    //   response.redirect("/courses");
    //   return null;
    // });
    //
    // get("/departments", (request, response) -> {
    //   HashMap<String, Object> model = new HashMap<String, Object>();
    //   model.put("allDepartments", Department.all());
    //   model.put("template", "templates/departments.vtl");
    //   return new ModelAndView(model, layout);
    // }, new VelocityTemplateEngine());
    //
    // post("/departments", (request, response) -> {
    //   String departmentName = request.queryParams("departmentName");
    //   Department newDepartment = new Department(departmentName);
    //   newDepartment.save();
    //   response.redirect("/departments");
    //   return null;
    // });
    //
    // post("/department/:id/delete", (request, response) -> {
    //   int departmentId = Integer.parseInt(request.params("id"));
    //   Department department = Department.find(departmentId);
    //   department.delete();
    //   response.redirect("/departments");
    //   return null;
    // });
  }
}
