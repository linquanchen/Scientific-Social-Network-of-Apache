/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package models;

import com.fasterxml.jackson.databind.JsonNode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;


@Entity
public class Workflow {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private long userID;
	private String userName;
	private String wfTitle;
	private String wfCategory;
	private String wfCode;
	@Column( length = 100000 )
	private String wfDesc;
	private String wfImg;
	private String wfVisibility;
	private String status;
	private long   viewCount;
    private long   groupId;
    private boolean edit;
    private String wfUrl;
	private String wfInput;
	private String wfOutput;
	private Date wfDate;

	@OneToMany(fetch = FetchType.EAGER)
	@JoinColumn(name = "CommentId", referencedColumnName = "id")
	private List<Comment> comments;

	@ManyToOne(optional = false)
	@JoinColumn(name = "creatorId", referencedColumnName = "id")
	private User user;

	@ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE})
	@JoinTable(name = "WorkflowAndContributors", joinColumns = { @JoinColumn(name ="workflowId", referencedColumnName = "id")}, inverseJoinColumns = { @JoinColumn(name = "contributorId", referencedColumnName = "id") })
	private List<User> wfContributors;

	@ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE})
	@JoinTable(name = "WorkflowAndRelated", joinColumns = { @JoinColumn(name ="workflowId", referencedColumnName = "id")}, inverseJoinColumns = { @JoinColumn(name = "relatedId", referencedColumnName = "id") })
	private List<Workflow> wfRelated;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "WorkflowAndTags", joinColumns = { @JoinColumn(name ="workflowId", referencedColumnName = "id")}, inverseJoinColumns = { @JoinColumn(name = "tagId", referencedColumnName = "id") })
	private Set<Tag> tags;

	public Workflow() {
	}

	public Workflow(long userID, String wfTitle, String wfCategory, String wfCode,
					String wfDesc, String wfImg, String wfVisibility,
					User user, List<User> wfContributors, List<Workflow> wfRelated,
					String status, long groupId, String userName, String wfUrl, String wfInput, String wfOutput, Date wfDate) {
		super();
		this.userID = userID;
		this.wfTitle = wfTitle;
		this.wfCategory = wfCategory;
		this.wfCode = wfCode;
		this.wfDesc = wfDesc;
		this.wfImg = wfImg;
		this.wfVisibility = wfVisibility;
		this.user = user;
		this.wfContributors = wfContributors;
		this.wfRelated = wfRelated;
		this.status = status;
		this.viewCount = 0;
        this.groupId = groupId;
        this.edit = false;
		this.comments = new ArrayList<>();
		this.userName = userName;
        this.wfUrl = wfUrl;
		this.wfInput = wfInput;
		this.wfOutput = wfOutput;
		this.wfDate = wfDate;
	}

	public Workflow(JsonNode node) {
		if (node.get("userID")!=null) userID = node.get("userID").asLong();
		if (node.get("wfTitle")!=null) wfTitle = node.get("wfTitle").asText();
		if (node.get("wfCode")!=null) wfCode = node.get("wfCode").asText();
		if (node.get("wfDesc")!=null) wfDesc = node.get("wfDesc").asText();
		if (node.get("wfImg")!=null) wfImg = node.get("wfImg").asText();
		if (node.get("wfCategory")!=null) wfCategory = node.get("wfCategory").asText();
		if (node.get("wfVisibility")!=null) wfVisibility = node.get("wfVisibility").asText();
		if (node.get("wfUrl")!=null) wfUrl = node.get("wfUrl").asText();
		if (node.get("wfGroupId")!=null) groupId = node.get("wfGroupId").asLong();
		if (node.get("wfInput")!=null) wfInput = node.get("wfInput").asText();
		if (node.get("wfOutput")!=null) wfOutput = node.get("wfOutput").asText();
		wfDate = new Date();
	}


	public Set<Tag> getTags() {return this.tags;}

	public void setTags(Set<Tag> tags) {this.tags = tags;}

	public List<Comment> getComments(){ return this.comments; }

	public void setComments(List<Comment> comments){ this.comments = comments; }

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

	public List<User> getWfContributors() {
		return wfContributors;
	}

	public void setWfContributors(List<User> wfContributors) {
		this.wfContributors = wfContributors;
	}

	public List<Workflow> getWfRelated() {
		return wfRelated;
	}

	public void setWfRelated(List<Workflow> wfRelated) {
		this.wfRelated = wfRelated;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public long getUserID() {
		return userID;
	}

	public void setUserID(long userID) {
		this.userID = userID;
	}

	public String getWfTitle() {
		return wfTitle;
	}

	public void setWfTitle(String wfTitle) {
		this.wfTitle = wfTitle;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public long getId() {
		return id;
	}

	public void setViewCount() {viewCount++;}

	public long getViewCount() {return viewCount;}

    public void setGroupId(long groupId) {this.groupId = groupId;}

    public long getGroupId() {return groupId;}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

    public void setEdit(boolean edit) {this.edit = edit;}

    public boolean getEdit() {return edit;}

    public String getWfUrl() {
        return wfUrl;
    }

    public void setWfUrl(String wfUrl) {
        this.wfUrl = wfUrl;
    }

	public String getWfInput() {
		return wfInput;
	}

	public void setWfInput(String wfInput) {
		this.wfInput = wfInput;
	}

	public String getWfOutput() {
		return wfOutput;
	}

	public void setWfOutput(String wfOutput) {
		this.wfOutput = wfOutput;
	}

	public Date getWfDate() {
		return wfDate;
	}

	public void setWfDate(Date wfDate) {
		this.wfDate = wfDate;
	}

	@Override
	public String toString() {
		return "Workflow [id=" + id + ", userID=" + userID + ", wfTitle=" + wfTitle
				+ ", wfCategory=" + wfCategory + ", wfCode=" + wfCode
				+ ", wfDesc=" + wfDesc + ", wfImg=" + wfImg + ", wfVisibility" + wfVisibility
				+ ", user=" + user + ", wfContributors=" + wfContributors + ", wfRelated=" + wfRelated + ", viewCount="
                + viewCount + ", groupId=" + groupId + ", userName=" + userName + ", edit=" + edit + ", wfUrl=" + wfUrl
				+ ", wfInput=" + wfInput  + ", wfOutput=" + wfOutput + "]";

	}

}