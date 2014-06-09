package dr.javadao.entities;

//see http://www.dokwork.ru/2014/02/daotalk.html
import java.io.Serializable;
import java.util.Date;
import dr.javadao.dao.DaoException;



/**
 * Объектное представление сущности Студент.
 */
public class Student {
    public enum Sex {
        Male, Female
    }

    private Integer id = null;
    private String name;
    private Sex sex;
    private Date birthDate;
    private Date created;
    private Date updated;

    public Student() {
    }

    public Student(Student another) {
        this.id = another.id;
        this.name = another.name;
        this.sex = another.sex;
        this.birthDate = another.birthDate;
        this.created = another.created;
        this.updated = another.updated;
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Sex getSex() {
        return sex;
    }

    public String getSexString() {
        if (sex == Sex.Male) {
            return "M";
        } else if (sex == Sex.Female) {
            return "F";
        } else {
            return null;
        }
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }
    public void setSex(String sexString) throws DaoException {
        if (sexString.length() != 1) {
            throw new DaoException("Sex string lenght should be 1");
        }
        if (sexString.equals("M") ) {
            this.sex = Sex.Male;
        } else if (sexString.equals("F") ) {
            this.sex = Sex.Female;
        } else {
            throw new DaoException("Invalid sex value: " + sexString);
        }
    }

    public Date getCreated() {
        return created; 
    }

    public void setCreated(Date d) {
        this.created = d;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date d) {
        this.updated = d;
    }

    public String toString() {
        String out = String.format("Student<name: %s, sex: %s, birthDate: %s>", 
                                   name, sex, birthDate);
        return out;
    }
    @Override
    public boolean equals(Object other){
        if (other == null) return false;
        if (other == this) return true;
        if (!(other instanceof Student)) return false;

        
        Student otherStudent = (Student)other;
        if (!otherStudent.id.equals(this.id)) return false;
        if (!otherStudent.name.equals(this.name)) return false;
        if (!otherStudent.sex.equals(this.sex)) return false;
        if (!otherStudent.birthDate.equals(this.birthDate)) return false;

        return true;
    }
}
