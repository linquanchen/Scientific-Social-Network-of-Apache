
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
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import models.User;
import models.UserRepository;

import models.Workflow;
import models.WorkflowRepository;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.persistence.PersistenceException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Named
@Singleton
public class WorkflowController extends Controller {


    private final WorkflowRepository workflowRepository;
    private final UserRepository userRepository;

    @Inject
    public WorkflowController(final WorkflowRepository workflowRepository,
                              UserRepository userRepository) {
        this.workflowRepository = workflowRepository;
        this.userRepository = userRepository;
    }

    public Result post() {
        JsonNode json = request().body().asJson();
        if (json == null) {
            System.out.println("Workflow not created, expecting Json data");
            return badRequest("Workflow not created, expecting Json data");
        }

        long userID = json.path("userID").asLong();
        String wfTitle = json.path("wfTitle").asText();
        String wfCategory = json.path("wfCategory").asText();
        String wfCode = json.path("wfCode").asText();
        String wfDesc = json.path("wfDesc").asText();
        //img
        String wfVisibility = json.path("wfVisibility").asText();

        User user = userRepository.findOne(userID);

        JsonNode contributorsID = json.path("wfContributors");
        List<User> wfContributors = new ArrayList<User>();
        for (JsonNode node : contributorsID) {
            wfContributors.add(userRepository.findOne(node.path("userID").asLong()));
        }

        JsonNode relatedID = json.path("wfRelated");
        List<Workflow> wfRelated = new ArrayList<Workflow>();
        for (JsonNode node : relatedID) {
            wfRelated.add(workflowRepository.findOne(node.path("workflowID").asLong()));
        }

        Workflow workflow = new Workflow(userID, wfTitle, wfCategory, wfCode, wfDesc, "IMAGE",
                wfVisibility, user, wfContributors, wfRelated, "norm");
        Workflow savedWorkflow = workflowRepository.save(workflow);

        return created(new Gson().toJson(savedWorkflow.getId()));
    }

    public Result get(Long wfID, Long userID, String format) {
        if (wfID == null) {
            System.out.println("Workflow id is null or empty!");
            return badRequest("Workflow id is null or empty!");
        }

        Workflow workflow = workflowRepository.findOne(wfID);
        if (workflow == null) {
            System.out.println("The workflow does not exist!");
            return badRequest("The workflow does not exist!");
        }
        else {
            if (workflow.getStatus().equals("deleted")) {
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("status", "deleted");
                return ok(new Gson().toJson(jsonObject));
            }
            else {
                if (workflow.getWfVisibility().equals("private")) {
                    if(workflow.getUserID() != userID) {
                        JsonObject jsonObject = new JsonObject();
                        jsonObject.addProperty("status", "protected");
                        return ok(new Gson().toJson(jsonObject));
                    }
                }
            }
        }

        String result = new String();
        if (format.equals("json")) {
            result = new Gson().toJson(workflow);
        }

        return ok(result);
    }

    public Result getTimeLine(Long id, Long offset, String format) {
        if(id == null) {
            System.out.println("Id not created, please enter valid user");
            return badRequest("Id not created, please enter valid user");
        }

        List<Workflow> allWorkflows = new ArrayList<>();
        Set<User> followees = userRepository.findByFollowerId(id);
        for (User followee: followees) {
            List<Workflow> workflows = workflowRepository.findByUserID(followee.getId());
            allWorkflows.addAll(workflows);
        }

        String result = new String();
        if (format.equals("json")) {
            result = new Gson().toJson(allWorkflows);
        }

        return ok(result);
    }
}