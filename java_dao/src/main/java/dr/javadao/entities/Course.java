package dr.javadao.entities;

import java.io.Serializable;
import java.util.Date;

public class Course {

    private Integer id = null;
    private String name;
    private Integer duration;
    private Date created;
    private Date updated;

    public Course() {
    }

    public Course(Course another) {
        this.id = another.id;
        this.name = another.name;
        this.duration = another.duration;
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


    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
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
        String out = String.format("Course<name: %s, duration: %s>", 
                                   name, duration);
        return out;
    }
    @Override
    public boolean equals(Object other){
        if (other == null) return false;
        if (other == this) return true;
        if (!(other instanceof Course)) return false;

        Course otherCourse = (Course)other;
        if (!otherCourse.id.equals(this.id)) return false;
        if (!otherCourse.name.equals(this.name)) return false;
        if (!otherCourse.duration.equals(this.duration)) return false;

        return true;
    }
}
