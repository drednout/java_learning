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
import dr.javadao.entities.Course;
import dr.javadao.entities.Course;
import dr.javadao.dao.CourseDao;
import dr.javadao.dao.DaoException;


public class CourseDaoTest {

    private static MySqlDaoFactory factory;
    private CourseDao courseDao;
    private static final String testCourseName = "test course";
    private static final String propertyFile = "test.properties";
    private static String user;
    private static String password;
    private static String url;


    public CourseDaoTest() {
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
        courseDao = factory.getCourseDao();
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

    @Test
    public void testInsert() throws DaoException, ParseException {
        Course newCourse = createOneCourse(testCourseName);
        int insertedCourseId = courseDao.insertCourse(newCourse);

        Assert.assertTrue(newCourse.getName().equals(testCourseName));

        Assert.assertNotNull(newCourse.getId());
        Assert.assertTrue(newCourse.getId() == insertedCourseId);

        Course selectedCourse = courseDao.selectCourse(insertedCourseId);

        Assert.assertTrue("Persistent course should be equal to course in memory",
                          selectedCourse.equals(newCourse));
    }
    @Test
    public void testUpdate() throws DaoException, ParseException {
        Course newCourse = createOneCourse(testCourseName);
        int insertedCourseId = courseDao.insertCourse(newCourse);
        
        String newCourseName = "vasya";

        newCourse.setName(newCourseName);
        courseDao.updateCourse(newCourse);

        Assert.assertTrue(newCourse.getName().equals(newCourseName));

        Course selectedCourse = courseDao.selectCourse(insertedCourseId);
        Assert.assertTrue("Persistent course should be equal to course in memory",
                          selectedCourse.equals(newCourse));
        Assert.assertTrue(selectedCourse.getName().equals(newCourseName));

    }
    @Test
    public void testDelete() throws DaoException, ParseException {
        Course newCourse = createOneCourse(testCourseName);
        int insertedCourseId = courseDao.insertCourse(newCourse);
        
        courseDao.deleteCourse(newCourse);
        Course selectedCourse = courseDao.selectCourse(insertedCourseId);
        Assert.assertTrue(selectedCourse == null);

    }
    
}

