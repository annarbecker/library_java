// import java.util.List;
// import java.util.Date;
// import java.util.ArrayList;
// import org.sql2o.*;
//
// public class Department {
//   private int id;
//   private String department_name;
//
//   public int getId() {
//     return id;
//   }
//
//   public String getDepartmentName() {
//     return department_name;
//   }
//
//
//   public Department(String department_name) {
//     this.department_name = department_name;
//   }
//
//   @Override
//   public boolean equals(Object otherDepartment){
//     if (!(otherDepartment instanceof Department)) {
//       return false;
//     } else {
//       Department newDepartment = (Department) otherDepartment;
//       return this.getDepartmentName().equals(newDepartment.getDepartmentName()) &&
//              this.getId() == newDepartment.getId();
//     }
//   }
//
//
//   public static List<Department> all() {
//     String sql = "SELECT * FROM departments ORDER BY department_name";
//     try(Connection con = DB.sql2o.open()) {
//       return con.createQuery(sql).executeAndFetch(Department.class);
//     }
//   }
//
//   public void save() {
//     try(Connection con = DB.sql2o.open()) {
//       String sql = "INSERT INTO departments (department_name) VALUES (:department_name)";
//       this.id = (int) con.createQuery(sql, true)
//         .addParameter("department_name", department_name)
//         .executeUpdate()
//         .getKey();
//     }
//   }
//
//   public static Department find(int id) {
//     try(Connection con = DB.sql2o.open()) {
//       String sql = "SELECT * FROM departments where id=:id";
//       Department department = con.createQuery(sql)
//         .addParameter("id", id)
//         .executeAndFetchFirst(Department.class);
//       return department;
//     }
//   }
//
//   public void update(String department_name) {
//     try(Connection con = DB.sql2o.open()) {
//       String sql = "UPDATE departments SET department_name = :department_name WHERE id = :id";
//       con.createQuery(sql)
//         .addParameter("department_name", department_name)
//         .addParameter("id", id)
//         .executeUpdate();
//     }
//   }
//
//   public void delete() {
//     String sql = "DELETE FROM departments WHERE id = :id";
//     try(Connection con = DB.sql2o.open()) {
//       con.createQuery(sql)
//         .addParameter("id", id)
//         .executeUpdate();
//
//     String joinStudentsDeleteQuery = "DELETE FROM students_departments WHERE department_id = :departmentId";
//     con.createQuery(joinStudentsDeleteQuery)
//       .addParameter("departmentId", id)
//       .executeUpdate();
//
//     String joinCoursesDeleteQuery = "DELETE FROM courses_departments WHERE department_id = :departmentId";
//     con.createQuery(joinCoursesDeleteQuery)
//       .addParameter("departmentId", id)
//       .executeUpdate();
//     }
//   }
//
//   public void addStudent(Student student) {
//     try(Connection con = DB.sql2o.open()) {
//       String sql = "INSERT INTO students_departments (student_id, department_id) VALUES (:student_id, :department_id)";
//       con.createQuery(sql)
//         .addParameter("student_id", student.getId())
//         .addParameter("department_id", this.getId())
//         .executeUpdate();
//     }
//   }
//
//   public ArrayList<Student> getStudents() {
//     try(Connection con = DB.sql2o.open()){
//       String sql = "SELECT student_id FROM students_departments WHERE department_id = :department_id";
//       List<Integer> studentIds = con.createQuery(sql)
//         .addParameter("department_id", this.getId())
//         .executeAndFetch(Integer.class);
//
//       ArrayList<Student> students = new ArrayList<Student>();
//
//       for (Integer studentId : studentIds) {
//           String departmentQuery = "Select * From students WHERE id = :studentId ORDER BY last_name, first_name";
//           Student student = con.createQuery(departmentQuery)
//             .addParameter("studentId", studentId)
//             .executeAndFetchFirst(Student.class);
//           students.add(student);
//       }
//       return students;
//     }
//   }
//
//   public void addCourse(Course course) {
//     try(Connection con = DB.sql2o.open()) {
//       String sql = "INSERT INTO courses_departments (department_id, course_id) VALUES (:department_id, :course_id)";
//       con.createQuery(sql)
//         .addParameter("department_id", this.getId())
//         .addParameter("course_id", course.getId())
//         .executeUpdate();
//     }
//   }
//
//   public ArrayList<Course> getCourses() {
//     try(Connection con = DB.sql2o.open()) {
//       String sql = "SELECT course_id FROM courses_departments WHERE department_id = :department_id";
//       List<Integer> courseIds = con.createQuery(sql)
//         .addParameter("department_id", this.getId())
//         .executeAndFetch(Integer.class);
//
//       ArrayList<Course> courses = new ArrayList<Course>();
//
//       for (Integer courseId : courseIds) {
//         String courseQuery = "Select * FROM courses WHERE id = :courseId ORDER BY course_name";
//         Course course = con.createQuery(courseQuery)
//           .addParameter("courseId", courseId)
//           .executeAndFetchFirst(Course.class);
//         courses.add(course);
//       }
//       return courses;
//     }
//   }
//
//   // public void removeStudent(int studentId) {
//   //   try(Connection con = DB.sql2o.open()){
//   //     String sql ="DELETE FROM categories_departments WHERE category_id =  :categoryId AND task_id = :taskId";      con.createQuery(sql)
//   //       .addParameter("categoryId", categoryId)
//   //       .addParameter("taskId", this.getId())
//   //       .executeUpdate();
//   //   }
//   // }
//   //
//   // public void deCompleteTask() {
//   // try(Connection con = DB.sql2o.open()){
//   //   String sql = "UPDATE tasks SET complete = false WHERE id = :id";
//   //   con.createQuery(sql)
//   //     .addParameter("id", id)
//   //     .executeUpdate();
//   //   }
//   // }
//   //
//   // public void completeTask() {
//   //   try(Connection con = DB.sql2o.open()){
//   //     String sql = "UPDATE tasks SET complete = true WHERE id = :id";
//   //     con.createQuery(sql)
//   //       .addParameter("id", id)
//   //       .executeUpdate();
//   //   }
//   // }
//   //
//   // public void addDue(String dueDate) {
//   //   try(Connection con = DB.sql2o.open()){
//   //     String sql = "UPDATE tasks SET due = :dueDate WHERE id = :id";
//   //     con.createQuery(sql)
//   //       .addParameter("id", id)
//   //       .addParameter("dueDate", dueDate)
//   //       .executeUpdate();
//   //   }
//   // }
// }
