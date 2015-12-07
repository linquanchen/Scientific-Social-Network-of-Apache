package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.Comment;
import models.Group;
import models.Reply;
import models.Workflow;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import util.APICall;
import util.Constants;
import views.html.forum;
import views.html.workflow;
import views.html.workflow_edit;
import views.html.workflowdetail;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class WorkflowController extends Controller {
    final static Form<Workflow> f_wf = Form.form(Workflow.class);
    final static Form<Comment> f_comment = Form.form(Comment.class);
    final static Form<Reply> f_reply = Form.form(Reply.class);

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

    public static Result addComment(Long wid) {
        Form<Comment> form = f_comment.bindFromRequest();

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode jnode = mapper.createObjectNode();
        try {
            jnode.put("userID", session("id"));
            jnode.put("timestamp", new Date().getTime());
            jnode.put("workflowID", wid);
            jnode.put("Content", form.field("content").value());
        }catch(Exception e) {
            flash("error", "Form value invalid");
        }

        JsonNode commentResponse = Comment.create(jnode);
        if (commentResponse == null || commentResponse.has("error")) {
            //Logger.debug("Create Failed!");
            if (commentResponse == null) flash("error", "Create Comment error.");
            else flash("error", commentResponse.get("error").textValue());
            return redirect(routes.WorkflowController.workflowDetail(wid));
        }
        flash("success", "Create Comment successfully.");
        return redirect(routes.WorkflowController.workflowDetail(wid));
    }

    public static Result addReply(long toUserId, long commentId, long wid) {
        Form<Reply> form = f_reply.bindFromRequest();
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode jnode = mapper.createObjectNode();
        try {
            jnode.put("commentId", commentId);
            jnode.put("fromUserId", session("id"));
            jnode.put("toUserId", toUserId);
            jnode.put("timestamp", new Date().getTime());
            jnode.put("content", form.field("content").value());
            System.out.println(form.field("content").value());
        }catch(Exception e) {
            flash("error", "Form value invalid");
        }

        JsonNode replyResponse = Reply.create(jnode);
        if (replyResponse == null || replyResponse.has("error")) {
            System.out.println("Add Reply: Step four");
            if (replyResponse == null) flash("error", "Create Reply error.");
            else flash("error", replyResponse.get("error").textValue());
            return redirect(routes.WorkflowController.workflowDetail(wid));
        }
        flash("success", "Create Reply successfully.");
        return redirect(routes.WorkflowController.workflowDetail(wid));
    }

    public static Result replyReply(long toUserId, long replyId, long wid) {
        Form<Reply> form = f_reply.bindFromRequest();
        System.out.println("Step One");

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode jnode = mapper.createObjectNode();
        try {
            jnode.put("replyId", replyId);
            jnode.put("fromUserId", session("id"));
            jnode.put("toUserId", toUserId);
            jnode.put("timestamp", new Date().getTime());
            jnode.put("content", form.field("content").value());
        }catch(Exception e) {
            flash("error", "Form value invalid");
        }
        System.out.println("Step Two");
        System.out.println(jnode);
        JsonNode replyResponse = Reply.createReply(jnode);
        if (replyResponse == null || replyResponse.has("error")) {

            if (replyResponse == null) flash("error", "Create Reply error.");
            else flash("error", replyResponse.get("error").textValue());
            return redirect(routes.WorkflowController.workflowDetail(wid));
        }
        flash("success", "Create Reply successfully.");
        return redirect(routes.WorkflowController.workflowDetail(wid));
    }


    public static Result workflowDetail(Long wid) {

        JsonNode wfres = APICall.callAPI(Constants.NEW_BACKEND + "workflow/get/workflowID/"
                + wid.toString() + "/userID/" + session("id") + "/json");

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

        JsonNode commentList = APICall.callAPI(Constants.NEW_BACKEND + "workflow/getComments/"
                + wid.toString());

        List<Comment> commentRes = new ArrayList<>();
        List<List<Reply>> replyRes = new ArrayList<>();

        for (int i = 0; i < commentList.size(); i++) {
            JsonNode node = commentList.get(i);
            Comment comment = new Comment(node);
            commentRes.add(comment);
            Long commentId = comment.getId();
            JsonNode replyList = APICall.callAPI(Constants.NEW_BACKEND + "Comment/getReply/"
                    + commentId.toString());
            List<Reply> listReply = new ArrayList<>();
            for (int j = 0; j < replyList.size(); j++) {
                JsonNode rNode = replyList.get(j);
                Reply reply = new Reply(rNode);
                listReply.add(reply);
            }
            replyRes.add(listReply);
        }

        return ok(workflowdetail.render(wf, commentRes, replyRes, session("username"), Long.parseLong(session("id"))));
    }

    public static Result edit(Long wid)
    {
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
        return ok(workflow_edit.render(wf, session("username"), Long.parseLong(session("id"))));
    }

    public static Result addTag(Long wid, String tag)
    {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode jnode = mapper.createObjectNode();
        try {
            jnode.put("workflowID", wid.toString());
            jnode.put("tags", tag);
        }catch(Exception e) {
            flash("error", "Form value invalid");
        }
        String addTag = Constants.NEW_BACKEND + "workflow/setTag";
        JsonNode response = APICall.postAPI(addTag, jnode);

        if (response == null || response.has("error")) {
            if (response == null) flash("error", "add tag error.");
            else flash("error", response.get("error").textValue());
            return redirect(routes.WorkflowController.workflowDetail(wid));
        }
        flash("success", "Add workflow tag successfully.");
        return redirect(routes.WorkflowController.workflowDetail(wid));
    }

    public static Result deleteTag(Long wid, String tag)
    {
        String query = Constants.NEW_BACKEND + "workflow/deleteTag/workflowId/" + wid.toString() + "/tag/" + tag;
        JsonNode response = APICall.callAPI(query);

        if (response == null || response.has("error")) {
            if (response == null) flash("error", "delete tag error.");
            else flash("error", response.get("error").textValue());
            return redirect(routes.WorkflowController.workflowDetail(wid));
        }
        flash("success", "Delete workflow tag successfully.");
        return redirect(routes.WorkflowController.workflowDetail(wid));
    }


    public static Result editFlow(Long wid) {
        Form<Workflow> form = f_wf.bindFromRequest();
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode jnode = mapper.createObjectNode();

        try {
            jnode.put("wfID", wid.toString());
            jnode.put("uid", session("id"));
            jnode.put("wfTitle", form.field("wfTitle").value());
            jnode.put("wfCategory", form.field("wfCategory").value());
            jnode.put("wfCode", form.field("wfCode").value());
            jnode.put("wfDesc", form.field("wfDesc").value());
        }catch(Exception e) {
            flash("error", "Form value invalid");
        }
        JsonNode wfresponse = Workflow.update(jnode);

        if (wfresponse == null || wfresponse.has("error")) {
            if (wfresponse == null) flash("error", "Create workflow error.");
            else flash("error", wfresponse.get("error").textValue());
            return redirect(routes.WorkflowController.main());
        }
        flash("success", "Update workflow successfully.");
        return redirect(routes.WorkflowController.main());
    }


    public static Result createFlow() {
        Form<Workflow> form = f_wf.bindFromRequest();

        Http.MultipartFormData body = request().body().asMultipartFormData();
        Http.MultipartFormData.FilePart image = body.getFile("image");
        String imgPathToSave = "";
        if (image != null) {
            String fileName = image.getFilename();
            String contentType = image.getContentType();
            java.io.File file = image.getFile();
            String ext = FilenameUtils.getExtension(fileName);
            imgPathToSave = "public/images/" + "image_" + UUID.randomUUID() + "." + ext;
            boolean success = new File("images").mkdirs();
            try {
                byte[] bytes = IOUtils.toByteArray(new FileInputStream(file));
                FileUtils.writeByteArrayToFile(new File(imgPathToSave), bytes);
            } catch (IOException e) {
                imgPathToSave = "public/images/service.jpeg";
            }
        } else {
            imgPathToSave = "public/images/service.jpeg";
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
            jnode.put("wfInput", form.field("wfInput").value());
            jnode.put("wfOutput", form.field("wfOutput").value());
            jnode.put("wfTags", form.field("wfTag").valueOr(""));
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
