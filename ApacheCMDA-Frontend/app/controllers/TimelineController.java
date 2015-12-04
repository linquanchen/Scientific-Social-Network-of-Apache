package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.Workflow;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import util.APICall;
import util.Constants;
import views.html.workflow;
import views.html.workflowdetail;
import views.html.timeline;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class TimelineController extends Controller {
    final static Form<Workflow> f_wf = Form.form(Workflow.class);

    public static boolean notpass() {
        if (session("id") == null) {
            return true;
        }
        return false;
    }


    public static Result main(long offset) {
        //show first page of timeline
        List<Workflow> timelines = getWorkflows(offset);
        offset++;
        return ok(timeline.render(session("username"), Long.parseLong(session("id")), timelines, offset));
    }

    public static List<Workflow> getWorkflows(long offset) {
        long userID = Long.parseLong(session("id"));
        JsonNode response = APICall.callAPI(Constants.NEW_BACKEND + "workflow/getTimeline/" + userID +
                "/offset/" +offset + "/json");
        if (response == null || response.has("error")) {
            return null;
        }

        System.out.println("response is " + response);
        List<Workflow> timelines = new ArrayList<>();
        for (JsonNode n: response) {
            System.out.println("node is " + n);
            Workflow workflow = new Workflow();
            JsonNode userNode = n.get("user");
            workflow.setUserName(userNode.get("userName").textValue());
            workflow.setId(n.get("id").longValue());
            workflow.setWfTitle(n.get("wfTitle").asText());
            workflow.setWfCategory(n.get("wfCategory").asText());
            workflow.setWfCode(n.get("wfCode").asText());
            //workflow.setWfContributors(n.get("wfContributors").asText());
            workflow.setWfDesc(n.get("wfDesc").asText());
            workflow.setWfImg(n.get("wfImg").asText());
            workflow.setWfViewCount(n.get("viewCount").asLong());
            timelines.add(workflow);
        }
        if (timelines.size() == 0) {
            timelines = null;
        }
        return timelines;
    }

}
