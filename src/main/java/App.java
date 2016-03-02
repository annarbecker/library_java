import java.util.HashMap;
import java.util.Date;
import java.util.List;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

import static spark.Spark.*;

public class App {
  public static void main(String[] args) {
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";


    // get("/", (request, response) -> {
    //   HashMap<String, Object> model = new HashMap<String, Object>();
    //   model.put("students", Student.all());
    //   model.put("template", "templates/index.vtl");
    //   return new ModelAndView(model, layout);
    // }, new VelocityTemplateEngine());
    //
    // post("/", (request, response) -> {
    //   String firstName = request.queryParams("studentFirstName");
    //   String lastName = request.queryParams("studentLastName");
    //   Student newStudent = new Student(firstName, lastName);
    //   newStudent.save();
    //   response.redirect("/");
    //   return null;
    // });
    //
    // post("/student/:id/delete", (request, response) -> {
    //   int studentId = Integer.parseInt(request.params("id"));
    //   Student student = Student.find(studentId);
    //   student.deleteStudent();
    //   response.redirect("/");
    //   return null;
    // });
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
