import java.sql.Connection;
import java.util.Date;
import java.text.SimpleDateFormat;

import dr.java_dao.mysql.MySqlDaoFactory;
import dr.java_dao.entities.Student;
import dr.java_dao.dao.StudentDao;


public class DaoTest {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello from DaoTest");
        MySqlDaoFactory daoFactory = new MySqlDaoFactory();
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
    }
}
