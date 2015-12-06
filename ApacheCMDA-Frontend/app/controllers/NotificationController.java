package controllers;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.JsonNode;
import models.Group;
import models.SearchResult;
import models.Workflow;
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
 * Created by stain on 12/4/2015.
 */
public class NotificationController extends Controller {


    public static Result main()
    {
        JsonNode response = APICall.callAPI(Constants.NEW_BACKEND + "users/getFriendRequests/userId/" + session("id"));
        if (response == null || !response.has("friendRequestSender"))
        {
            flash("error", "No response from server!");
            return ok(home.render(session("username"), session("id")));
        }
        ArrayList<User> requests = new ArrayList<User>();
        for (JsonNode ni : response.get("friendRequestSender") )
        {
            User obj = new User();
            JsonNode n = ni.get("User");
            obj.setUserName(n.get("userName").textValue());
            try {
                obj.setEmail(n.get("email").toString());
            } catch (Exception e){
                obj.setEmail("");
            }
            obj.setId(Long.parseLong(n.get("id").textValue()));
            requests.add(obj);
        }
        return ok(notification.render(requests, session("username"), (session("id"))));
    }

    public static Result accpetFriend(Long id)
    {
        String requestStr = Constants.NEW_BACKEND + "users/acceptFriendRequest/userId/"+session("id") + "/sender/" + id.toString();
        JsonNode response = APICall.callAPI(requestStr);
        if (response == null || response.has("error")) {
            flash("error", response.get("error").textValue());
            return redirect(routes.NotificationController.main());
        }
        flash("success", "You has accepted the friend request!");
        return redirect(routes.NotificationController.main());
    }

    public static Result rejectFriend(Long id)
    {
        String requestStr = Constants.NEW_BACKEND + "users/rejectFriendRequest/userId/"+session("id") + "/sender/" + id.toString();
        JsonNode response = APICall.callAPI(requestStr);
        if (response == null || response.has("error")) {
            flash("error", response.get("error").textValue());
            return redirect(routes.NotificationController.main());
        }
        flash("success", "You has rejected the friend request!");
        return redirect(routes.NotificationController.main());
    }
}
