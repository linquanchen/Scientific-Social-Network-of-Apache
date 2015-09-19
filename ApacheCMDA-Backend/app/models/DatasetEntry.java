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

import java.util.Date;


@Entity
public class DatasetEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String versionNo;
    @Temporal(TemporalType.TIMESTAMP)
    private Date registerTimeStamp;
    private String registerNote;
    private int count;
    @Temporal(TemporalType.TIMESTAMP)
    private Date latestAccessTimeStamp;
    @ManyToOne(optional = false)
	@JoinColumn(name = "datasetId", referencedColumnName = "id")
    Dataset dataset;
    @ManyToOne(optional = false)
    @JoinColumn(name = "registorId", referencedColumnName = "id")
    private User user;

    public DatasetEntry(){
    }

	public DatasetEntry(String versionNo, Date registerTimeStamp,
			String registerNote, int count, Date latestAccessTimeStamp,
			Dataset dataset, User user) {
		super();
		this.versionNo = versionNo;
		this.registerTimeStamp = registerTimeStamp;
		this.registerNote = registerNote;
		this.count = count;
		this.latestAccessTimeStamp = latestAccessTimeStamp;
		this.dataset = dataset;
		this.user = user;
	}

	public long getId() {
		return id;
	}

	public String getVersionNo() {
		return versionNo;
	}

	public Date getRegisterTimeStamp() {
		return registerTimeStamp;
	}

	public String getRegisterNote() {
		return registerNote;
	}

	public int getCount() {
		return count;
	}

	public Date getLatestAccessTimeStamp() {
		return latestAccessTimeStamp;
	}

	public Dataset getDataset() {
		return dataset;
	}

	public User getUser() {
		return user;
	}
	
	public void setVersionNo(String versionNo) {
		this.versionNo = versionNo;
	}

	public void setRegisterTimeStamp(Date registerTimeStamp) {
		this.registerTimeStamp = registerTimeStamp;
	}

	public void setRegisterNote(String registerNote) {
		this.registerNote = registerNote;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public void setLatestAccessTimeStamp(Date latestAccessTimeStamp) {
		this.latestAccessTimeStamp = latestAccessTimeStamp;
	}

	public void setDataset(Dataset dataset) {
		this.dataset = dataset;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "DatasetEntry [id=" + id + ", versionNo=" + versionNo
				+ ", registerTimeStamp=" + registerTimeStamp
				+ ", registerNote=" + registerNote + ", count=" + count
				+ ", latestAccessTimeStamp=" + latestAccessTimeStamp
				+ ", dataset=" + dataset + ", user=" + user + "]";
	}


}