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

    private long id;
    private long timestamp;
    private long fromUserId;
    private long toUserId;
    private String content;

    public Reply() {

    }

    public Reply(JsonNode node){
        if (node != null) {
            if (node.get("id") != null) id = node.get("id").asLong();
            if (node.get("timestamp") != null) timestamp = node.get("timestamp").asLong();
            if (node.get("fromUserId") != null) fromUserId = node.get("fromUserId").asLong();
            if (node.get("toUserId") != null) toUserId = node.get("toUserId").asLong();
            if (node.get("content") != null) content = node.get("content").asText();
        }
    }

    public static JsonNode create(ObjectNode node) {
        JsonNode response = APICall.postAPI(CREATE, node);
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
}
