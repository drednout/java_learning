package dr.javadao.dao.impl.mysql;

//see http://www.dokwork.ru/2014/02/daotalk.html
import java.sql.Connection;
import java.sql.DriverManager;

import dr.javadao.dao.DaoException;
import dr.javadao.dao.DaoFactory;
import dr.javadao.dao.StudentDao;
import dr.javadao.dao.CourseDao;
import dr.javadao.dao.StudentCourseDao;

public class MySqlDaoFactory implements DaoFactory, AutoCloseable  {
    //TODO: get connection credentials from config

    private String user = null;
    private String password = null;
    private String url = null;
    private String driver = "com.mysql.jdbc.Driver";
    private Connection connection = null;
    private Boolean doRollbackWhenClosing = false;
    private Boolean isOpened = false;

    public MySqlDaoFactory(String user, String password, String url) 
            throws DaoException {
        this.user = user;
        this.password = password;
        this.url = url;
        this.connection = getConnection();
    }

    private Connection getConnection() throws DaoException {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            throw new DaoException(e);
        }
        isOpened = true;

        return connection;
    }

    @Override
    public StudentDao getStudentDao() throws DaoException {
        return new MySqlStudentDao(connection);
    }

    @Override
    public CourseDao getCourseDao() throws DaoException {
        return new MySqlCourseDao(connection);
    }

    @Override
    public StudentCourseDao getStudentCourseDao() throws DaoException {
        return new MySqlStudentCourseDao(connection);
    }

    public MySqlDaoFactory() {
        try {
            Class.forName(driver); //Регистрируем драйвер
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void setAutoCommit(Boolean flag) throws DaoException {
        try {
            connection.setAutoCommit(flag);
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    public void setDoRollbackWhenClosing(Boolean flag) {
        doRollbackWhenClosing = flag;
    }


    public void close() throws Exception {
        if (doRollbackWhenClosing) {
            connection.rollback();
        }
        if (isOpened) {
            connection.close();
            isOpened = false;
        }
    }
}
