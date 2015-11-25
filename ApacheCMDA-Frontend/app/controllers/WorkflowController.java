package controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.SearchResult;
import models.Workflow;
import play.api.mvc.*;
import play.data.Form;
import play.mvc.*;
import play.mvc.Result;
import util.APICall;
import util.Constants;
import views.html.*;
import play.mvc.Controller;
import java.util.ArrayList;
import java.util.List;
import models.User;
import play.api.Logger;



public class WorkflowController extends Controller {
    final static Form<Workflow> f_wf = Form.form(Workflow.class);

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
        Form<Workflow> form = f_wf.bindFromRequest();

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode jnode = mapper.createObjectNode();
        jnode.put("userID", session("id"));
        jnode.put("wfTitle", form.field("wfTitle").value());
        jnode.put("wfCategory", form.field("wfCategory").value());
        jnode.put("wfCode", form.field("wfCode").value());
        jnode.put("wfDesc", form.field("wfDesc").value());
        jnode.put("wfVisibility", form.field("wfVisibility").value());
        //TODO: Check the availability of the API call
        //JsonNode wfresponse = Workflow.create(jnode);
        //if (wfresponse == null || wfresponse.has("error")) {
        //    //Logger.debug("Create Failed!");
        //    return redirect(routes.SignupController.signUp());
        //}
        ////Logger.debug("New workflow created");
        return redirect(routes.WorkflowController.main());
    }


}
