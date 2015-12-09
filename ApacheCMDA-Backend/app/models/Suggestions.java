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
public class Suggestions {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
    private long suggestionCreator;
	private String suggestionContent;
	private String suggestionTag;
	private int suggestionVotes;

	@ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE})
	@JoinTable(name = "SuggestionsAndWorkflows", joinColumns = { @JoinColumn(name ="suggestionId", referencedColumnName = "id")}, inverseJoinColumns = { @JoinColumn(name = "workflowId", referencedColumnName = "id") })
	private List<Workflow> suggestionWorkflows;

	public Suggestions() {
	}

	public Suggestions(long suggestionCreator, String suggestionContent, List<Workflow> suggestionWorkflows) {
		super();
        this.suggestionCreator = suggestionCreator;
		this.suggestionContent = suggestionContent;
		this.suggestionWorkflows = suggestionWorkflows;
		this.suggestionVotes = 0;
	}

    public long getId() {return id;}

	public List<Workflow> getSuggestionWorkflows() {
		return suggestionWorkflows;
	}

	public void setSuggestionWorkflows(List<Workflow> suggestionWorkflows) {
		this.suggestionWorkflows = suggestionWorkflows;
	}

	public int getSuggestionVotes() {
		return suggestionVotes;
	}

	public void setSuggestionVotes() {
		this.suggestionVotes++;
	}

	public String getSuggestionTag() {
		return suggestionTag;
	}

	public void setSuggestionTag(String suggestionTag) {
		this.suggestionTag = suggestionTag;
	}

	public String getSuggestionContent() {
		return suggestionContent;
	}

	public void setSuggestionContent(String suggestionContent) {
		this.suggestionContent = suggestionContent;
	}

	public long getSuggestionCreator() {
		return suggestionCreator;
	}

	public void setSuggestionCreator(long suggestionCreator) {
		this.suggestionCreator = suggestionCreator;
	}

	@Override
	public String toString() {
		return "Suggestions [id=" + id + ", suggestionCreator=" + suggestionCreator + ", suggestionContent=" + suggestionContent + ", suggestionTag=" + suggestionTag
				+ ", suggestionVotes=" + suggestionVotes +"]";
	}
}

