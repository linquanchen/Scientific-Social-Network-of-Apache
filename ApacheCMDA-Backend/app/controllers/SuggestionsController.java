
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
package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import models.*;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import util.Common;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.util.*;

@Named
@Singleton
public class SuggestionsController extends Controller {


    private final WorkflowRepository workflowRepository;
    private final UserRepository userRepository;
    private final SuggestionsRepository suggestionsRepository;


    @Inject
    public SuggestionsController(final WorkflowRepository workflowRepository,
                                 UserRepository userRepository, SuggestionsRepository suggestionsRepository) {
        this.workflowRepository = workflowRepository;
        this.userRepository = userRepository;
        this.suggestionsRepository = suggestionsRepository;
    }


    //post suggestion
    public Result publishSuggestion() {
        JsonNode json = request().body().asJson();
        if (json == null) {
            System.out.println("Suggestion not created, expecting Json data");
            return Common.badRequestWrapper("Suggestion not created, expecting Json data");
        }

        long userID = json.path("userID").asLong();
        User user = userRepository.findOne(userID);
        if(user == null) {
            return Common.badRequestWrapper("User doesn't exist!");
        }
        long wfID = json.path("wfID").asLong();
        Workflow workflow = workflowRepository.findById(wfID);
        if(workflow == null) {
            return Common.badRequestWrapper("Workflow doesn't exist!");
        }

        String suggestionContent = json.path("sContent") == null? "NaN": json.path("sContent").asText();
        List<Workflow> suggestionAndWorkflow = new ArrayList<>();
        suggestionAndWorkflow.add(workflow);

        Suggestions suggestions = new Suggestions(userID, suggestionContent,suggestionAndWorkflow);
        suggestionsRepository.save(suggestions);
        return created(new Gson().toJson(suggestions.getId()));
    }

    //post: add Tag to suggestion
    public Result addTag() {
        JsonNode json = request().body().asJson();
        if (json == null) {
            System.out.println("Tag not created, expecting Json data");
            return Common.badRequestWrapper("Tag not created, expecting Json data");
        }

        long suggestionID = json.path("sID").asLong();
        Suggestions suggestions = suggestionsRepository.findOne(suggestionID);
        if(suggestions == null) {
            return Common.badRequestWrapper("Suggestion doesn't exist!");
        }
        String sTag = json.path("sTag") == null? "NaN": json.path("sTag").asText();
        String originTag = suggestions.getSuggestionTag();
        if (originTag == null || originTag.toLowerCase().equals("null")) {
            originTag = sTag;
        }
        else {
            originTag = originTag + "|" + sTag;
        }
        suggestions.setSuggestionTag(originTag);
        suggestionsRepository.save(suggestions);

        return created(new Gson().toJson("success"));
    }

    //vote to suggestion
    public Result voteToSuggestion(Long suggestionID) {

        Suggestions suggestions = suggestionsRepository.findOne(suggestionID);
        if(suggestions == null) {
            return Common.badRequestWrapper("Suggestion doesn't exist!");
        }
        suggestions.setSuggestionVotes();
        suggestionsRepository.save(suggestions);

        return created(new Gson().toJson("success"));
    }

    //get suggestionList for workflow
    public Result getSuggestionForWorkflow(Long workflowId) {
        Workflow workflow = workflowRepository.findById(workflowId);
        if(workflow == null) {
            return Common.badRequestWrapper("Workflow doesn't exist!");
        }

        List<Suggestions> suggestionsList = suggestionsRepository.findByWorkflowId(workflowId);
        return created(new GsonBuilder().excludeFieldsWithModifiers(Modifier.PROTECTED).create().toJson(suggestionsList));
    }

}
