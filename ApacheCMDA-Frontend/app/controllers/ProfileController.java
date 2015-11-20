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
    public static boolean notpass() {
        if (session("id") == null) {
            return true;
        }
        return false;
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

        return ok(profile.render(user));
    }
}
