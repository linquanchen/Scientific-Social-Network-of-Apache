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

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.User;
import models.UserRepository;
import play.mvc.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.persistence.PersistenceException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.Gson;
import com.google.gson.JsonObject;


/**
 * The main set of web services.
 */
@Named
@Singleton
public class UserController extends Controller {

	private final UserRepository userRepository;

	// We are using constructor injection to receive a repository to support our
	// desire for immutability.
	@Inject
	public UserController(final UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public Result userRegister() {
		JsonNode json = request().body().asJson();
		if (json == null) {
			System.out.println("User not created, expecting Json data");
			return badRequest("User not created, expecting Json data");
		}

		// Parse JSON file
		String name = json.path("username").asText();
		String email = json.path("email").asText();
		String password = json.path("password").asText();

		try {
			if (userRepository.findByEmail(email) != null) {
				System.out.println("Email has been used: " + email);
				return badRequest("Email has been used");
			}
			User user = new User(name, email, MD5Hashing(password));
			userRepository.save(user);
			System.out.println("User saved: " + user.getId());
			return created(new Gson().toJson(user.getId()));
		} catch (PersistenceException pe) {
			pe.printStackTrace();
			System.out.println("User not saved: " + name);
			return badRequest("User not saved: " + name);
		}
	}

	public Result userLogin() {
		JsonNode json = request().body().asJson();
		if (json == null) {
			System.out.println("Cannot check user, expecting Json data");
			return badRequest("Cannot check user, expecting Json data");
		}
		String email = json.path("email").asText();
		String password = json.path("password").asText();
		User user = userRepository.findByEmail(email);
		if (user.getPassword().equals(MD5Hashing(password))) {
			System.out.println("User is valid");
			JsonObject jsonObject = new JsonObject();
			jsonObject.addProperty("id", user.getId());
			jsonObject.addProperty("userName", user.getUserName());
            return ok(new Gson().toJson(jsonObject));
		} else {
			System.out.println("User is not valid");
			return badRequest("User is not valid");
		}
	}

	private static String MD5Hashing(String password) {

		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		md.update(password.getBytes());
		byte byteData[] = md.digest();

		//convert the byte to hex format method
		StringBuffer hexString = new StringBuffer();
		for (int i=0;i<byteData.length;i++) {
			String hex=Integer.toHexString(0xff & byteData[i]);
			if(hex.length()==1) hexString.append('0');
			hexString.append(hex);
		}

		return hexString.toString();
	}

	public Result deleteUser(Long id) {
		User deleteUser = userRepository.findOne(id);
		if (deleteUser == null) {
			System.out.println("User not found with id: " + id);
			return notFound("User not found with id: " + id);
		}

		userRepository.delete(deleteUser);
		System.out.println("User is deleted: " + id);
		return ok("User is deleted: " + id);
	}

	public Result setProfile(long id) {
		JsonNode json = request().body().asJson();
		if (json == null) {
			System.out.println("User not saved, expecting Json data");
			return badRequest("User not saved, expecting Json data");
		}

		// Parse JSON file
	    String email = json.path("email").asText();
	    String phoneNumber = json.path("phoneNumber").asText();
		try {
			User updateUser = userRepository.findOne(id);

			updateUser.setEmail(email);
			updateUser.setPhoneNumber(phoneNumber);
			
			User savedUser = userRepository.save(updateUser);
			System.out.println("User updated: " + savedUser.getEmail());
			return created("User updated: " + savedUser.getEmail());
		} catch (PersistenceException pe) {
			pe.printStackTrace();
			System.out.println("User not updated: " + email);
			return badRequest("User not updated: " + email);
		}
	}

	public Result getProfile(Long id, String format) {
		if (id == null) {
			System.out.println("User id is null or empty!");
			return badRequest("User id is null or empty!");
		}

		User user = userRepository.findOne(id);

		if (user == null) {
			System.out.println("User not found with with id: " + id);
			return notFound("User not found with with id: " + id);
		}
		String result = new String();
		if (format.equals("json")) {
			JsonObject jsonObject = new JsonObject();
			jsonObject.addProperty("id", user.getId());
			jsonObject.addProperty("userName", user.getUserName());
			jsonObject.addProperty("email", user.getEmail());
			result = new Gson().toJson(jsonObject);
		}

		return ok(result);
	}
	
	public Result getAllUsers(String format) {
		Iterable<User> userIterable = userRepository.findAll();
		List<User> userList = new ArrayList<User>();
		for (User user : userIterable) {
			userList.add(user);
		}
		String result = new String();
		if (format.equals("json")) {
			result = new Gson().toJson(userList);
		}
		return ok(result);
	}
	
	public Result deleteUserByUserNameandPassword(String userName, String password) {
		try {
			List<User> users = userRepository.findByUserName(userName);
			if (users.size()==0) {
				System.out.println("User is not existed");
				return badRequest("User is not existed");
			}
			User user = users.get(0);
			if (user.getPassword().equals(password)) {
				System.out.println("User is deleted: "+user.getId());
				userRepository.delete(user);
				return ok("User is deleted");
			}
			else {
				System.out.println("User is not deleted for wrong password");
				return badRequest("User is not deleted for wrong password");
			}
		}
		catch (PersistenceException pe) {
			pe.printStackTrace();
			System.out.println("User is not deleted");
			return badRequest("User is not deleted");
		}
	}

	public Result userSearch(String display_name, String format) {
		if (display_name == null) {
			System.out.println("Display name is null or empty!");
			return badRequest("Display name is null or empty!");
		}

		List<User> users = userRepository.getUserByDisplayName(display_name);
		for (User user: users) {
			user.setPassword("****");
		}


		if (users == null) {
			System.out.println("User not found with with display name: " + display_name);
			return notFound("User not found with with display name: " + display_name);
		}
		String result = new String();
		if (format.equals("json")) {
			result = new Gson().toJson(users);
		}

		return ok(result);
	}

	public Result userFollow(Long userId, Long followeeId){
		try{
			if(userId==null){
				System.out.println("Follower id is null or empty!");
				return badRequest("Follower id is null or empty!");
			}
			User user = userRepository.findOne(userId);
            if(user==null){
                return badRequest("Follower is not existed");
            }


			if(followeeId==null){
				System.out.println("Followee id is null or empty!");
				return badRequest("Followee id is null or empty!");
			}
			User followee = userRepository.findOne(followeeId);
            if(followee==null){
                return badRequest("Followee is not existed");
            }

            Set<User> followers = followee.getFollowers();
            followers.add(user);
            followee.setFollowers(followers);

            userRepository.save(followee);
			return ok("Followship is established");
		} catch (Exception e){
			e.printStackTrace();
			return badRequest("Followship is not established: Follower:"+userId+"\tFollowee:"+followeeId);
		}
	}

    public Result userUnfollow(Long userId, Long followeeId){
        try{
            if(userId==null){
                System.out.println("Follower id is null or empty!");
                return badRequest("Follower id is null or empty!");
            }
            User user = userRepository.findOne(userId);
            if(user==null){
                return badRequest("Follower is not existed");
            }
            if(followeeId==null){
                System.out.println("Followee id is null or empty!");
                return badRequest("Followee id is null or empty!");
            }
            User followee = userRepository.findOne(followeeId);
            if(followee==null){
                return badRequest("Followee is not existed");
            }

			Set<User> followers = followee.getFollowers();
			for(User u : followers) {
				if(u.getId()==user.getId()) {
					followers.remove(u);
				}
			}
			followee.setFollowers(followers);

			userRepository.save(followee);
            return ok("Followship is destroyed");
        } catch (Exception e){
            e.printStackTrace();
            return badRequest("Followship is established: Follower:"+userId+"\tFollowee:"+followeeId);
        }
    }

	public Result getFollowers(Long id){
		try{
			if(id==null){
				System.out.println("User id is null or empty!");
				return badRequest("User id is null or empty");
			}
			User user = userRepository.findOne(id);
			if(user==null){
				System.out.println("Cannot find user");
				return badRequest("Cannot find user");
			}
			Set<User> followers = user.getFollowers();
			StringBuilder sb = new StringBuilder();
			sb.append("{\"followers\":");

			if(!followers.isEmpty()) {
				sb.append("[");
				for (User follower : followers) {
					sb.append(follower.toJson() + ",");
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
			return badRequest("Cannot get followers");
		}
	}

	public Result getFollowees(Long id){
		try{
			if(id==null){
				System.out.println("User id is null or empty!");
				return badRequest("User id is null or empty");
			}
			User user = userRepository.findOne(id);
			if(user==null){
				System.out.println("Cannot find user");
				return badRequest("Cannot find user");
			}
			Set<User> followees = userRepository.findByFollowerId(id);
			StringBuilder sb = new StringBuilder();

			sb.append("{\"followees\":");
			if(!followees.isEmpty()) {
				sb.append("[");
				for (User follower : followees) {
					sb.append(follower.toJson() + ",");
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
			return badRequest("Cannot get followers");
		}
	}

}
