package dr.javadao.dao.impl.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.Date;
import java.util.List;
import java.util.LinkedList;

import dr.javadao.dao.DaoException;
import dr.javadao.dao.DaoFactory;
import dr.javadao.dao.CourseDao;
import dr.javadao.entities.Course;


public class MySqlCourseDao implements
        CourseDao, AutoCloseable {

    private Connection connection;
    private PreparedStatement insertStatement = null;
    private PreparedStatement updateStatement = null;
    private PreparedStatement deleteStatement = null;
    private PreparedStatement selectOneStatement = null;
    private Boolean isClosed = false;

    private String getSelectQuery() {
        return "SELECT id, name, duration, created, updated FROM "
               + "courses WHERE id=?";
    }

    private String getCreateQuery() {
        return "INSERT INTO courses (name, duration, created, updated)"
               + " VALUES (?, ?, NOW(), NOW()) ;";
    }

    private String getUpdateQuery() {
        return "UPDATE courses \n"
               + "SET name=?, duration=?, updated=NOW() \n"
               + "WHERE id=? ;";
    }

    private String getDeleteQuery() {
        return "DELETE FROM courses WHERE id=? ;";
    }

    private List<Course> parseResultSet(ResultSet rs) throws DaoException {
        LinkedList<Course> result = new LinkedList<Course>();
        try {
            while (rs.next()) {
                Course course = new Course();
                course.setId(rs.getInt("id"));
                course.setName(rs.getString("name"));
                course.setDuration(rs.getInt("duration"));
                course.setCreated(rs.getDate("created"));
                course.setUpdated(rs.getDate("updated"));
                result.add(course);
            }
        } catch (Exception e) {
            throw new DaoException(e);
        }
        return result;
    }

    

    private void prepareStatementForInsert(PreparedStatement statement, 
            Course object) throws DaoException {
        try {
            statement.setString(1, object.getName());
            statement.setInt(2, object.getDuration());
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    private void prepareStatementForUpdate(PreparedStatement statement, 
            Course object) throws DaoException {
        try {
            if (object.getId() == null) {
                throw new DaoException("Course id is null");
            }
            statement.setString(1, object.getName());
            statement.setInt(2, object.getDuration());
            statement.setLong(3, object.getId());

        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    private void prepareStatementForDelete(PreparedStatement statement, 
            Course object) throws DaoException {
        try {
            if (object.getId() == null) {
                throw new DaoException("Course id is null");
            }
            statement.setInt(1, object.getId());
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    public MySqlCourseDao(Connection connection) throws DaoException {
        this.connection = connection;
        try {
            insertStatement = connection.prepareStatement(getCreateQuery(), 
                                    Statement.RETURN_GENERATED_KEYS);
            updateStatement = connection.prepareStatement(getUpdateQuery());
            deleteStatement = connection.prepareStatement(getDeleteQuery());
            selectOneStatement = connection.prepareStatement(getSelectQuery());
        } catch (Exception e) {
            releaseStatements();
            throw new DaoException(e);
        }
    }

    public int insertCourse(Course course) throws DaoException {
        Integer courseId = -1;
        ResultSet generatedKeys = null;

        try {
            prepareStatementForInsert(insertStatement, course);
            insertStatement.executeUpdate();

            generatedKeys = insertStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                courseId = generatedKeys.getInt(1);
                course.setId(courseId);
            }
        } catch (Exception e) {
            throw new DaoException(e);
        } finally {
            try {
                if (generatedKeys != null) {
                    generatedKeys.close();
                }
            } catch (Exception e) {
                throw new DaoException(e);
            }
        }

        return courseId;
    }
    public void updateCourse(Course course) throws DaoException {
        String sql = getUpdateQuery();

        try {
            prepareStatementForUpdate(updateStatement, course);
            updateStatement.executeUpdate();
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }
    public void deleteCourse(Course course) throws DaoException {
        String sql = getDeleteQuery();

        try {
            prepareStatementForDelete(deleteStatement, course);
            deleteStatement.executeUpdate();
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }
    public Course selectCourse(int courseId) throws DaoException {
        List<Course> list;
        ResultSet rs = null;
        try {
            selectOneStatement.setInt(1, courseId);
            rs = selectOneStatement.executeQuery();
            list = parseResultSet(rs);
        } catch (Exception e) {
            throw new DaoException(e);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (Exception e) {
                throw new DaoException(e);
            }
        }

        if (list == null || list.size() == 0) {
            return null;
        }
        if (list.size() > 1) {
            //нужна ли эта проверка? очень похожа по сути на 2x2 == 4
            throw new DaoException("Broken primary key for course " + courseId);
        }
        return list.iterator().next();
    }

    private void releaseStatements() throws DaoException {
        LinkedList<Exception> exceptions = new LinkedList<Exception>();
        try {
            if (insertStatement != null) insertStatement.close();
        } catch(Exception e) {
            exceptions.add(e);
        }
        try {
            if (updateStatement != null) updateStatement.close();
        } catch(Exception e) {
            exceptions.add(e);
        }
        try {
            if (deleteStatement != null) deleteStatement.close();
        } catch(Exception e) {
            exceptions.add(e);
        }
        try {
            if (selectOneStatement != null) selectOneStatement.close();
        } catch(Exception e) {
            exceptions.add(e);
        }

        if (exceptions.size() > 0) {
            //here we loose some information about all exceptions, but
            //all resources are released:)
            throw new DaoException(exceptions.iterator().next());
        }
    }


    public void close() throws Exception {
        if (isClosed) return;
        
        releaseStatements();
    }
}
