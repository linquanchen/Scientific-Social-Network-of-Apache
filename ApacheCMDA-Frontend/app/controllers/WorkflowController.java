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

    // return json
    public static Result createFlow() {
        Form<Workflow> form = f_wf.bindFromRequest();

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode jnode = mapper.createObjectNode();
        try {
            jnode.put("userID", session("id"));
            jnode.put("wfTitle", form.field("wfTitle").value());
            jnode.put("wfCategory", form.field("wfCategory").value());
            jnode.put("wfCode", form.field("wfCode").value());
            jnode.put("wfDesc", form.field("wfDesc").value());
            jnode.put("wfVisibility", form.field("wfVisibility").value());
        }catch(Exception e) {
            flash("error", "Form value invalid");
        }

        JsonNode wfresponse = Workflow.create(jnode);
        if (wfresponse == null || wfresponse.has("error")) {
            // Logger.debug("Create Failed!");
            return badRequest(wfresponse);
        } else {
            return ok(wfresponse);
        }
        // Logger.debug("New workflow created");
        // return redirect(routes.WorkflowController.main());
    }

    public Result uploadImage(Long id) {
        Http.MultipartFormData body = request().body().asMultipartFormData();
        Http.MultipartFormData.FilePart image = body.getFile("image");

        if (image != null) {
            File imgFile = image.getFile();
            String imgPathToSave = "public/images/" + "image_" + id + ".jpg";

            //save on disk
            boolean success = new File("images").mkdirs();
            try {
                byte[] bytes = IOUtils.toByteArray(new FileInputStream(imgFile));
                FileUtils.writeByteArrayToFile(new File(imgPathToSave), bytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
            workflow.setWfImg(imgPathToSave);
            workflowRepository.save(workflow);
            return ok("File uploaded");
        }
        else {
            flash("error", "Missing file");
            return badRequest("Wrong!!!!!!!!");
            // return redirect(routes.Application.index());
        }
    }

}
