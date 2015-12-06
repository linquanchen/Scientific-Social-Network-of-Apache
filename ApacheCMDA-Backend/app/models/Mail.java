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
import java.util.List;
import java.util.UUID;

@Entity
public class Mail {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
    private String fromUserMail;
	private String toUserMail;
	private String mailTitle;
	private String mailContent;
	private Date mailDate;
	private boolean readStatus;

	public Mail() {
	}

	public Mail(String fromUserMail, String toUserMail, String mailTitle, String mailContent, Date mailDate) {
		super();
        this.fromUserMail = fromUserMail;
		this.toUserMail = toUserMail;
		this.mailTitle = mailTitle;
		this.mailContent = mailContent;
		this.mailDate = mailDate;
		this.readStatus = false;
	}

    public long getId() {return id;}

	public String getFromUserMail() {
        return fromUserMail;
    }

	public void setFromUserMail(String fromUserMail) {this.fromUserMail = fromUserMail;}

    public void setToUserMail(String toUserMail) {
        this.toUserMail = toUserMail;
    }

	public String getToUserMail() {
		return toUserMail;
	}

	public String getMailContent() {
		return mailContent;
	}

	public Date getMailDate() {
		return mailDate;
	}

	public String getMailTitle() {
		return mailTitle;
	}

	public void setMailTitle(String mailTitle) {
		this.mailTitle = mailTitle;
	}

	public void setMailDate(Date mailDate) {
		this.mailDate = mailDate;
	}

	public void setMailContent(String mailContent) {
		this.mailContent = mailContent;
	}

	public boolean getReadStatus() {
		return readStatus;
	}

	public void setReadStatus(boolean readStatus) {
		this.readStatus = readStatus;
	}

	@Override
	public String toString() {
		return "Mail [id=" + id + ", fromUserMail=" + fromUserMail + ", toUserMail=" + toUserMail + ", mailTitle=" + mailTitle
				+ ", mailContent=" + mailContent + ", mailDate=" + mailDate +"]";
	}
}

