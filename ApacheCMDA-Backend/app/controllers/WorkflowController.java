
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
            return Common.badRequestWrapper("Workflow not created, expecting Json data");
        }

//        long userID = json.path("userID").asLong();
//        String wfTitle = json.path("wfTitle").asText();
//        String wfCategory = json.path("wfCategory").asText();
//        String wfCode = json.path("wfCode").asText();
//        String wfDesc = json.path("wfDesc").asText();
//        String wfImg = json.path("wfImg").asText();
//        String wfVisibility = json.path("wfVisibility").asText();
//        String wfTags = json.path("wfTags").asText();
//        long wfGroupId = json.path("wfGroupId").asLong();
//        String wfUrl = json.path("wfUrl").asText();
//        String wfInput = json.path("wfInput").asText();
//        String wfOutput = json.path("wfOutput").asText();

        long userID = json.path("userID").asLong();
        String wfTags = json.path("wfTags").asText();
        User user = userRepository.findOne(userID);
        if(user == null) {
            return Common.badRequestWrapper("User doesn't exist!");
        }

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
        Workflow workflow = new Workflow(json);
        workflow.setWfContributors(wfContributors);
        workflow.setWfRelated(wfRelated);
        workflow.setUserName(user.getUserName());
        workflow.setStatus("norm");
        workflow.setUser(user);
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
            System.out.println("Workflow not updated, expecting Json data");
            return Common.badRequestWrapper("Workflow not updated, expecting Json data");
        }

        long wfID = json.path("wfID").asLong();
        long userID = json.path("userID").asLong();
        Workflow workflow = workflowRepository.findOne(wfID);
        User user = userRepository.findOne(userID);
        if (workflow == null) {
            return Common.badRequestWrapper("Workflow doesn't exist.");
        }

        System.out.println("wfID is " + wfID);
        System.out.println("userID is " + userID);

        //public workflow cannot be edit by others
        long wfGroupId = workflow.getGroupId();
        if((int) wfGroupId == 0 && (int)workflow.getUserID() != (int) userID) {
            Map<String, String> map = new HashMap<>();
            map.put("error", "No Access!");
            String error = new Gson().toJson(map);
            return ok(error);
        }
        GroupUsers group = groupUsersRepository.findOne(wfGroupId);
        //only the admin of the group or the user himself could edit the workflow
        if((int) wfGroupId != 0 && (int)group.getCreatorUser() != userID && (int)workflow.getUserID() != (int)userID) {
            Map<String, String> map = new HashMap<>();
            map.put("error", "No Access!");
            String error = new Gson().toJson(map);
            return ok(error);
        }

        System.out.println("have access");
        if (json.get("wfTitle")!=null) workflow.setWfTitle(json.get("wfTitle").asText());
        if (json.get("wfCode")!=null) workflow.setWfCode(json.get("wfCode").asText());
        if (json.get("wfDesc")!=null)  workflow.setWfDesc(json.get("wfDesc").asText());
        if (json.get("wfImg")!=null)   workflow.setWfImg(json.get("wfImg").asText());
        if (json.get("wfCategory")!=null) workflow.setWfCategory(json.get("wfCategory").asText());
        if (json.get("wfVisibility")!=null) workflow.setWfVisibility(json.get("wfVisibility").asText());
        if (json.get("wfUrl")!=null)  workflow.setWfUrl(json.get("wfUrl").asText());
        if (json.get("wfInput")!=null) workflow.setWfInput(json.get("wfInput").asText());
        if (json.get("wfOutput")!=null) workflow.setWfOutput(json.get("wfOutput").asText());
        if (json.get("wfStatus")!=null) workflow.setStatus(json.get("wfStatus").asText());
        Date cur = new Date();
        workflow.setWfDate(cur);

        //if(!workflow.getWfContributors().contains(user)) {
        //    workflow.getWfContributors().add(user);
        //}
        workflowRepository.save(workflow);
        return created(new Gson().toJson("success"));
    }

    //delete workflow
    public Result deleteWorkflow() {
        JsonNode json = request().body().asJson();
        if (json == null) {
            System.out.println("Workflow not created, expecting Json data");
            return Common.badRequestWrapper("Workflow not created, expecting Json data");
        }

        long wfID = json.path("wfID").asLong();
        long userID = json.path("userID").asLong();
        Workflow workflow = workflowRepository.findOne(wfID);
        if(workflow == null) {
            return Common.badRequestWrapper("Workflow doesn't exist!");
        }

        List<GroupUsers> groups = groupUsersRepository.findByCreatorUser(userID);
        List<Integer> groupList = new ArrayList<>();
        for (GroupUsers g: groups) {
            groupList.add((int)g.getId());
        }
        if(!groupList.contains((int)workflow.getGroupId()) && (int)userID != (int)workflow.getUserID()) {
            return Common.badRequestWrapper("No access!");
        }
        workflow.setStatus("deleted");
        workflowRepository.save(workflow);
        return ok("{\"success\":\"Success!\"}");
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
            return Common.badRequestWrapper("Wrong!!!!!!!!");
            // return redirect(routes.Application.index());
        }
    }
    //get detailed workflow.
    public Result get(Long wfID, Long userID, String format) {
        if (wfID == null) {
            System.out.println("Workflow id is null or empty!");
            return Common.badRequestWrapper("Workflow id is null or empty!");
        }

        Workflow workflow = workflowRepository.findOne(wfID);
        Set<User> empty = new HashSet<>();
        workflow.getUser().setFollowers(empty);
        workflow.getUser().setFriends(empty);

        if (workflow == null) {
            System.out.println("The workflow does not exist!");
            return Common.badRequestWrapper("The workflow does not exist!");
        }
        else {
            if (workflow.getStatus().equals("deleted")) {
                return Common.badRequestWrapper("This workflow has been deleted");
            }
            else if((int) workflow.getGroupId() != 0 && (int)workflow.getUserID() != userID.intValue()) {
                List<GroupUsers> groupList = groupUsersRepository.findByUserId(userID);
                List<Integer> groupListParse = new ArrayList<>();
                for (GroupUsers g: groupList) {
                    groupListParse.add((int)g.getId());
                }
                if(!groupListParse.contains((int) workflow.getGroupId())) {
                    Map<String, String> map = new HashMap<>();
                    map.put("error", "No Access!");
                    String error = new Gson().toJson(map);
                    return ok(error);
                }
            }
        }

        workflow.setViewCount();
        workflowRepository.save(workflow);
        List<GroupUsers> adminGroup = groupUsersRepository.findByCreatorUser(userID);
        List<Integer> adminGroupList = new ArrayList<>();
        for (GroupUsers g:adminGroup) {
            adminGroupList.add((int)g.getId());
        }
        System.out.println("admin group is " + adminGroupList);
        if((int)workflow.getUserID() == userID.intValue() || adminGroupList.contains((int)workflow.getGroupId())) {
            workflow.setEdit(true);
        }

        String result = new String();
        if (format.equals("json")) {
            result = new GsonBuilder().excludeFieldsWithModifiers(Modifier.PROTECTED).create().toJson(workflow);
        }

//        Map<String, Object> map = new HashMap<>();
//        map.put("admin group", adminGroupList);
//        map.put("workflow group id is ", (int)workflow.getGroupId());
//        map.put("work flow user id", (int)workflow.getUserID());
//        map.put("user id:", userID.intValue());
//        String erro = new Gson().toJson(map);
//        return ok(erro);

        return ok(result);
    }

    //get user's own workflow list.
    public Result getWorkflowList(Long userID, String format) {
        if (userID == null) {
            System.out.println("user id is null or empty!");
            return Common.badRequestWrapper("user id is null or empty!");
        }

        List<Workflow> workflowList = workflowRepository.findByUserID(userID);
        for(Workflow workflow: workflowList) {
            workflow.setEdit(true);
        }

        String result = new String();
        if (format.equals("json")) {
            result = new GsonBuilder().excludeFieldsWithModifiers(Modifier.PROTECTED).create().toJson(workflowList);
        }

        return ok(result);
    }

    //get public workflow list.
    public Result getPublicWorkflow(String format) {

        List<Workflow> workflowList = workflowRepository.findPubicWorkflow();
        for(Workflow workflow: workflowList) {
            workflow.setEdit(true);
            Set<User> empty = new HashSet<>();
            workflow.getUser().setFollowers(empty);
            workflow.getUser().setFriends(empty);
        }

        String result = new String();
        if (format.equals("json")) {
            result = new GsonBuilder().excludeFieldsWithModifiers(Modifier.PROTECTED).create().toJson(workflowList);
        }

        return ok(result);
    }



    public Result getTimeLine(Long id, Long offset, String format) {
        if(id == null) {
            System.out.println("Id not created, please enter valid user");
            return Common.badRequestWrapper("Id not created, please enter valid user");
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
            if(w.getGroupId() != 0) {
                for (int i=0; i<allWorkflows.size(); i++) {
                    if ((int)w.getGroupId() == (int)allWorkflows.get(i).getGroupId()) {
                        allWorkflows.get(i).setEdit(true);
                    }
                }
            }
            else {
                w.setEdit(true);
                allWorkflows.add(w);
            }
        }

        Comparator<Workflow> cmp = new Comparator<Workflow>() {
            @Override
            public int compare(Workflow o1, Workflow o2) {
                return o2.getWfDate().compareTo(o1.getWfDate());
            }
        };

        Collections.sort(allWorkflows, cmp);
        for(Workflow wf: allWorkflows) {
            Set<User> empty = new HashSet<>();
            wf.getUser().setFollowers(empty);
            wf.getUser().setFriends(empty);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("size", allWorkflows.size());
        List<Workflow> workflowWithOffset = new ArrayList<>();
        for(int i=(offset.intValue()*6); i<allWorkflows.size() && i<(offset.intValue()*6+6); i++) {
            workflowWithOffset.add(allWorkflows.get(i));
        }

        String result = new String();
        if (format.equals("json")) {
            map.put("timeline", workflowWithOffset);
            result = new GsonBuilder().excludeFieldsWithModifiers(Modifier.PROTECTED).create().toJson(map);
        }

        return ok(result);
    }


    public Result addComment(){
        try{
            JsonNode json = request().body().asJson();
            if(json==null){
                System.out.println("Comment not created, expecting Json data");
                return Common.badRequestWrapper("Comment not created, expecting Json data");
            }

            long userId = json.path("userID").asLong();
            long timestamp = json.path("timestamp").asLong();
            long workflowId = json.path("workflowID").asLong();
            String content = json.path("Content").asText();
            String commentImage = json.path("commentImg").asText();

            User user = userRepository.findOne(userId);
            if(user==null){
                System.out.println("Cannot find user with given user id");
                return Common.badRequestWrapper("Cannot find user with given user id");
            }
            Workflow workflow = workflowRepository.findOne(workflowId);
            if(workflow==null){
                System.out.println("Cannot find workflow with given workflow id");
                return Common.badRequestWrapper("Cannot find workflow with given workflow id");
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
            return Common.badRequestWrapper("Failed to add comment!");
        }
    }
    
    public Result setTag() {
        try{
            JsonNode json = request().body().asJson();
            if(json==null){
                System.out.println("Tag not created, expecting Json data");
                return Common.badRequestWrapper("Tag not created, expecting Json data");
            }

            long workflowId = json.path("workflowID").asLong();
            String tagString = json.path("tags").asText();
            String tagStrings[] = tagString.split(",");
            for(int i=0; i<tagStrings.length; i++) {
                tagStrings[i] = tagStrings[i].trim();
            }

            if(tagStrings.length<1) {
                System.out.println("Please input tag");
                return Common.badRequestWrapper("Please input tag");
            }

            Workflow workflow = workflowRepository.findOne(workflowId);
            if(workflow==null){
                System.out.println("Cannot find workflow with given workflow id");
                return Common.badRequestWrapper("Cannot find workflow with given workflow id");
            }

            for(String t: tagStrings) {
                Tag tag = tagRepository.findByTag(t);
                if(tag == null) {
                    tag = new Tag(t);
                    tagRepository.save(tag);
                }
                Set<Tag> tags = workflow.getTags();
                tags.add(tag);
                workflow.setTags(tags);
            }
            workflowRepository.save(workflow);

            return ok("{\"success\":\"Success!\"}");
        } catch (Exception e){
            e.printStackTrace();
            return Common.badRequestWrapper("Failed to add Tag!");
        }
    }

    public Result deleteTag( Long workflowId, String tagString) {
        try{
            Workflow workflow = workflowRepository.findOne(workflowId);
            if(workflow==null){
                System.out.println("Cannot find workflow with given workflow id");
                return Common.badRequestWrapper("Cannot find workflow with given workflow id");
            }

            Tag tag = tagRepository.findByTag(tagString);
            if(tag==null){
                System.out.println("Cannot find tag with given tagString");
                return Common.badRequestWrapper("Cannot find tag with given tagString");
            }
            Set<Tag> tags = workflow.getTags();
            for(Tag tt : tags) {
                if(tt.getTag().equals(tagString)) {
                    tags.remove(tt);
                }
            }
            workflow.setTags(tags);
            workflowRepository.save(workflow);
            return ok("{\"success\":\"Success!\"}");

        } catch (Exception e){
            e.printStackTrace();
            return Common.badRequestWrapper("Failed to delete Tag!");
        }
    }

    public Result getTags(Long workflowId) {
        try {
            Workflow workflow = workflowRepository.findOne(workflowId);
            if(workflow==null){
                System.out.println("Cannot find workflow with given workflow id");
                return Common.badRequestWrapper("Cannot find workflow with given workflow id");
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
            return Common.badRequestWrapper("Failed to get Tags!");
        }
    }

    public Result getByTag(String tagString) {
        try {
            if(tagString==null || tagString.equals("")) {
                System.out.println("tag is null or empty!");
                return Common.badRequestWrapper("tag is null or empty!");
            }

            Tag tag = tagRepository.findByTag(tagString);
            if(tag==null) {
                System.out.println("Tag doesn't exist");
                return ok();
            }

            Long tagId = tag.getId();

            List<Workflow> workflowList = workflowRepository.findByTagId(tagId);
            for (Workflow wf: workflowList) {
                Set<User> empty = new HashSet<>();
                wf.getUser().setFollowers(empty);
                wf.getUser().setFriends(empty);
            }

            String result = new GsonBuilder().excludeFieldsWithModifiers(Modifier.PROTECTED).create().toJson(workflowList);
            return  ok(result);

        } catch (Exception e){
            e.printStackTrace();
            return Common.badRequestWrapper("Failed to get workflow by Tag!");
        }
    }
    
    public Result getByTitle(String title) {
        try {
            if(title==null || title.equals("")) {
                System.out.println("title is null or empty!");
                return Common.badRequestWrapper("title is null or empty!");
            }
            
            List<Workflow> workflowList = workflowRepository.findByTitle("%" + title + "%");
            for (Workflow wf: workflowList) {
                Set<User> empty = new HashSet<>();
                wf.getUser().setFollowers(empty);
                wf.getUser().setFriends(empty);
            }

            String result = new GsonBuilder().excludeFieldsWithModifiers(Modifier.PROTECTED).create().toJson(workflowList);
            return  ok(result);

        } catch (Exception e){
            e.printStackTrace();
            return Common.badRequestWrapper("Failed to get workflow by Title!");
        }
    }

    public Result getTop3WorkFlow() {
        List<Workflow> topWorkflow = workflowRepository.findTop3Workflow();
        String result = new GsonBuilder().excludeFieldsWithModifiers(Modifier.PROTECTED).create().toJson(topWorkflow);
        return  ok(result);
    }

    public Result getComments(Long workflowId) {
        try{
            if(workflowId==null){
                System.out.println("Expecting workflow id");
                return Common.badRequestWrapper("Expecting workflow id");
            }

            List<Comment> comments = commentRepository.findByWorkflowId(workflowId);
            for (Comment comment: comments) {
                Set<User> empty = new HashSet<>();
                comment.getUser().setFollowers(empty);
                comment.getUser().setFriends(empty);
            }


            return ok(new GsonBuilder().excludeFieldsWithModifiers(Modifier.PROTECTED).create().toJson(comments));
        } catch (Exception e){
            e.printStackTrace();
            return Common.badRequestWrapper("Failed to fetch comments");
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
//            return Common.badRequestWrapper("Wrong!!!!!!!!");
//            // return redirect(routes.Application.index());
//        }
//
//    }
}
