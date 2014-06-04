import java.io.Serializable;
import java.util.Date;

public class Course implements Identified<Integer> {

    private Integer id = null;
    private String name;
    private Integer duration;
    private Date created;
    private Date updated;

    public Integer getId() {
        return id;
    }

    protected void setId(int id) {
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
}
