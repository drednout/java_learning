package dr.java_dao.mysql;

//see http://www.dokwork.ru/2014/02/daotalk.html
import java.sql.Connection;
import java.sql.DriverManager;

import dr.java_dao.dao.DaoException;
import dr.java_dao.dao.DaoFactory;
import dr.java_dao.dao.StudentDao;
import dr.java_dao.dao.CourseDao;

public class MySqlDaoFactory implements DaoFactory {
    //TODO: get connection credentials from config

    private String user = null;
    private String password = null;
    private String url = null;
    private String driver = "com.mysql.jdbc.Driver";

    public MySqlDaoFactory(String user, String password, String url) {
        this.user = user;
        this.password = password;
        this.url = url;
    }

    public Connection getConnection() throws DaoException {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            throw new DaoException(e);
        }
        return connection;
    }

    @Override
    public StudentDao getStudentDao(Connection connection) {
        return new MySqlStudentDao(connection);
    }

    @Override
    public CourseDao getCourseDao(Connection connection) {
        //TODO: implementation
        return null;
    }

    public MySqlDaoFactory() {
        try {
            Class.forName(driver); //Регистрируем драйвер
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
