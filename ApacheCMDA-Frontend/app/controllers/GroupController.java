package controllers;

/**
 * Created by stain on 12/3/2015.
 */

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.Group;
import play.api.mvc.Controller;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.JsonNode;
import models.SearchResult;
import models.Workflow;
import play.api.mvc.*;
import play.data.Form;
import play.mvc.Result;
import util.APICall;
import util.Constants;
import views.html.*;
import java.util.ArrayList;
import java.util.List;
import models.User;

public class GroupController extends play.mvc.Controller {
    public static class GroupForm
    {
        public String title;
        public String linkstring;
        public GroupForm(){

        }
    }
    final static Form<GroupForm> f_group = Form.form(GroupForm.class);

    public static Result create()
    {
        JsonNode response = APICall.callAPI(Constants.NEW_BACKEND + "group/getGroupList/" + session("id") + "/json");
        ArrayList<Group> groupArr = new ArrayList<Group>();
        for (JsonNode n: response) {
            Group g = new Group(n);
            groupArr.add(g);
        }
        return ok(create_group.render(session("username"), Long.parseLong(session("id")), groupArr));
    }

    public static Result join()
    {
        return ok(join_group.render(session("username"), Long.parseLong(session("id"))));
    }

    public static Result createGroup()
    {
        Form<GroupForm> form = f_group.bindFromRequest();
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode jnode = mapper.createObjectNode();

        jnode.put("userID", session("id"));
        jnode.put("groupName", form.field("groupName").value());
        jnode.put("groupDescription", form.field("groupDescription").value());

        JsonNode res = APICall.postAPI(Constants.NEW_BACKEND + "group/createGroup", jnode);
        if (res.has("error")) {
            if (res == null)
                flash("error", "no respond");
            else
                flash("failed", res.get("error").textValue());
            return redirect(routes.GroupController.create());
        }
        String pass = res.textValue();
        flash("linkstring", pass);
        JsonNode response = APICall.callAPI(Constants.NEW_BACKEND + "group/getGroupList/" + session("id") + "/json");
        ArrayList<Group> groupArr = new ArrayList<Group>();
        for (JsonNode n: response) {
            Group g = new Group(n);
            groupArr.add(g);
        }
        return ok(create_group.render(session("username"), Long.parseLong(session("id")), groupArr));
    }

    public static Result joinGroup()
    {
        Form<GroupForm> form = f_group.bindFromRequest();

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode jnode = mapper.createObjectNode();

        jnode.put("userID", session("id"));
        jnode.put("groupUrl", form.field("groupUrl").value());

        JsonNode res = APICall.postAPI(Constants.NEW_BACKEND + "group/addMembersToGroup", jnode);
        if (res.has("error")) {
            if (res == null)
                flash("error", "no respond");
            else
                flash("failed", "Invalid Code");
            return redirect(routes.GroupController.join());
        }
        flash("success", "You have joined the group with your code!");
        return ok(join_group.render(session("username"), Long.parseLong(session("id"))));
    }

}
