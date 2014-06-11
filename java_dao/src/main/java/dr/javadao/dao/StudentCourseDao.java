package dr.javadao.dao;

import dr.javadao.entities.Student;
import dr.javadao.entities.Course;
import java.util.List;

public interface StudentCourseDao extends AutoCloseable {

    List<Course> getStudentCourses(Student student) throws DaoException;
    List<Student> getCourseStudents(Course course) throws DaoException;
    Boolean hasStudentCourse(Student student, Course course) throws DaoException;
    Boolean joinStudentCourse(Student student, Course course) throws DaoException;
    Boolean deleteStudentCourse(Student student, Course course)
        throws DaoException;
    void close() throws Exception;
}
