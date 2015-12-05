package models;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * Created by chenlinquan on 12/4/15.
 */
public class Comment {
    private long id;
    private long user;
    private long timestamp;
    private String content;

    public Comment(JsonNode node) {
        if (node.get("id") != null) id = node.get("id").asLong();
        if (node.get("user") != null) user = node.get("user").asLong();
        if (node.get("timestamp") != null) timestamp = node.get("timestamp").asLong();
        if (node.get("content") != null) content = node.get("content").asText();
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
