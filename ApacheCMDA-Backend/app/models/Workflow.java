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

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;


@Entity
public class Workflow {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String name;
	private String purpose;
	private Date createTime;
	private String versionNo;
	private long rootWorkflowId;
	@ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE})
	@JoinTable(name = "WorkflowAndUser", joinColumns = { @JoinColumn(name ="workflowId", referencedColumnName = "id")}, inverseJoinColumns = { @JoinColumn(name = "userId", referencedColumnName = "id") })
	private List<User> userSet;
	@ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE})
	@JoinTable(name = "WorkflowAndClimateService", joinColumns = { @JoinColumn(name ="workflowId", referencedColumnName = "id")}, inverseJoinColumns = { @JoinColumn(name = "climateServiceId", referencedColumnName = "id") })
	private List<ClimateService> climateServiceSet;

	public Workflow() {
	}

	public Workflow(String name, String purpose, Date createTime,
			String versionNo, long rootWorkflowId, List<User> userSet,
			List<ClimateService> climateServiceSet) {
		super();
		this.name = name;
		this.purpose = purpose;
		this.createTime = createTime;
		this.versionNo = versionNo;
		this.rootWorkflowId = rootWorkflowId;
		this.userSet = userSet;
		this.climateServiceSet = climateServiceSet;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getVersionNo() {
		return versionNo;
	}

	public void setVersionNo(String versionNo) {
		this.versionNo = versionNo;
	}

	public long getRootWorkflowId() {
		return rootWorkflowId;
	}

	public void setRootWorkflowId(long rootWorkflowId) {
		this.rootWorkflowId = rootWorkflowId;
	}

	public List<User> getUserSet() {
		return userSet;
	}

	public void setUserSet(List<User> userSet) {
		this.userSet = userSet;
	}

	public List<ClimateService> getClimateServiceSet() {
		return climateServiceSet;
	}

	public void setClimateServiceSet(List<ClimateService> climateServiceSet) {
		this.climateServiceSet = climateServiceSet;
	}

	public long getId() {
		return id;
	}

	@Override
	public String toString() {
		return "Workflow [id=" + id + ", name=" + name + ", purpose=" + purpose
				+ ", createTime=" + createTime + ", versionNo=" + versionNo
				+ ", rootWorkflowId=" + rootWorkflowId + ", userSet=" + userSet
				+ ", climateServiceSet=" + climateServiceSet + "]";
	}
	
	
}