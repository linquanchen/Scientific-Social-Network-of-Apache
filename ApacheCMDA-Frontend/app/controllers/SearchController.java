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

public class SearchController extends Controller{
    public static boolean notpass() {
        if (session("id") == null) {
            return true;
        }
        return false;
    }

    public static Result index() {
        if (notpass()) return redirect(routes.Application.login());
        return ok(search.render(null, null));
    }

    public static Result search(String category, String keywd) {
        if (notpass()) return redirect(routes.Application.login());
        String path = null;
        ArrayList<User> userArr = new ArrayList<User>();

        switch (category) {
            case "user":
                path = "users";
                JsonNode response = APICall.callAPI(Constants.NEW_BACKEND + path + "/search/" + keywd + "/json");
                for (JsonNode n: response) {
                    User obj = new User();
                    obj.setUserName(n.get("userName").toString());
                    try {
                        obj.setEmail(n.get("email").toString());
                    } catch (Exception e){
                        obj.setEmail("");
                    }
                    obj.setId(Long.parseLong(n.get("id").toString()));

                    userArr.add(obj);
                }
                break;
            case "group":
                break;
            case "workflow":
                break;
        }

        return ok(search.render(category, userArr));
    }
}
