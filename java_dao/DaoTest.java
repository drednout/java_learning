import java.sql.Connection;
import java.util.Date;
import java.text.SimpleDateFormat;

import dr.java_dao.mysql.MySqlDaoFactory;
import dr.java_dao.entities.Student;
import dr.java_dao.entities.Course;
import dr.java_dao.dao.StudentDao;


public class DaoTest {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello from DaoTest");
        String user = "monstrillo";
        String password = "frygvzvu";
        String url = "jdbc:mysql://localhost:3306/java_db";
        MySqlDaoFactory daoFactory = new MySqlDaoFactory(user, password, url);
        Connection connection = daoFactory.getConnection();
        StudentDao studentDao = daoFactory.getStudentDao(connection);
        Student forthStudent = studentDao.selectStudent(4);
        System.out.println(forthStudent.toString());

        Student newStudent = new Student();
        newStudent.setName("New student");
        newStudent.setSex(Student.Sex.Female);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
        newStudent.setBirthDate(dateFormat.parse("2010-04-04"));
        int deletingStudentId = studentDao.insertStudent(newStudent);

        int newStudentId = studentDao.insertStudent(newStudent);
        System.out.println("New student id is " + newStudentId);
        newStudent.setName("Vasya-Vasya");
        newStudent.setSex(Student.Sex.Male);
        studentDao.updateStudent(newStudent);

        Student studentForDeleting = studentDao.selectStudent(deletingStudentId);
        System.out.println("Deleting student " + deletingStudentId + " ...");
        studentDao.deleteStudent(studentForDeleting);
        Student brandNewStudent = new Student();
        System.out.println("brandNewStudent equals to newStudent: " + (brandNewStudent.equals(newStudent)));
        System.out.println("newStudent equals to newStudent: " + (newStudent.equals(newStudent)));
        Student changedNewStudent = new Student(newStudent);
        //System.out.println("newStudent is " + newStudent.toString());
        //System.out.println("changedNewStudent is " + changedNewStudent.toString());
        System.out.println("(should be true) newStudent equals to changedNewStudent: " + (newStudent.equals(changedNewStudent)));
        changedNewStudent.setName("Linus Torvalds");
        System.out.println("(should be false) newStudent equals to changedNewStudent: " + (newStudent.equals(changedNewStudent)));

        Course course = new Course();
        System.out.println("(should be true) course.equals(course): " + (course.equals(course)));


    }
}
