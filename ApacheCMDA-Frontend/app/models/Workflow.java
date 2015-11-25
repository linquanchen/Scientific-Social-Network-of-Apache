package models;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import util.APICall;
import util.Constants;

/**
 * Created by gavin on 11/24/15.
 */
public class Workflow {

    private final static String CREATE = Constants.NEW_BACKEND + "workflow/post";

    private long id = (-1);
    private String userName = "NaN";
    private long UserId = (-1);

    private String wfTitle = "NaN";
    private String wfCategory = "NaN";
    private String wfCode = "NaN";
    private String wfDesc = "NaN";
    private String wfImg = "NaN";
    private String wfVisibility = "NaN";
    private long [] wfContributors = {-1};
    private long [] wfRelated = {-1};

    public Workflow() {
    }

    public static JsonNode create(ObjectNode node) {
        JsonNode response = APICall.postAPI(CREATE, node);
        return response;
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


}
