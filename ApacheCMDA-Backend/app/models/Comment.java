package models;

import javax.persistence.*;

/**
 * Created by baishi on 12/3/15.
 */
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private boolean status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "creatorId", referencedColumnName = "id")
    private User user;
    private long timestamp;
    private String content;

    private String commentImage;

    public Comment(){

    }

    public Comment(User user, long timestamp, String content, String commentImage){
        this.status = true;
        this.user = user;
        this.timestamp = timestamp;
        this.content = content;
        this.commentImage = commentImage;
    }

    public long getId() {
        return id;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCommentImage() {
        return commentImage;
    }

    public void setCommentImage(String commentImage) {
        this.commentImage = commentImage;
    }

    @Override
    public String toString() {
        return "Comments [id="+id+", user="+user.getId()+", timestamp="+timestamp+", content="+content+"]";
    }
}
