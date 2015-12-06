package models;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import util.APICall;
import util.Constants;

/**
 * Created by chenlinquan on 12/4/15.
 */
public class Comment {
    private final static String CREATE = Constants.NEW_BACKEND + "workflow/addComment";

    private long id;
    private long user;
    private long timestamp;
    private String content;

    public Comment() {

    }

    public Comment(JsonNode node) {
        if (node != null) {
            if (node.get("id") != null) id = node.get("id").asLong();
            if (node.get("user") != null) user = node.get("user").asLong();
            if (node.get("timestamp") != null) timestamp = node.get("timestamp").asLong();
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
}
