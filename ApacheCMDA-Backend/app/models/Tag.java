package models;

import javax.persistence.*;


@Entity
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String tag;

    public Tag() {

    }

    public Tag(String tagString) {
        super();
        this.tag = tagString;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tagString) {
        this.tag = tagString;
    }

    public long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Tag [id=" + id + ", tag=" + tag + "]";
    }

    public String toJson() {
        return "{\"Tag\":{\"id\":\"" + id + "\", \"tagString\":\"" + tag + "\"}}";
    }
}
