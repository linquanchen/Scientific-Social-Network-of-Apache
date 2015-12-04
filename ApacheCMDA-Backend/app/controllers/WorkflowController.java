
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
import com.google.gson.JsonObject;
import models.*;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Named
@Singleton
public class WorkflowController extends Controller {


    private final WorkflowRepository workflowRepository;
    private final UserRepository userRepository;
    private final GroupUsersRepository groupUsersRepository;
    private final CommentRepository commentRepository;

    @Inject
    public WorkflowController(final WorkflowRepository workflowRepository,
                              UserRepository userRepository, GroupUsersRepository groupUsersRepository,
                              CommentRepository commentRepository) {
        this.workflowRepository = workflowRepository;
        this.userRepository = userRepository;
        this.groupUsersRepository = groupUsersRepository;
        this.commentRepository = commentRepository;
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
        String wfImg = json.path("wfImg").asText();
        String wfVisibility = json.path("wfVisibility").asText();
        long wfGroupId = json.path("wfGroupId").asLong();

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

        //groupId would be 0 if it is public
        Workflow workflow = new Workflow(userID, wfTitle, wfCategory, wfCode, wfDesc, wfImg,
                wfVisibility, user, wfContributors, wfRelated, "norm", wfGroupId);
        Workflow savedWorkflow = workflowRepository.save(workflow);
        return created(new Gson().toJson(savedWorkflow.getId()));
    }

    //edit workflow
    public Result updateWorkflow() {
        JsonNode json = request().body().asJson();
        if (json == null) {
            System.out.println("Workflow not created, expecting Json data");
            return badRequest("Workflow not created, expecting Json data");
        }

        long wfID = json.path("wfID").asLong();
        long userID = json.path("userID").asLong();
        String wfTitle = json.path("wfTitle").asText();
        String wfCategory = json.path("wfCategory").asText();
        String wfCode = json.path("wfCode").asText();
        String wfDesc = json.path("wfDesc").asText();
        //img
        String wfVisibility = json.path("wfVisibility").asText();
        String wfStatus = json.path("wfStatus").asText();

        Workflow workflow = workflowRepository.findOne(wfID);
        User user = userRepository.findOne(userID);
        workflow.getWfContributors().add(user);
        workflow.setWfTitle(wfTitle);
        workflow.setWfCategory(wfCategory);
        workflow.setWfCategory(wfCode);
        workflow.setWfVisibility(wfVisibility);
        workflow.setWfDesc(wfDesc);
        workflow.setStatus(wfStatus);

        workflowRepository.save(workflow);

        return created(new Gson().toJson("success"));
    }

    //delete workflow
    public Result deleteWorkflow() {
        JsonNode json = request().body().asJson();
        if (json == null) {
            System.out.println("Workflow not created, expecting Json data");
            return badRequest("Workflow not created, expecting Json data");
        }

        long wfID = json.path("wfID").asLong();
        Workflow workflow = workflowRepository.findOne(wfID);
        workflow.setStatus("deleted");
        workflowRepository.save(workflow);
        return created(new Gson().toJson("success"));
    }

