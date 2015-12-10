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
import java.util.Set;

@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private boolean status;
	private String userName;
	private String password;
	private String email;
	private String phoneNumber;
	private String avatar;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(	name = "Followers",
				joinColumns = { @JoinColumn(name ="userId", referencedColumnName = "id")},
				inverseJoinColumns = { @JoinColumn(name = "followerId", referencedColumnName = "id") })
	protected Set<User> followers;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(	name = "friendRequests",
			joinColumns = { @JoinColumn(name ="userId", referencedColumnName = "id")},
			inverseJoinColumns = { @JoinColumn(name = "senderId", referencedColumnName = "id") })
	protected Set<User> friendRequestSender;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(	name = "friendship",
			joinColumns = { @JoinColumn(name ="userAId", referencedColumnName = "id")},
			inverseJoinColumns = { @JoinColumn(name = "userBId", referencedColumnName = "id") })
	protected Set<User> friends;

//	private String middleInitial;
//	private String affiliation;
//	private String title;
//	private String mailingAddress;
//	private String faxNumber;
//	private String researchFields;
//	private String highestDegree;

	// @OneToMany(mappedBy = "user", cascade={CascadeType.ALL})
	// private Set<ClimateService> climateServices = new
	// HashSet<ClimateService>();

	public User() {
	}

	public User(String userName, String email, String password) {
		this.userName = userName;
		this.email = email;
		this.password = password;
	}

	public User(String userName, String password,
			String email, String phoneNumber) {
		super();
		this.userName = userName;
		this.password = password;
		this.email = email;
		this.phoneNumber = phoneNumber;
	}

	public long getId() {
		return id;
	}

	public String getUserName() {
		return userName;
	}

	public String getPassword() {
		return password;
	}

	public String getEmail() {
		return email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Set<User> getFollowers(){ return this.followers; }
	
	public void setFollowers(Set<User> followers) {
		this.followers = followers;
	}

	public void setFriendRequestSender(Set<User> friendRequestSender) {
		this.friendRequestSender = friendRequestSender;
	}
	public Set<User> getFriendRequestSender() {	return this.friendRequestSender;}

	public void setFriends(Set<User> friends) {this.friends = friends;}

	public Set<User> getFriends() {return this.friends;}

	@Override
	public String toString() {
		return "User [id=" + id + ", userName=" + userName
				+ ", password=" + password + ", email=" + email
				+ ", phoneNumber=" + phoneNumber + "]";
	}

	public String toJson() {
		return "{\"User\":{\"id\":\"" + id + "\", \"userName\":\"" + userName
				+ "\", \"password\":\"" + password + "\", \"email\":\"" + email + "\", \"avatar\":\"" + avatar

				+ "\", \"phoneNumber\":\"" + phoneNumber + "\"}}";
	}
	
	public void setStatus(boolean status){ this.status = status; }

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

/*	public String getMiddleInitial() {
		return middleInitial;
	}

	public String getAffiliation() {
		return affiliation;
	}

	public String getTitle() {
		return title;
	}

	public String getMailingAddress() {
		return mailingAddress;
	}

	public String getFaxNumber() {
		return faxNumber;
	}

	public String getResearchFields() {
		return researchFields;
	}

	public String getHighestDegree() {
		return highestDegree;
	}

	public void setMiddleInitial(String middleInitial) {
		this.middleInitial = middleInitial;
	}

	public void setAffiliation(String affiliation) {
		this.affiliation = affiliation;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setMailingAddress(String mailingAddress) {
		this.mailingAddress = mailingAddress;
	}

	public void setFaxNumber(String faxNumber) {
		this.faxNumber = faxNumber;
	}

	public void setResearchFields(String researchFields) {
		this.researchFields = researchFields;
	}

	public void setHighestDegree(String highestDegree) {
		this.highestDegree = highestDegree;
	}*/
}

