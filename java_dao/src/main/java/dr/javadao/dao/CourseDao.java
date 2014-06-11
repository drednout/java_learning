package dr.javadao.dao;

import dr.javadao.entities.Course;


public interface CourseDao extends AutoCloseable {

    public int insertCourse(Course course) throws DaoException;
    public void updateCourse(Course course) throws DaoException;
    public void deleteCourse(Course course) throws DaoException;
    public Course selectCourse(int courseId) throws DaoException;
    void close() throws Exception;
}
