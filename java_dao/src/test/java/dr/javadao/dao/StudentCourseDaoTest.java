import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.text.SimpleDateFormat;
import java.util.Properties;
import java.text.ParseException;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.io.InputStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

import dr.javadao.dao.impl.mysql.MySqlDaoFactory;
import dr.javadao.entities.Course;
import dr.javadao.entities.Student;
import dr.javadao.dao.CourseDao;
import dr.javadao.dao.StudentDao;
import dr.javadao.dao.StudentCourseDao;
import dr.javadao.dao.DaoException;


public class StudentCourseDaoTest {

    private static MySqlDaoFactory factory;
    private CourseDao courseDao;
    private StudentDao studentDao;
    private StudentCourseDao studentCourseDao;
    private static final String testCourseName = "test course";
    private static final String testStudentName = "test student";
    private static final String propertyFile = "test.properties";
    private static String user;
    private static String password;
    private static String url;


    public StudentCourseDaoTest() {
        InputStream io = this.getClass().getClassLoader().getResourceAsStream(propertyFile);
        if (io == null) {
            System.err.println("ERROR: Unable to find property file: " + propertyFile);
            System.exit(1);
        }

        Properties properties = new Properties();
        try {
            properties.load(io);
        } catch (IOException e) {
            System.err.println("ERROR: Unable to find property file: " + propertyFile);
            System.exit(1);
        }
        user = properties.getProperty("mysql.user");
        password = properties.getProperty("mysql.password");
        url = properties.getProperty("mysql.url");
    }

    @Before
    public void setUp() throws DaoException, SQLException, IOException {
        factory = new MySqlDaoFactory(user, password, url);
        factory.setAutoCommit(true);
        //factory.setDoRollbackWhenClosing(true);
        courseDao = factory.getCourseDao();
        studentDao = factory.getStudentDao();
        studentCourseDao = factory.getStudentCourseDao();
    }

    @After
    public void tearDown() throws Exception {
        factory.close();
        courseDao.close();
    }

    private Course createOneCourse(String name)
            throws DaoException, ParseException {
        Course newCourse = new Course();
        newCourse.setName(name);
        newCourse.setDuration(10);
        return newCourse;
    }

    private Student createOneStudent(String name)
            throws DaoException, ParseException {
        Student newStudent = new Student();
        newStudent.setName(name);
        newStudent.setSex(Student.Sex.Female);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
        newStudent.setBirthDate(dateFormat.parse("2010-04-04"));
        return newStudent;
    }

    @Test
    public void testStudentCourseInsert() throws DaoException, ParseException {
            Course course = createOneCourse(testCourseName);
            Student student = createOneStudent(testStudentName);

            courseDao.insertCourse(course);
            studentDao.insertStudent(student);

            Boolean res = studentCourseDao.joinStudentCourse(student, course);
            Assert.assertTrue(res);

            Boolean isStudentInCourse = studentCourseDao.hasStudentCourse(student, course);
            Assert.assertTrue(isStudentInCourse);
        
    }
    @Test
    public void testStudentCourses() throws DaoException, ParseException {
        List<Course> courseList = new LinkedList<Course>();
        for(int i=0; i< 5; ++i) {
            Course course = createOneCourse(testCourseName + i);
            courseList.add(course);
            courseDao.insertCourse(course);
        }

        Student student = createOneStudent(testStudentName);
        studentDao.insertStudent(student);
        
        ListIterator<Course> courseIterator;

        courseIterator = courseList.listIterator();
        while(courseIterator.hasNext()) {
            Course course = courseIterator.next();
            studentCourseDao.joinStudentCourse(student, course);
        }
        courseIterator = courseList.listIterator();
        while(courseIterator.hasNext()) {
            Course course = courseIterator.next();
            Boolean isStudentInCourse = studentCourseDao.hasStudentCourse(student, course);
            Assert.assertTrue(isStudentInCourse);
        }
        List<Course> courseListFromDb = studentCourseDao.getStudentCourses(student);
        Assert.assertTrue(courseListFromDb.equals(courseList));
    } 
    
    @Test
    public void testCourseStudents() throws DaoException, ParseException {
        List<Student> studentList = new LinkedList<Student>();
        for(int i=0; i< 5; ++i) {
            Student student = createOneStudent(testStudentName + i);
            studentList.add(student);
            studentDao.insertStudent(student);
        }

        Course course = createOneCourse(testCourseName);
        courseDao.insertCourse(course);
        
        ListIterator<Student> studentIterator;

        studentIterator = studentList.listIterator();
        while(studentIterator.hasNext()) {
            Student student = studentIterator.next();
            studentCourseDao.joinStudentCourse(student, course);
        }
        studentIterator = studentList.listIterator();
        while(studentIterator.hasNext()) {
            Student student = studentIterator.next();
            Boolean isStudentInCourse = studentCourseDao.hasStudentCourse(student, course);
            Assert.assertTrue(isStudentInCourse);
        }
        List<Student> studentListFromDb = studentCourseDao.getCourseStudents(course);
        Assert.assertTrue(studentListFromDb.equals(studentList));
    }

        
}

