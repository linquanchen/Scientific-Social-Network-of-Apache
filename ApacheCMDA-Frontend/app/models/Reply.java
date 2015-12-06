package models;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * Created by chenlinquan on 12/4/15.
 */
public class Reply {

    private long id;
    private String fromUserName;
    private long fromUserId;
    private String toUserName;
    private long toUserId;
    private String content;

    public Reply() {

    }

    public Reply(JsonNode node){
        if (node != null) {
            if (node.get("id") != null) id = node.get("id").asLong();
            if (node.get("fromUserName") != null) fromUserName = node.get("fromUserName").asText();
            if (node.get("fromUserId") != null) fromUserId = node.get("fromUserId").asLong();
            if (node.get("toUserName") != null) toUserName = node.get("toUserName").asText();
            if (node.get("toUserId") != null) toUserId = node.get("toUserId").asLong();
            if (node.get("content") != null) content = node.get("content").asText();
        }
    }

    public long getId() {
        return id;
    }

    public String getFromUserName() {
        return fromUserName;
    }

    public void setFromUserName(String fromUserName) {
        this.fromUserName = fromUserName;
    }

    public long getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(long fromUserId) {
        this.fromUserId = fromUserId;
    }

    public String getToUserName() {
        return toUserName;
    }

    public void setToUserName(String toUserName) {
        this.toUserName = toUserName;
    }

    public long getToUserId() {
        return toUserId;
    }

    public void setToUserId(long toUserId) {
        this.toUserId = toUserId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
