package controllers;

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
        String userApi = "/users/search";
        String workflowApi = "/workflow/search";

        switch (category) {
            case "user":
                
                break;
            case "group":
                break;
            case "workflow":
                break;
        }
        JsonNode response = APICall.callAPI(Constants.NEW_BACKEND + "");
        ArrayList<SearchResult> resArr = new ArrayList<SearchResult>();
        return ok(search.render(resArr));
    }
}
