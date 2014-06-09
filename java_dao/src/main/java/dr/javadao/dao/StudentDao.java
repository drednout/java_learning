package dr.javadao.dao;

import java.util.Date;

import dr.javadao.entities.Student;


public interface StudentDao {

    int insertStudent(Student student) throws DaoException;
    void updateStudent(Student student) throws DaoException;
    void deleteStudent(Student student) throws DaoException;
    Student selectStudent(int studentId) throws DaoException;
}
