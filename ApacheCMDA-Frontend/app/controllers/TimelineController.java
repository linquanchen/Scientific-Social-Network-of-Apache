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
    static int timelineSize = 0;

    public static boolean notpass() {
        if (session("id") == null) {
            return true;
        }
        return false;
    }


    public static Result main(long offset) {
        //show first page of timeline
        List<Workflow> timelines = getWorkflows(offset);;

        return ok(timeline.render(session("username"), Long.parseLong(session("id")), timelines, offset));
    }

    public static List<Workflow> getWorkflows(long offset) {
        long userID = Long.parseLong(session("id"));
        JsonNode response = APICall.callAPI(Constants.NEW_BACKEND + "workflow/getTimeline/" + userID +
                "/offset/" +offset + "/json");
        if (response == null || response.has("error")) {
            return null;
        }

        List<Workflow> timelines = new ArrayList<>();
        timelineSize = response.get("size").asInt();
        JsonNode timeline = response.get("timeline");
        for (JsonNode n: timeline) {
            Workflow workflow = new Workflow(n);
            timelines.add(workflow);
        }
        if (timelines.size() == 0) {
            timelines = null;
        }
        return timelines;
    }

}
