import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
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
import dr.javadao.entities.Student;
import dr.javadao.entities.Course;
import dr.javadao.dao.StudentDao;
import dr.javadao.dao.DaoException;


public class DaoSmokeTest {

    private static MySqlDaoFactory factory;
    private StudentDao studentDao;
    private static final String testStudentName = "test student";
    private static final String propertyFile = "test.properties";
    private static String user;
    private static String password;
    private static String url;


    public DaoSmokeTest() {
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
        factory.setAutoCommit(false);
        factory.setDoRollbackWhenClosing(true);
        studentDao = factory.getStudentDao();
    }

    @After
    public void tearDown() throws Exception {
        factory.close();
        studentDao.close();
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
    public void testInsert() throws DaoException, ParseException {
        Student newStudent = createOneStudent(testStudentName);
        int insertedStudentId = studentDao.insertStudent(newStudent);

        Assert.assertTrue(newStudent.getName().equals(testStudentName));

        Assert.assertNotNull(newStudent.getId());
        Assert.assertTrue(newStudent.getId() == insertedStudentId);

        Student selectedStudent = studentDao.selectStudent(insertedStudentId);

        Assert.assertTrue("Persistent student should be equal to student in memory",
                          selectedStudent.equals(newStudent));
    }
    @Test
    public void testUpdate() throws DaoException, ParseException {
        Student newStudent = createOneStudent(testStudentName);
        int insertedStudentId = studentDao.insertStudent(newStudent);
        
        String newStudentName = "vasya";

        newStudent.setName(newStudentName);
        studentDao.updateStudent(newStudent);

        Assert.assertTrue(newStudent.getName().equals(newStudentName));

        Student selectedStudent = studentDao.selectStudent(insertedStudentId);
        Assert.assertTrue("Persistent student should be equal to student in memory",
                          selectedStudent.equals(newStudent));
        Assert.assertTrue(selectedStudent.getName().equals(newStudentName));

    }
    @Test
    public void testDelete() throws DaoException, ParseException {
        Student newStudent = createOneStudent(testStudentName);
        int insertedStudentId = studentDao.insertStudent(newStudent);
        
        studentDao.deleteStudent(newStudent);
        Student selectedStudent = studentDao.selectStudent(insertedStudentId);
        Assert.assertTrue(selectedStudent == null);

    }
    
}

