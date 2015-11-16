package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import models.SearchResult;
import play.api.mvc.*;
import play.mvc.Result;
import util.APICall;
import util.Constants;
import views.html.*;
import play.mvc.Controller;
import java.util.ArrayList;
import java.util.List;

public class SearchController extends Controller{
    public static boolean notpass() {
        if (session("id") == null) {
            return true;
        }
        return false;
    }

    public static Result index() {
        if (notpass()) return redirect(routes.Application.login());
        return ok(search.render(null));
    }

    public static Result search(String category, String keywd) {
        if (notpass()) return redirect(routes.Application.login());
        String path = null;
        ArrayList<SearchResult> resArr = new ArrayList<SearchResult>();
        switch (category) {
            case "user":
                path = "users";
                ArrayNode response = (ArrayNode)APICall.callAPI(Constants.NEW_BACKEND + path + "/search/" + keywd + "/json");
                for (JsonNode n: response) {
                    SearchResult obj = new SearchResult();
                    obj.setTitle(n.get("userName").toString());
                    obj.setDesc(n.get("email").toString());
                    resArr.add(obj);
                }
                break;
            case "group":
                break;
            case "workflow":
                break;
        }


        return ok(search.render(resArr));
    }
}
