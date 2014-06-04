public interface CourseDao {

    public int insertCourse(Course course);
    public Boolean updateCourse(Course course);
    public Boolean deleteCourse(Course course);
    public Course selectCourse(int courseId);
}
