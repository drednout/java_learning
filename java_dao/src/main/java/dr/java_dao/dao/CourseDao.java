package dr.java_dao.dao;

import dr.java_dao.entities.Course;


public interface CourseDao {

    public int insertCourse(Course course);
    public Boolean updateCourse(Course course);
    public Boolean deleteCourse(Course course);
    public Course selectCourse(int courseId);
}
