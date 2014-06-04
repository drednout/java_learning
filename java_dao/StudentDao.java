import java.util.Date;

public interface StudentDao {

    int insertStudent(Student student) throws DaoException;
    void updateStudent(Student student) throws DaoException;
    void deleteStudent(Student student) throws DaoException;
    Student selectStudent(int studentId) throws DaoException;
}
