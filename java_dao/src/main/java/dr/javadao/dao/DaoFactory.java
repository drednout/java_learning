package dr.javadao.dao;
// See http://javatutor.net/articles/j2ee-pattern-data-access-object
// Абстрактный класс DAO Factory
//
import java.sql.Connection;

public interface DaoFactory {

  // Здесь будет метод для каждого DAO, который может быть
  // создан. Реализовывать эти методы
  // должны конкретные генераторы.
  public StudentDao getStudentDao();
  public CourseDao getCourseDao();
}
