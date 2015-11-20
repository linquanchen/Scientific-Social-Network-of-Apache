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
            u.setUserName(user.get("userName").toString());
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

        String res_user = response.get("userName").toString();
        String res_email = response.get("email").toString();

        User user = new User();
        user.setUserName(res_user);
        user.setEmail(res_email);

        List<User> followers = ProfileController.getFollow(id, FollowType.FOLLOWER);
        List<User> followees = ProfileController.getFollow(id, FollowType.FOLLOWEE);

        return ok(profile.render(user, followers, followees));
    }
}
