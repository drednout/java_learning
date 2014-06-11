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
import dr.javadao.dao.StudentCourseDao;
import dr.javadao.entities.Student;
import dr.javadao.entities.Course;

public class MySqlStudentCourseDao implements
        StudentCourseDao, AutoCloseable {
    private Connection connection;
    private PreparedStatement studentCoursesStatement = null;
    private PreparedStatement courseStudentsStatement = null;
    private PreparedStatement hasStudentCourseStatement = null;
    private PreparedStatement insertStudentCourseStatement = null;
    private PreparedStatement deleteStudentCourseStatement = null;

    private Boolean isClosed = false;

    private String getHasStudentCourseQuery() {
        return "SELECT COUNT(id) AS courses_count FROM student_courses WHERE "
               + "student_id=? AND course_id=?";
    }

    private String getInsertStudentCourseQuery() {
        return "INSERT INTO student_courses (student_id, course_id, created) "
               + " VALUES(?, ?, NOW())";
    }

    private String getDeleteStudentCourseQuery() {
        return "DELETE FROM student_courses WHERE student_id=? AND course_id=?";
    }

    private String getSelectStudentCoursesQuery () {
        return "SELECT c.id, c.name, c.duration, c.created, c.updated FROM " 
               + "student_courses sc LEFT JOIN courses c ON "
               + " sc.course_id=c.id WHERE sc.student_id=?";

    }

    private String getSelectCourseStudentsQuery () {
        return "SELECT s.id, s.name, s.sex, s.birth_date, s.created, s.updated FROM " 
               + "student_courses sc LEFT JOIN students s ON "
               + " sc.student_id=s.id WHERE sc.course_id=?";

    }

    private List<Course> parseCourseResultSet(ResultSet rs) throws DaoException {
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
    private List<Student> parseStudentResultSet(ResultSet rs) throws DaoException {
        LinkedList<Student> result = new LinkedList<Student>();
        try {
            while (rs.next()) {
                Student student = new Student();
                student.setId(rs.getInt("id"));
                student.setName(rs.getString("name"));
                student.setSex(rs.getString("sex"));
                student.setBirthDate(rs.getDate("birth_date"));
                student.setCreated(rs.getDate("created"));
                student.setUpdated(rs.getDate("updated"));
                result.add(student);
            }
        } catch (Exception e) {
            throw new DaoException(e);
        }
        return result;
    }


    public List<Course> getStudentCourses(Student student) throws DaoException {
        List<Course> studentCourses;
        ResultSet rs = null;

        try { 
            studentCoursesStatement.setInt(1, student.getId());
            rs = studentCoursesStatement.executeQuery();
            studentCourses = parseCourseResultSet(rs);
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

        return studentCourses;
    }

    public MySqlStudentCourseDao(Connection connection) throws DaoException {
        this.connection = connection;
        try {
            studentCoursesStatement = connection.prepareStatement(getSelectStudentCoursesQuery());
            insertStudentCourseStatement = connection.prepareStatement(getInsertStudentCourseQuery());
            deleteStudentCourseStatement = connection.prepareStatement(getDeleteStudentCourseQuery());
            hasStudentCourseStatement = connection.prepareStatement(getHasStudentCourseQuery());
            courseStudentsStatement = connection.prepareStatement(getSelectCourseStudentsQuery());
        } catch (Exception e) {
            releaseStatements();
            throw new DaoException(e);
        }
    }

    private void releaseStatements() throws DaoException {
        LinkedList<Exception> exceptions = new LinkedList<Exception>();
        try {
            if (courseStudentsStatement != null) courseStudentsStatement.close();
        } catch(Exception e) {
            exceptions.add(e);
        }
        try {
            if (studentCoursesStatement != null) studentCoursesStatement.close();
        } catch(Exception e) {
            exceptions.add(e);
        }
        try {
            if (insertStudentCourseStatement != null) insertStudentCourseStatement.close();
        } catch(Exception e) {
            exceptions.add(e);
        }
        try {
            if (deleteStudentCourseStatement != null) deleteStudentCourseStatement.close();
        } catch(Exception e) {
            exceptions.add(e);
        }
        try {
            if (hasStudentCourseStatement != null) hasStudentCourseStatement.close();
        } catch(Exception e) { 
            exceptions.add(e);
        }

        if (exceptions.size() > 0) {
            //here we loose some information about all exceptions, but
            //all resources are released:)
            throw new DaoException(exceptions.iterator().next());
        }
    }
    public Boolean hasStudentCourse(Student student, Course course) throws DaoException {
        ResultSet rs = null;

        try { 
            hasStudentCourseStatement.setInt(1, student.getId());
            hasStudentCourseStatement.setInt(2, course.getId());
            rs = hasStudentCourseStatement.executeQuery();
            rs.first();
            int size = rs.getInt("courses_count");
            if (size > 0)
                return true; 
            else 
                return false;

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
    }
    
    public Boolean joinStudentCourse(Student student, Course course) throws DaoException {
        if (hasStudentCourse(student, course)) {
            return false;
        }
        try {
            prepareStatementForInsert(insertStudentCourseStatement, student, course);
            insertStudentCourseStatement.executeUpdate();
        } catch (Exception e) {
            throw new DaoException(e);
        }
        return true;

    }
    public List<Student> getCourseStudents(Course course) throws DaoException {
        List<Student> courseStudents;
        ResultSet rs = null;

        try { 
            courseStudentsStatement.setInt(1, course.getId());
            rs = courseStudentsStatement.executeQuery();
            courseStudents = parseStudentResultSet(rs);
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
        return courseStudents;
    }

    private void prepareStatementForDelete(PreparedStatement statement, 
            Student student, Course course) throws DaoException {
        try {
            if (student.getId() == null) {
                throw new DaoException("Student id is null");
            }
            if (course.getId() == null) {
                throw new DaoException("Course id is null");
            }
            statement.setInt(1, student.getId());
            statement.setInt(1, course.getId());
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }
    private void prepareStatementForInsert(PreparedStatement statement, 
            Student student, Course course) throws DaoException {
        try {
            if (student.getId() == null) {
                throw new DaoException("Student id is null");
            }
            if (course.getId() == null) {
                throw new DaoException("Course id is null");
            }
            statement.setInt(1, student.getId());
            statement.setInt(2, course.getId());
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }


    public Boolean deleteStudentCourse(Student student, Course course) throws DaoException {
        if (hasStudentCourse(student, course)) {
            return false;
        }
        try {
            prepareStatementForDelete(deleteStudentCourseStatement, student, course);
            deleteStudentCourseStatement.executeUpdate();
        } catch (Exception e) {
            throw new DaoException(e);
        }
        return true;
    }

    public void close() throws Exception {
        if (isClosed) return;
        
        releaseStatements();
    }
}