    public Result uploadImage(Long id) {
        Http.MultipartFormData body = request().body().asMultipartFormData();
        Http.MultipartFormData.FilePart image = body.getFile("image");

        Workflow workflow = workflowRepository.findOne(id);
        if (image != null) {
            File imgFile = image.getFile();
            String imgPathToSave = "public/images/" + "image_" + id + ".jpg";

            //save on disk
            boolean success = new File("images").mkdirs();
            try {
                byte[] bytes = IOUtils.toByteArray(new FileInputStream(imgFile));
                FileUtils.writeByteArrayToFile(new File(imgPathToSave), bytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
            workflow.setWfImg(imgPathToSave);
            workflowRepository.save(workflow);
            return ok("File uploaded");
        } else {
            flash("error", "Missing file");
            return badRequest("Wrong!!!!!!!!");
            // return redirect(routes.Application.index());
        }
    }
    //get detailed workflow.
    public Result get(Long wfID, Long userID, String format) {
        if (wfID == null) {
            System.out.println("Workflow id is null or empty!");
            return badRequest("Workflow id is null or empty!");
        }

        Workflow workflow = workflowRepository.findOne(wfID);
        workflow.setViewCount();
        workflowRepository.save(workflow);

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

    //get user's own workflow list.
    public Result getWorkflowList(Long userID, String format) {
        if (userID == null) {
            System.out.println("user id is null or empty!");
            return badRequest("user id is null or empty!");
        }

        List<Workflow> workflowList = workflowRepository.findByUserID(userID);
        for(Workflow workflow: workflowList) {
            workflow.setEdit(true);
        }

        String result = new String();
        if (format.equals("json")) {
            result = new Gson().toJson(workflowList);
        }

        return ok(result);
    }

    //get user's own workflow list.
    public Result getPublicWorkflow(String format) {

        List<Workflow> workflowList = workflowRepository.findPubicWorkflow();
        for(Workflow workflow: workflowList) {
            workflow.setEdit(true);
        }

        String result = new String();
        if (format.equals("json")) {
            result = new Gson().toJson(workflowList);
        }

        return ok(result);
    }



    public Result getTimeLine(Long id, Long offset, String format) {
        if(id == null) {
            System.out.println("Id not created, please enter valid user");
            return badRequest("Id not created, please enter valid user");
        }

        List<GroupUsers> groups = groupUsersRepository.findByUserId(id);
        List<GroupUsers> adminGroup = groupUsersRepository.findByCreatorUser(id);
        List<Integer> adminGroupParse = new ArrayList<>();
        List<Integer> groupsParse = new ArrayList<>();

        for(int i=0; i<groups.size(); i++) {
            groupsParse.add((int)groups.get(i).getId());
        }
        for(int i=0; i<adminGroup.size(); i++) {
            adminGroupParse.add((int)adminGroup.get(i).getId());
        }
        List<Workflow> allWorkflows = new ArrayList<>();
        Set<User> followees = userRepository.findByFollowerId(id);
        if(followees.size()>0) {
            for (User followee: followees) {
                List<Workflow> workflows = workflowRepository.findByUserID(followee.getId());
                for(Workflow single: workflows) {
                    if((groupsParse.contains((int)single.getGroupId()) || single.getGroupId() == 0) && !single.getStatus().equals("deleted")) {
                        if(adminGroup.contains((int)single.getGroupId())) {
                            single.setEdit(true);
                        }
                        allWorkflows.add(single);
                    }
                }
            }
        }
        List<Workflow> workflows = workflowRepository.findByUserID(id);
        for(Workflow w: workflows) {
            w.setEdit(true);
            allWorkflows.add(w);
        }

        List<Workflow> workflowWithOffset = new ArrayList<>();
        for(int i=(offset.intValue()*6); i<allWorkflows.size() && i<(offset.intValue()*6+6); i++) {
            workflowWithOffset.add(allWorkflows.get(i));
        }

        String result = new String();
        if (format.equals("json")) {
            result = new Gson().toJson(workflowWithOffset);
        }

        return ok(result);
    }


    public Result addComment(){
        try{
            JsonNode json = request().body().asJson();
            if(json==null){
                System.out.println("Comment not created, expecting Json data");
                return badRequest("Comment not created, expecting Json data");
            }

            long userId = json.path("userID").asLong();
            long timestamp = json.path("timestamp").asLong();
            long workflowId = json.path("workflowID").asLong();
            String content = json.path("Content").asText();
            String commentImage = json.path("commentImg").asText();

            User user = userRepository.findOne(userId);
            if(user==null){
                System.out.println("Cannot find user with given user id");
                return badRequest("Cannot find user with given user id");
            }
            Workflow workflow = workflowRepository.findOne(workflowId);
            if(workflow==null){
                System.out.println("Cannot find workflow with given workflow id");
                return badRequest("Cannot find workflow with given workflow id");
            }
            Comment comment = new Comment(user, timestamp, content, commentImage);
            Comment savedComment = commentRepository.save(comment);
            List<Comment> list = workflow.getComments();
            list.add(comment);
            workflow.setComments(list);
            workflowRepository.save(workflow);
            return ok(new Gson().toJson(savedComment.getId()));
        } catch (Exception e){
            e.printStackTrace();
            return badRequest("Failed to add comment!");
        }
    }

    public Result getComments(Long workflowId) {
        try{
            if(workflowId==null){
                System.out.println("Expecting workflow id");
                return badRequest("Expecting workflow id");
            }

            Workflow workflow = workflowRepository.findOne(workflowId);

            List<Comment> comments = workflow.getComments();

            return ok(new Gson().toJson(comments));
        } catch (Exception e){
            e.printStackTrace();
            return badRequest("Failed to fetch comments");
        }
    }

//    public Result uploadCommentImage(Long id) {
//        Http.MultipartFormData body = request().body().asMultipartFormData();
//        Http.MultipartFormData.FilePart image = body.getFile("image");
//
//        Comment comment = commentRepository.findOne(id);
//
//
//        if (image != null) {
//            File imgFile = image.getFile();
//            String imgPathToSave = "public/images/" + "commentImage_" + id + ".jpg";
//
//            //save on disk
//            boolean success = new File("images").mkdirs();
//            try {
//                byte[] bytes = IOUtils.toByteArray(new FileInputStream(imgFile));
//                FileUtils.writeByteArrayToFile(new File(imgPathToSave), bytes);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            comment.setCommentImage(imgPathToSave);
//            commentRepository.save(comment);
//            return ok("File uploaded");
//        }
//        else {
//            flash("error", "Missing file");
//            return badRequest("Wrong!!!!!!!!");
//            // return redirect(routes.Application.index());
//        }
//
//    }

}
