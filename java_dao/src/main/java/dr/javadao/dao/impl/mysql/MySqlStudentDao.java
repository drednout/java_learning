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
import dr.javadao.dao.StudentDao;
import dr.javadao.entities.Student;


public class MySqlStudentDao implements
        StudentDao {

    private Connection connection;

    private String getSelectQuery() {
        return "SELECT id, name, sex, birth_date, created, updated FROM "
               + "students ";
    }

    private String getCreateQuery() {
        return "INSERT INTO students (name, sex, birth_date, created, updated)"
               + " VALUES (?, ?, ?, NOW(), NOW()) ;";
    }

    private String getUpdateQuery() {
        return "UPDATE students \n"
               + "SET name=?, sex=?, birth_date=?, updated=NOW() \n"
               + "WHERE id=? ;";
    }

    private String getDeleteQuery() {
        return "DELETE FROM students WHERE id=? ;";
    }

    private List<Student> parseResultSet(ResultSet rs) throws DaoException {
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

    private void prepareStatementForInsert(PreparedStatement statement, 
            Student object) throws DaoException {
        try {
            //TODO: what can we do with magic numbers 1, 2, 3??
            statement.setString(1, object.getName());
            statement.setString(2, object.getSexString());

            Date sqlBirthDate = convert(object.getBirthDate());
            statement.setDate(3, sqlBirthDate);
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    private void prepareStatementForUpdate(PreparedStatement statement, 
            Student object) throws DaoException {
        try {
            if (object.getId() == null) {
                throw new DaoException("Student id is null");
            }
            statement.setString(1, object.getName());
            statement.setString(2, object.getSexString());

            Date sqlBirthDate = convert(object.getBirthDate());
            statement.setDate(3, sqlBirthDate);

            statement.setLong(4, object.getId());

        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    private void prepareStatementForDelete(PreparedStatement statement, 
            Student object) throws DaoException {
        try {
            if (object.getId() == null) {
                throw new DaoException("Student id is null");
            }
            statement.setInt(1, object.getId());
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    private java.sql.Date convert(java.util.Date date) {
        if (date == null) {
            return null;
        }
        return new java.sql.Date(date.getTime());
    }

    public MySqlStudentDao(Connection connection) {
        this.connection = connection;
    }

    public int insertStudent(Student student) throws DaoException {
        String sql = getCreateQuery();
        Integer studentId = -1;

        try (PreparedStatement statement = connection.prepareStatement(sql, 
                Statement.RETURN_GENERATED_KEYS)) {
            prepareStatementForInsert(statement, student);
            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                studentId = generatedKeys.getInt(1);
                student.setId(studentId);
            }
        } catch (Exception e) {
            throw new DaoException(e);
        }

        return studentId;
    }
    public void updateStudent(Student student) throws DaoException {
        String sql = getUpdateQuery();

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            prepareStatementForUpdate(statement, student);
            statement.executeUpdate();
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }
    public void deleteStudent(Student student) throws DaoException {
        String sql = getDeleteQuery();

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            prepareStatementForDelete(statement, student);
            statement.executeUpdate();
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }
    public Student selectStudent(int studentId) throws DaoException {
        List<Student> list;
        String sql = getSelectQuery();
        sql += " WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, studentId);
            ResultSet rs = statement.executeQuery();
            list = parseResultSet(rs);
        } catch (Exception e) {
            throw new DaoException(e);
        }
        if (list == null || list.size() == 0) {
            return null;
        }
        if (list.size() > 1) {
            //нужна ли эта проверка? очень похожа по сути на 2x2 == 4
            throw new DaoException("Broken primary key for student " + studentId);
        }
        return list.iterator().next();
    }
}
