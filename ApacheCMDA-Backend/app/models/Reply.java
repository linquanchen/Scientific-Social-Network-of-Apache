package models;

import javax.persistence.*;

/**
 * Created by baishi on 11/24/15.
 */
@Entity
public class Reply implements Comparable<Reply>{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private boolean status;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fromId", referencedColumnName = "id")
    private User fromUser;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "toId", referencedColumnName = "id")
    private User toUser;
    private long timestamp;
    private String content;

    public Reply(){

    }

    public Reply(User fromUser, User toUser, long timestamp, String content){
        this.status = true;
        this.fromUser = fromUser;
        this.toUser = toUser;
        this.timestamp = timestamp;
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public long getId() {
        return id;
    }

    public User getFromUser() {
        return fromUser;
    }

    public User getToUser() {
        return toUser;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setFromUser(User fromUser) {
        this.fromUser = fromUser;
    }

    public void setToUser(User toUser) {
        this.toUser = toUser;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString(){
        return "Reply: [id="+this.id+", fromUserName="+this.fromUser.getUserName()+
                ", fromUserId="+this.fromUser.getId()+", toUserName="+this.toUser.getUserName()+
                ", toUserId="+this.toUser.getId()+", Content="+this.content+"]";
    }

    @Override
    public int compareTo(Reply o) {
        if(this.id>o.id)return 1;
        else return -1;
    }
}