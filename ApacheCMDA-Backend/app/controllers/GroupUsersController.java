
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
import models.GroupUsers;
import models.GroupUsersRepository;
import models.User;
import models.UserRepository;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;

@Named
@Singleton
public class GroupUsersController extends Controller {


    private final GroupUsersRepository groupUsersRepository;
    private final UserRepository userRepository;

    @Inject
    public GroupUsersController(final GroupUsersRepository groupUsersRepository,
                                UserRepository userRepository) {
        this.groupUsersRepository = groupUsersRepository;
        this.userRepository = userRepository;
    }

    //post create group
    public Result createGroup() {
        JsonNode json = request().body().asJson();
        if (json == null) {
            System.out.println("group not created, expecting Json data");
            return badRequest("group not created, expecting Json data");
        }

        long userID = json.path("userID").asLong();
        String groupName = json.path("groupName").asText();
        String groupDescription = json.path("groupDescription").asText();
        String groupStatus = json.path("groupStatus").asText();

        User user = userRepository.findOne(userID);
        System.out.println("user is " + user);
        List<User> groupMembers = new ArrayList<User>();
        groupMembers.add(user);

        GroupUsers group = new GroupUsers(userID, groupName, groupDescription, groupStatus, groupMembers);
        System.out.println("group is " + group);
        groupUsersRepository.save(group);

        return created(new Gson().toJson(group.getGroupName()));
    }

    //post
    public Result addMembersToGroup() {
        JsonNode json = request().body().asJson();
        if (json == null) {
            System.out.println("group not created, expecting Json data");
            return badRequest("group not created, expecting Json data");
        }

        long groupID = json.path("groupID").asLong();
        long userID = json.path("userID").asLong();

        User user = userRepository.findOne(userID);
        GroupUsers group = groupUsersRepository.findById(groupID);
        group.getGroupMembers().add(user);
        groupUsersRepository.save(group);

        return created(new Gson().toJson("success"));
    }

    //get
    public Result getGroupList(Long userID, String format) {
        if (userID == null) {
            System.out.println("user id is null or empty!");
            return badRequest("user id is null or empty!");
        }

        List<GroupUsers> groups = groupUsersRepository.findByCreatorUser(userID);
        if (groups == null) {
            System.out.println("The group does not exist!");
            return badRequest("The group does not exist!");
        }

        String result = new String();
        if (format.equals("json")) {
            result = new Gson().toJson(groups);
        }

        return ok(result);
    }

    //get
    public Result getGroupMember(Long groupId, String format) {
        if(groupId == null) {
            System.out.println("Id not created, please enter valid user");
            return badRequest("Id not created, please enter valid user");
        }

        GroupUsers group = groupUsersRepository.findById(groupId);
        List<User> groupMembers = group.getGroupMembers();
        for(User groupMember: groupMembers) {
            groupMember.setPassword("******");
        }

        String result = new String();
        if (format.equals("json")) {
            result = new Gson().toJson(groupMembers);
        }

        return ok(result);
    }
}