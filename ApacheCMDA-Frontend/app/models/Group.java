package models;

/**
 * Created by stain on 12/4/2015.
 */

import com.fasterxml.jackson.databind.JsonNode;

public class Group {
    public Long getId() {
        return id;
    }

    public Long getCreatorUser() {
        return creatorUser;
    }

    public String getGroupName() {
        return groupName;
    }

    public String getGroupDescription() {
        return groupDescription;
    }

    public String getGroupUrl() {
        return groupUrl;
    }

    public Long id;
    public Long creatorUser;
    public String groupName;
    public String groupDescription;
    public String groupUrl;

    public Group() {}
    public Group(JsonNode node)
    {
        if (node.get("id")!=null) id = node.get("id").asLong();
        if (node.get("creatorUser")!=null) creatorUser = node.get("creatorUser").asLong();
        if (node.get("groupName")!=null) groupName = node.get("groupName").asText();
        if (node.get("groupDescription")!=null) groupDescription = node.get("groupDescription").asText();
        if (node.get("groupUrl")!=null) groupUrl = node.get("groupUrl").asText();
    }
}
