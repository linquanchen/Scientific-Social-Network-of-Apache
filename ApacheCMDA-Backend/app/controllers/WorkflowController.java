
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

import models.Workflow;
import models.WorkflowRepository;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;

import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
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
    private final TagRepository tagRepository;

    @Inject
    public WorkflowController(final WorkflowRepository workflowRepository,
                              UserRepository userRepository, GroupUsersRepository groupUsersRepository,
                              CommentRepository commentRepository, TagRepository tagRepository) {
        this.workflowRepository = workflowRepository;
        this.userRepository = userRepository;
        this.groupUsersRepository = groupUsersRepository;
        this.commentRepository = commentRepository;
        this.tagRepository = tagRepository;
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
        String wfTags = json.path("wfTags").asText();
        long wfGroupId = json.path("wfGroupId").asLong();
        String wfUrl = json.path("wfUrl").asText();

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
                wfVisibility, user, wfContributors, wfRelated, "norm", wfGroupId, user.getUserName(), wfUrl);
        Workflow savedWorkflow = workflowRepository.save(workflow);
        Workflow newWorkflow = workflowRepository.findById(savedWorkflow.getId());


        if(wfTags!=null && !wfTags.equals("")) {
            //add tag to workflow
            String tagStrings[] = wfTags.split(",");
            for (int i = 0; i < tagStrings.length; i++) {
                tagStrings[i] = tagStrings[i].trim();
            }

            for (String t : tagStrings) {
                Tag tag = tagRepository.findByTag(t);
                if (tag == null) {
                    tag = new Tag(t);
                    tagRepository.save(tag);
                }
                Set<Tag> tags = newWorkflow.getTags();

                tags.add(tag);
                newWorkflow.setTags(tags);
            }
        }

        newWorkflow = workflowRepository.save(newWorkflow);
        return created(new Gson().toJson(newWorkflow.getId()));
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
        Workflow workflow = workflowRepository.findOne(wfID);
        User user = userRepository.findOne(userID);

        //public workflow cannot be edit by others
        long wfGroupId = workflow.getGroupId();
        if((int) wfGroupId == 0) {
            return badRequest("You have no access to edit the workflow!");
        }
        GroupUsers group = groupUsersRepository.findOne(wfGroupId);
        //only the admin of the group or the user himself could edit the workflow
        if((int)group.getCreatorUser() != userID && (int)workflow.getUserID() != userID) {
            return badRequest("You have no access to edit the workflow!");
        }
        String wfTitle = json.path("wfTitle").asText();
        String wfCategory = json.path("wfCategory").asText();
        String wfCode = json.path("wfCode").asText();
        String wfDesc = json.path("wfDesc").asText();
        //img
        String wfVisibility = json.path("wfVisibility").asText();
        String wfStatus = json.path("wfStatus").asText();


        if(!workflow.getWfContributors().contains(user)) {
            workflow.getWfContributors().add(user);
        }
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
        long userID = json.path("userID").asLong();
        Workflow workflow = workflowRepository.findOne(wfID);
        if(workflow == null) {
            return badRequest("Workflow doesn't exist!");
        }

        List<GroupUsers> groups = groupUsersRepository.findByCreatorUser(userID);
        List<Integer> groupList = new ArrayList<>();
        for (GroupUsers g: groups) {
            groupList.add((int)g.getId());
        }
        if(!groupList.contains((int)workflow.getGroupId()) && (int)userID != (int)workflow.getUserID()) {
            return badRequest("No access!");
        }
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

        if (workflow == null) {
            System.out.println("The workflow does not exist!");
            return badRequest("The workflow does not exist!");
        }
        else {
            if (workflow.getStatus().equals("deleted")) {
                return badRequest("This workflow has been deleted");
            }
            else if (workflow.getWfVisibility().equals("private")){
                return badRequest("This workflow has is private");
            }
            else if((int) workflow.getGroupId() != 0 && (int)workflow.getUserID() != userID.intValue()) {
                List<GroupUsers> groupList = groupUsersRepository.findByUserId(userID);
                List<Integer> groupListParse = new ArrayList<>();
                for (GroupUsers g: groupList) {
                    groupListParse.add((int)g.getId());
                }
                if(!groupListParse.contains((int) workflow.getGroupId())) {
                    return badRequest("You have no access to this workflow");
                }
            }
        }

        workflow.setViewCount();
        workflowRepository.save(workflow);
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

    public Result getTimeLine(Long id, Long offset, String format) {
        if(id == null) {
            System.out.println("Id not created, please enter valid user");
            return badRequest("Id not created, please enter valid user");
        }

        List<GroupUsers> groups = groupUsersRepository.findByUserId(id);
        List<GroupUsers> adminGroup = groupUsersRepository.findByCreatorUser(id);
        List<Workflow> allWorkflows = new ArrayList<>();

        for (GroupUsers g: groups) {
            List<Workflow> cur = workflowRepository.findByGroupId(g.getId());
            allWorkflows.addAll(new ArrayList<>(cur));
        }

        List<Integer> adminGroupParse = new ArrayList<>();
        List<Integer> groupsParse = new ArrayList<>();

        for(int i=0; i<groups.size(); i++) {
            groupsParse.add((int)groups.get(i).getId());
        }
        for(int i=0; i<adminGroup.size(); i++) {
            adminGroupParse.add((int)adminGroup.get(i).getId());
        }

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

            commentRepository.save(comment);
            //Comment comment = new Comment(user, timestamp, content);

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
    
    public Result setTag() {
        try{
            JsonNode json = request().body().asJson();
            if(json==null){
                System.out.println("Tag not created, expecting Json data");
                return badRequest("Tag not created, expecting Json data");
            }

            long workflowId = json.path("workflowID").asLong();
            String tagString = json.path("tags").asText();
            String tagStrings[] = tagString.split(",");
            for(int i=0; i<tagStrings.length; i++) {
                tagStrings[i] = tagStrings[i].trim();
            }

            if(tagStrings.length<1) {
                System.out.println("Please input tag");
                return badRequest("Please input tag");
            }

            Workflow workflow = workflowRepository.findOne(workflowId);
            if(workflow==null){
                System.out.println("Cannot find workflow with given workflow id");
                return badRequest("Cannot find workflow with given workflow id");
            }

            for(String t: tagStrings) {
                Tag tag = tagRepository.findByTag(t);
                if(tag == null) {
                    tag = new Tag(t);
                    tagRepository.save(tag);
                }
                Set<Tag> tags = workflow.getTags();
                tags.add(tag);
            }
            workflowRepository.save(workflow);

            return ok("Tags add successfully");
        } catch (Exception e){
            e.printStackTrace();
            return badRequest("Failed to add Tag!");
        }
    }

    public Result deleteTag( Long workflowId, String tagString) {
        try{
            Workflow workflow = workflowRepository.findOne(workflowId);
            if(workflow==null){
                System.out.println("Cannot find workflow with given workflow id");
                return badRequest("Cannot find workflow with given workflow id");
            }

            Tag tag = tagRepository.findByTag(tagString);
            if(tag==null){
                System.out.println("Cannot find tag with given tagString");
                return badRequest("Cannot find tag with given tagString");
            }
            Set<Tag> tags = workflow.getTags();
            for(Tag tt : tags) {
                if(tt.getTag().equals(tagString)) {
                    tags.remove(tt);
                }
            }
            workflow.setTags(tags);
            workflowRepository.save(workflow);
            return ok("Tags delete successfully");

        } catch (Exception e){
            e.printStackTrace();
            return badRequest("Failed to delete Tag!");
        }
    }

    public Result getTags(Long workflowId) {
        try {
            Workflow workflow = workflowRepository.findOne(workflowId);
            if(workflow==null){
                System.out.println("Cannot find workflow with given workflow id");
                return badRequest("Cannot find workflow with given workflow id");
            }

            Set<Tag> tags = workflow.getTags();
            StringBuilder sb = new StringBuilder();
            sb.append("{\"tags\":");

            if(!tags.isEmpty()) {
                sb.append("[");
                for (Tag t : tags) {
                    sb.append(t.toJson() + ",");
                }
                if (sb.lastIndexOf(",") > 0) {
                    sb.deleteCharAt(sb.lastIndexOf(","));
                }
                sb.append("]}");
            } else {
                sb.append("{}}");
            }
            return ok(sb.toString());
        } catch (Exception e){
            e.printStackTrace();
            return badRequest("Failed to get Tags!");
        }
    }

    public Result getByTag(String tagString) {
        try {
            if(tagString==null || tagString.equals("")) {
                System.out.println("tag is null or empty!");
                return badRequest("tag is null or empty!");
            }

            Tag tag = tagRepository.findByTag(tagString);
            if(tag==null) {
                System.out.println("Tag doesn't exist");
                return ok();
            }

            Long tagId = tag.getId();

            List<Workflow> workflowList = workflowRepository.findByTagId(tagId);

            String result = new Gson().toJson(workflowList);
            return  ok(result);

        } catch (Exception e){
            e.printStackTrace();
            return badRequest("Failed to get workflow by Tag!");
        }
    }
    
    public Result getByTitle(String title) {
        try {
            if(title==null || title.equals("")) {
                System.out.println("title is null or empty!");
                return badRequest("title is null or empty!");
            }
            
            List<Workflow> workflowList = workflowRepository.findByTitle("%" + title + "%");

            String result = new Gson().toJson(workflowList);
            return  ok(result);

        } catch (Exception e){
            e.printStackTrace();
            return badRequest("Failed to get workflow by Title!");
        }
    }


}
