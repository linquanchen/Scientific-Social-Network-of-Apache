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

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
public class GroupUsers {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
    private long creatorUser;
	private String groupName;
	private String groupDescription;
	private String groupUrl;

	@ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE})
	@JoinTable(name = "GoupAndGroupmembers", joinColumns = { @JoinColumn(name ="groupId", referencedColumnName = "id")}, inverseJoinColumns = { @JoinColumn(name = "member", referencedColumnName = "id") })
	private List<User> groupMembers;

	public GroupUsers() {
	}

	public GroupUsers(long creatorUser, String groupName, String groupDescription, List<User> groupMembers) {
		super();
        this.creatorUser = creatorUser;
		this.groupName = groupName;
		this.groupDescription = groupDescription;
		this.groupUrl = UUID.randomUUID().toString();
		this.groupMembers = groupMembers;
	}

    public long getId() {return id;}

	public long getCreatorUser() {
        return creatorUser;
    }

    public void setCreatorUser(long creatorUser) {
        this.creatorUser = creatorUser;
    }

    public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getGroupDescription() {
		return groupDescription;
	}

	public void setGroupDescription(String groupDescription) {
		this.groupDescription = groupDescription;
	}

	public String getGroupUrl() {
		return groupUrl;
	}

	public List<User> getGroupMembers() {
		return groupMembers;
	}

	public void setGroupMembers(List<User> groupMembers) {
		this.groupMembers = groupMembers;
	}
	@Override
	public String toString() {
		return "GroupUsers [id=" + id + ", creatorUser=" + creatorUser + ", groupName=" + groupName + ", groupDescription=" + groupDescription
				+ ", groupUrl=" + groupUrl + ", groupMembers=" + groupMembers +"]";
	}
}

