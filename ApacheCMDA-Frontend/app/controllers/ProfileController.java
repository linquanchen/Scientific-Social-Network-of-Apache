package controllers;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.JsonNode;
import models.SearchResult;
import play.api.mvc.*;
import play.mvc.Result;
import util.APICall;
import util.Constants;
import views.html.*;
import play.mvc.Controller;
import java.util.ArrayList;
import java.util.List;
import models.User;

/**
 * Created by gavin on 11/19/15.
 */
public class ProfileController extends Controller {
    private static enum FollowType {
        FOLLOWEE, FOLLOWER
    }

    public static boolean notpass() {
        if (session("id") == null) {
            return true;
        }
        return false;
    }

    private static List<User> getFollow(Long id, FollowType f) {
        String queryApi = Constants.NEW_BACKEND
                + (f == FollowType.FOLLOWEE ? "users/getFollowees/" : "users/getFollowers/")
                + id.toString();
        JsonNode response = APICall.callAPI(queryApi);
        if (response.has("error"))
            return new ArrayList<User>();
        List<User> result = new ArrayList<User>();
        String key = (f == FollowType.FOLLOWEE ? "followees" : "followers");
        JsonNode arr = response.get(key);
        for (JsonNode entity: arr) {
            User u = new User();
            JsonNode user = entity.get("User");
            u.setId(Long.parseLong(user.get("id").textValue()));
            u.setUserName(user.get("userName").textValue());
            u.setEmail(user.get("email").toString());
            result.add(u);
        }
        return result;
    }

    public static Result profile(Long id) {
        if (notpass()) return redirect(routes.Application.login());
        JsonNode response = APICall.callAPI(Constants.NEW_BACKEND + "users/getprofile/" + id.toString() + "/json");
        if (response == null || response.has("error")) {
            return redirect(routes.Application.login());
        }

        String res_user = response.get("userName").textValue();
        String res_email = "";
        Long res_id = response.get("id").asLong();
        try {
            res_email = response.get("email").toString();
        } catch (Exception e) {
            res_email = "";
        }

        User user = new User();
        user.setUserName(res_user);
        user.setEmail(res_email);
        user.setId(res_id);

        List<User> followers = ProfileController.getFollow(id, FollowType.FOLLOWER);
        List<User> followees = ProfileController.getFollow(id, FollowType.FOLLOWEE);

        boolean isFollower = false;
        boolean isFollowee = false;
        Long myId = Long.parseLong(session("id"));
        for (User entry : followers)
        {
            if (entry.getId() == myId)
                isFollower = true;
        }
        for (User entry : followees)
        {
            if (entry.getId() == myId)
                isFollowee = true;
        }

        return ok(profile.render(user, followers, followees, session("username"), isFollower, isFollowee));
    }

    public static Result follow(Long id) {
        // http://localhost:9034/users/follow/followerId/110/followeeId/12
        if (notpass()) return redirect(routes.Application.login());
        String followQuery = Constants.NEW_BACKEND
                + "users/follow/followerId/"
                + session("id")
                + "/followeeId/"
                + id.toString();
        JsonNode response = APICall.callAPI(followQuery);
        if (response == null || response.has("error")) {
            return redirect(routes.Application.login());
        }
        return redirect(routes.ProfileController.profile(id));
    }

    public static Result unfollow(Long id) {
        // http://localhost:9034/users/unfollow/followerId/110/followeeId/12
        if (notpass()) return redirect(routes.Application.login());
        String unfollowQuery = Constants.NEW_BACKEND
                + "users/unfollow/followerId/"
                + session("id")
                + "/followeeId/"
                + id.toString();
        JsonNode response = APICall.callAPI(unfollowQuery);
        if (response == null || response.has("error")) {
            return redirect(routes.Application.login());
        }
        return redirect(routes.ProfileController.profile(id));
    }
}
