package models;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import util.APICall;
import util.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gavin on 11/24/15.
 */
public class Suggestion {

    private final static String CREATE_SUGGESTION = Constants.NEW_BACKEND + "suggestion/publishSuggestion";
    private final static String ADD_TAG = Constants.NEW_BACKEND + "suggestion/addTag";

    private long id = (-1);
    private String sContent = "NaN";
    private List<String> sTag = new ArrayList<>();
    private int sVote = 0;
    private long wfId  = (-1);

    public Suggestion() {
    }

    public Suggestion(JsonNode node) {
        if (node.get("id")!=null) id = node.get("id").asLong();
        if (node.get("suggestionWorkflows")!=null) {
            JsonNode wfNode = node.get("suggestionWorkflows");
            if (wfNode.get("id") != null) {
                wfId = wfNode.get("id").asLong();
            }
        }
        if (node.get("suggestionContent")!=null) sContent = node.get("suggestionContent").asText();
        if (node.get("suggestionTag")!=null) {
            String[] tags = node.get("suggestionTag").asText().split("\\|");
            for (String tag: tags) {
                sTag.add(tag);
            }
        }
        if (node.get("suggestionVotes")!=null) sVote = node.get("suggestionVotes").asInt();
    }

    public static JsonNode createSuggestion(ObjectNode node) {
        JsonNode response = APICall.postAPI(CREATE_SUGGESTION, node);
        return response;
    }

    public static JsonNode addTagToSuggestion(ObjectNode node) {
        JsonNode response = APICall.postAPI(ADD_TAG, node);
        return response;
    }

    public int getsVote() {
        return sVote;
    }

    public void setsVote(int sVote) {
        this.sVote = sVote;
    }

    public long getWfId() {
        return wfId;
    }

    public void setWfId(long wfId) {
        this.wfId = wfId;
    }

    public List<String> getsTag() {
        return sTag;
    }

    public void setsTag(List<String> sTag) {
        this.sTag = sTag;
    }

    public String getsContent() {
        return sContent;
    }

    public void setsContent(String sContent) {
        this.sContent = sContent;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


}
