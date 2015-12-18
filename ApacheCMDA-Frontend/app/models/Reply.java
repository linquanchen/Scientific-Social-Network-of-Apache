package models;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import util.APICall;
import util.Constants;

/**
 * Created by chenlinquan on 12/4/15.
 */
public class Reply {
    private final static String CREATE = Constants.NEW_BACKEND + "Comment/addReply";
    private final static String CREATE_REPLY = Constants.NEW_BACKEND + "Comment/replyReply";

    private long id;
    private long timestamp;
    private long fromUserId;
    private String fromUserName;
    private long toUserId;
    private String toUserName;
    private String content;

    public Reply() {

    }

    public Reply(JsonNode node){
        if (node != null) {
            if (node.get("id") != null) id = node.get("id").asLong();
            if (node.get("timestamp") != null) timestamp = node.get("timestamp").asLong();
            if (node.get("fromUser") != null) {
                fromUserId = node.get("fromUser").get("id").asLong();
                fromUserName = node.get("fromUser").get("userName").asText();
            }
            if (node.get("toUser") != null) {
                toUserId = node.get("toUser").get("id").asLong();
                toUserName = node.get("toUser").get("userName").asText();
            }
            if (node.get("content") != null) content = node.get("content").asText();
        }
    }

    public static JsonNode create(ObjectNode node) {
        JsonNode response = APICall.postAPI(CREATE, node);
        return response;
    }

    public static JsonNode createReply(ObjectNode node) {
        JsonNode response = APICall.postAPI(CREATE_REPLY, node);
        return response;
    }

    public long getId() {
        return id;
    }

    public long getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(long fromUserId) {
        this.fromUserId = fromUserId;
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

    public String getFromUserName() {
        return fromUserName;
    }

    public void setFromUserName(String fromUserName) {
        this.fromUserName = fromUserName;
    }

    public String getToUserName() {
        return toUserName;
    }

    public void setToUserName(String toUserName) {
        this.toUserName = toUserName;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
