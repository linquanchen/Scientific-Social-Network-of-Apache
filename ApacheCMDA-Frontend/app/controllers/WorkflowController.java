package controllers;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.JsonNode;
import models.SearchResult;
import models.Workflow;
import play.api.mvc.*;
import play.mvc.*;
import play.mvc.Result;
import util.APICall;
import util.Constants;
import views.html.*;
import play.mvc.Controller;
import java.util.ArrayList;
import java.util.List;
import models.User;



public class WorkflowController extends Controller {

    public static boolean notpass() {
        if (session("id") == null) {
            return true;
        }
        return false;
    }

    public static Result main() {
        return ok(workflow.render(session("username"), Long.parseLong(session("id"))));
    }

    public static Result createFlow() {
        Form<Workflow> form = f_user.bindFromRequest();

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode jnode = mapper.createObjectNode();
        jnode.put("email", form.field("email").value());
        jnode.put("password", form.field("password").value());
        jnode.put("username", form.field("username").value());
        JsonNode usernode = User.register(jnode);
        if (usernode == null || usernode.has("error")) {
            Logger.debug("Register Failed!");
            return redirect(routes.SignupController.signUp());
        }
        Logger.debug("New user created");
        return redirect(routes.Application.login());
    }


}
