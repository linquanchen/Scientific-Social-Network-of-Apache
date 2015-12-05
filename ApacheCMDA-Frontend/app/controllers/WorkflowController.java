package controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.Group;
import models.SearchResult;
import models.Workflow;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import play.api.mvc.*;
import play.data.Form;
import play.mvc.*;
import play.mvc.Result;
import util.APICall;
import util.Constants;
import views.html.*;
import play.mvc.Controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import models.User;
import play.api.Logger;
import java.io.File;
import java.io.FileInputStream;
import java.util.UUID;

public class WorkflowController extends Controller {
    final static Form<Workflow> f_wf = Form.form(Workflow.class);

    public static boolean notpass() {
        if (session("id") == null) {
            return true;
        }
        return false;
    }


    public static Result main() {
        JsonNode response = APICall.callAPI(Constants.NEW_BACKEND + "group/getGroupList/" + session("id") + "/json");
        ArrayList<Group> groupArr = new ArrayList<Group>();
        for (JsonNode n: response) {
            Group g = new Group(n);
            groupArr.add(g);
        }
        return ok(workflow.render(session("username"), Long.parseLong(session("id")), groupArr));
    }

    public static Result workflowDetail(Long wid) {
        JsonNode wfres = APICall.callAPI(Constants.NEW_BACKEND + "workflow/get/workflowID/"
                +wid.toString()+ "/userID/" + session("id") + "/json");
        if (wfres == null || wfres.has("error")) {
            flash("error", wfres.get("error").textValue());
            return redirect(routes.WorkflowController.main());
        }
        if (wfres.get("status").asText().contains("protected") || wfres.get("status").asText().contains("deleted") )
        {
            flash("error", "The workflow is protected!");
            return redirect(routes.WorkflowController.main());
        }
        Workflow wf = new Workflow(wfres);
        return ok(workflowdetail.render(wf, session("username"), Long.parseLong(session("id"))));
    }

    // return json
    public static Result createFlow() {
        Form<Workflow> form = f_wf.bindFromRequest();

        Http.MultipartFormData body = request().body().asMultipartFormData();
        Http.MultipartFormData.FilePart image = body.getFile("image");
        String imgPathToSave = "";
        if (image != null) {
            String fileName = image.getFilename();
            String contentType = image.getContentType();
            java.io.File file = image.getFile();
            imgPathToSave = "public/images/" + "image_" + UUID.randomUUID() + ".jpg";
            boolean success = new File("images").mkdirs();
            try {
                byte[] bytes = IOUtils.toByteArray(new FileInputStream(file));
                FileUtils.writeByteArrayToFile(new File(imgPathToSave), bytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            imgPathToSave = "";
        }
        imgPathToSave = imgPathToSave.replaceFirst("public", "assets");

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode jnode = mapper.createObjectNode();

        try {
            jnode.put("userID", session("id"));
            jnode.put("wfTitle", form.field("wfTitle").value());
            jnode.put("wfCategory", form.field("wfCategory").value());
            jnode.put("wfCode", form.field("wfCode").value());
            jnode.put("wfDesc", form.field("wfDesc").value());
            jnode.put("wfGroupId", form.field("wfVisibility").value());
            jnode.put("wfImg", imgPathToSave);
        }catch(Exception e) {
            flash("error", "Form value invalid");
        }

        JsonNode wfresponse = Workflow.create(jnode);
        if (wfresponse == null || wfresponse.has("error")) {
            //Logger.debug("Create Failed!");
            if (wfresponse == null) flash("error", "Create workflow error.");
            else flash("error", wfresponse.get("error").textValue());
            return redirect(routes.WorkflowController.main());
        }
        flash("success", "Create workflow successfully.");
        return redirect(routes.WorkflowController.main());
    }

    public static Result getPublicWorkflow() {
        JsonNode wfres = APICall.callAPI(Constants.NEW_BACKEND + "workflow/getPublicWorkflow/json");
        if (wfres == null || wfres.has("error")) {
            flash("error", wfres.get("error").textValue());
            return redirect(routes.WorkflowController.main());
        }

        List<Workflow> res = new ArrayList<Workflow>();
        for (int i = 0; i < wfres.size(); i++) {
            JsonNode node = wfres.get(i);
            Workflow wf = new Workflow(node);
            res.add(wf);
        }
        return ok(forum.render(res, session("username"), Long.parseLong(session("id"))));
    }

    // TODO: need a timeline page displaying the posts of followees.
    // TODO: POST and DISPLAY comment on workflow. user can reply to comments.
}
