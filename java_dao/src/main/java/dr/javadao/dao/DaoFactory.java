package dr.javadao.dao;
// See http://javatutor.net/articles/j2ee-pattern-data-access-object
// Абстрактный класс DAO Factory
//
import java.sql.Connection;

public interface DaoFactory {

  public Connection getConnection() throws DaoException;
  // Здесь будет метод для каждого DAO, который может быть
  // создан. Реализовывать эти методы
  // должны конкретные генераторы.
  public StudentDao getStudentDao(Connection connection);
  public CourseDao getCourseDao(Connection connection);
}
