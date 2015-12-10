package models;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import util.APICall;
import util.Constants;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Created by gavin on 11/24/15.
 */
public class Workflow {

    private final static String CREATE = Constants.NEW_BACKEND + "workflow/post";
    private final static String EDIT = Constants.NEW_BACKEND + "workflow/updateWorkflow";

    private long id = (-1);
    private String userName = "NaN";
    private long UserId = (-1);

    private String wfTitle = "NaN";
    private String wfCategory = "NaN";
    private String wfCode = "NaN";
    private String wfDesc = "NaN";
    private String wfImg = "NaN";
    private String wfVisibility = "NaN";
    private String wfTag = "";
    private long [] wfContributors = {-1};
    private long [] wfRelated = {-1};
    private long wfViewCount = 0;
    private String wfUrl = "NaN";
    private List<String> tags = new ArrayList<String>();
    private String wfDate = "NaN";
    private String wfStatus = "norm";
    private String avatar;

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public void setWfEdit(boolean wfEdit) {
        this.wfEdit = wfEdit;
    }
    private boolean wfEdit = false;
    private List<String> wfInput = new ArrayList<>();
    private List<String> wfOutput = new ArrayList<>();

    public Workflow() {
    }

    public Workflow(JsonNode node) {
        if (node.get("id")!=null) id = node.get("id").asLong();
        if (node.get("user")!=null) {
            JsonNode nameNode = node.get("user");
            if (nameNode.get("userName") != null) {
                userName = nameNode.get("userName").asText();
            }
            if (nameNode.get("id") != null) {
                UserId = nameNode.get("id").asLong();
            }
        }
        if (node.get("UserId")!=null) UserId = node.get("UserId").asLong();
        if (node.get("wfTitle")!=null) wfTitle = node.get("wfTitle").asText();
        if (node.get("wfCode")!=null) wfCode = node.get("wfCode").asText();
        if (node.get("wfDesc")!=null) wfDesc = node.get("wfDesc").asText();
        if (node.get("wfImg")!=null) wfImg = node.get("wfImg").asText();
        if (node.get("wfCategory")!=null) wfCategory = node.get("wfCategory").asText();
        if (node.get("wfVisibility")!=null) wfVisibility = node.get("wfVisibility").asText();
        if (node.get("viewCount")!=null) wfViewCount = node.get("viewCount").asLong();
        if (node.get("wfTag")!=null) wfTag = node.get("wfTag").asText();
        if (node.get("wfUrl")!=null) wfUrl = node.get("wfUrl").asText();
        if (node.get("edit")!=null) wfEdit = node.get("edit").asBoolean();
        if (node.get("wfDate")!=null) wfDate = node.get("wfDate").asText();
        if (node.get("status")!=null) wfStatus = node.get("status").asText();
        if (node.get("user")!=null && node.get("user").get("avatar")!=null) avatar = node.get("user").get("avatar").asText();

        if (node.get("wfInput") != null) {
            String inputs[] = node.get("wfInput").asText().split("\\|");
            for (String in: inputs) {
                wfInput.add(in);
            }
        }
        if (node.get("wfOutput") != null) {
            String outputs[] = node.get("wfOutput").asText().split("\\|");
            for (String in: outputs) {
                wfOutput.add(in);
            }
        }
        if (node.get("tags") !=null) {
            for (JsonNode n : node.get("tags")) {
                tags.add(n.get("tag").textValue());
            }
        }
    }

    public static JsonNode create(ObjectNode node) {
        JsonNode response = APICall.postAPI(CREATE, node);
        return response;
    }

    public static JsonNode update(ObjectNode node) {
        JsonNode response = APICall.postAPI(EDIT, node);
        return response;
    }

    public String getShortWfDesc() {
        if(wfDesc.length()>15){
            return wfDesc.substring(0,12)+"...";
        }
        else{
            return wfDesc;
        }
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public long getUserId() {
        return UserId;
    }

    public void setUserId(long userId) {
        UserId = userId;
    }

    public String getWfTitle() {
        return wfTitle;
    }

    public void setWfTitle(String wfTitle) {
        this.wfTitle = wfTitle;
    }

    public String getWfCategory() {
        return wfCategory;
    }

    public void setWfCategory(String wfCategory) {
        this.wfCategory = wfCategory;
    }

    public String getWfCode() {
        return wfCode;
    }

    public void setWfCode(String wfCode) {
        this.wfCode = wfCode;
    }

    public String getWfDesc() {
        return wfDesc;
    }

    public void setWfDesc(String wfDesc) {
        this.wfDesc = wfDesc;
    }

    public String getWfImg() {
        return wfImg;
    }

    public void setWfImg(String wfImg) {
        this.wfImg = wfImg;
    }

    public String getWfVisibility() {
        return wfVisibility;
    }

    public void setWfVisibility(String wfVisibility) {
        this.wfVisibility = wfVisibility;
    }

    public long[] getWfContributors() {
        return wfContributors;
    }

    public void setWfContributors(long[] wfContributors) {
        this.wfContributors = wfContributors;
    }

    public long[] getWfRelated() {
        return wfRelated;
    }

    public void setWfRelated(long[] wfRelated) {
        this.wfRelated = wfRelated;
    }

    public long getWfViewCount() {return wfViewCount;}

    public void setWfViewCount(long wfViewCount) {this.wfViewCount = wfViewCount;}

    public String getWfTag() {return wfTag;}

    public void setWfTag(String wfTag) {this.wfTag = wfTag;}

    public String getWfUrl() {return wfUrl;}

    public void setWfUrl(String wfUrl) {this.wfUrl = wfUrl;}

    private static String concatWithCommas(Collection<String> words) {
        StringBuilder wordList = new StringBuilder();
        for (String word : words) {
            wordList.append(word + ",");
        }
        return new String(wordList.deleteCharAt(wordList.length() - 1));
    }

    public List<String> getWfInput() {
        return wfInput;
    }

    public String getInputString() {
        return concatWithCommas(wfInput);
    }

    public String getTagsString() {return concatWithCommas(tags);}

    public void setWfInput(List<String> wfInput) {
        this.wfInput = wfInput;
    }

    public List<String> getWfOutput() {
        return wfOutput;
    }

    public String getOutputString() {
        return concatWithCommas(wfOutput);
    }

    public void setWfOutput(List<String> wfOutput) {
        this.wfOutput = wfOutput;
    }

    public boolean isWfEdit() {
        return wfEdit;
    }

    public String getWfDate() {
        return wfDate;
    }

    public void setWfDate(String wfDate) {
        this.wfDate = wfDate;
    }

    public String getWfStatus() {
        return wfStatus;
    }

    public void setWfStatus(String wfStatus) {this.wfStatus = wfStatus;}

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

}
