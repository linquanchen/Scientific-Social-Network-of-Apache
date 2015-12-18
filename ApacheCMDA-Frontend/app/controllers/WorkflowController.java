package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.*;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.http.impl.conn.SystemDefaultDnsResolver;
import org.jsoup.Jsoup;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
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

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.*;

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
        }catch(Exception e) {
            flash("error", "Form value invalid");
        }

        JsonNode replyResponse = Reply.create(jnode);
        if (replyResponse == null || replyResponse.has("error")) {
            if (replyResponse == null) flash("error", "Create Reply error.");
            else flash("error", replyResponse.get("error").textValue());
            return redirect(routes.WorkflowController.workflowDetail(wid));
        }
        flash("success", "Create Reply successfully.");
        return redirect(routes.WorkflowController.workflowDetail(wid));
    }

    public static Result thumbUp(Long commentId, Long wid) {
        JsonNode res = APICall.callAPI(Constants.NEW_BACKEND + "Comment/thumbUp/"
                + commentId);
        if (res == null || res.has("error")) {
            flash("error", res.get("error").textValue());
        }
        return ok("{\"success\":\"success\"}");
    }

    public static Result thumbDown(Long commentId, Long wid) {
        JsonNode res = APICall.callAPI(Constants.NEW_BACKEND + "Comment/thumbDown/"
                + commentId);
        if (res == null || res.has("error")) {
            flash("error", res.get("error").textValue());
        }
        return ok("{\"success\":\"success\"}");
    }

    public static Result deleteWorkflow(Long wid) {
        String api = Constants.NEW_BACKEND + "workflow/deleteWorkflow";
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode jnode = mapper.createObjectNode();
        jnode.put("wfID", wid.toString());
        jnode.put("userID", session("id"));
        JsonNode response = APICall.postAPI(api, jnode);

        if (response == null || response.has("error")) {
            if (response == null) flash("error", "Delete Error.");
            else flash("error", response.get("error").textValue());
            return redirect(routes.WorkflowController.workflowDetail(wid));
        }
        flash("success", "Workflow Deleted!");
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

        JsonNode suggetionNode = APICall.callAPI(Constants.NEW_BACKEND + "suggestion/getSuggestionForWorkflow/" + wid.toString());
        List<Suggestion> suggestionList = new ArrayList<>();
        for (JsonNode n: suggetionNode) {
            Suggestion cur = new Suggestion(n);
            suggestionList.add(cur);
        }

        return ok(workflowdetail.render(wf, commentRes, replyRes,  suggestionList, session("username"), Long.parseLong(session("id"))));
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
            jnode.put("userID", session("id"));
            jnode.put("uid", session("id"));
            jnode.put("wfTitle", form.field("wfTitle").value());
            jnode.put("wfCategory", form.field("wfCategory").value());
            jnode.put("wfCode", form.field("wfCode").value());
            jnode.put("wfDesc", form.field("wfDesc").value());
            jnode.put("wfInput", form.field("wfInput").value());
            jnode.put("wfOutput", form.field("wfOutput").value());
            jnode.put("wfTags", form.field("wfTag").valueOr(""));

        }catch(Exception e) {
            flash("error", "Form value invalid");
        }
        JsonNode wfresponse = Workflow.update(jnode);
        System.out.println("response is "+ wfresponse.path("error"));

        if (wfresponse == null || wfresponse.has("error")) {
            if (wfresponse == null) flash("error", "Create workflow error.");
            else flash("error", wfresponse.get("error").textValue());
            return redirect(routes.WorkflowController.main());
        }
        flash("success", "Update workflow successfully.");
        return redirect(routes.WorkflowController.main());
    }

    private static String concatWithCommas(Collection<String> words) {
        StringBuilder wordList = new StringBuilder();
        for (String word : words) {
            wordList.append(word + ",");
        }
        return new String(wordList.deleteCharAt(wordList.length() - 1));
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
                imgPathToSave = "/" + imgPathToSave;
            } catch (IOException e) {
                imgPathToSave = "/public/images/service.jpeg";
            }
        } else {
            imgPathToSave = "/public/images/service.jpeg";
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
            jnode.put("wfUrl", form.field("wfUrl").value());
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

    public static Result addSuggestion(Long wid) {
        Form<Workflow> form = f_wf.bindFromRequest();
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode jnode = mapper.createObjectNode();

        try {
            jnode.put("wfID", wid.toString());
            jnode.put("userID", session("id"));
            jnode.put("sContent", form.field("sContent").value());
        }catch(Exception e) {
            flash("error", "Form value invalid");
        }
        JsonNode addSgstResponse = Suggestion.createSuggestion(jnode);

        if (addSgstResponse == null || addSgstResponse.has("error")) {
            if (addSgstResponse == null) flash("error", "Create suggestion error.");
            else flash("error", addSgstResponse.get("error").textValue());
            return redirect(routes.WorkflowController.main());
        }
        flash("success", "Add Suggestion successfully.");
        return redirect(routes.WorkflowController.workflowDetail(wid));
    }

    public static Result addSuggestionTag(Long suggestionID) {
        Form<Workflow> form = f_wf.bindFromRequest();
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode jnode = mapper.createObjectNode();

        try {
            jnode.put("sID", suggestionID.toString());
            jnode.put("sTag", form.field("sTag").value());
        }catch(Exception e) {
            flash("error", "Form value invalid");
        }
        JsonNode addTagtResponse = Suggestion.addTagToSuggestion(jnode);

        if (addTagtResponse == null || addTagtResponse.has("error")) {
            if (addTagtResponse == null) flash("error", "Add tag to suggestion error.");
            else flash("error", addTagtResponse.get("error").textValue());
            return redirect(routes.WorkflowController.main());
        }
        flash("success", "Add tag successfully.");
        return redirect(routes.WorkflowController.main());
    }

    public static Result voteToSuggestion(Long suggestionID) {
        System.out.println("suggestionsid is " + suggestionID);
        JsonNode voteNode = APICall.callAPI(Constants.NEW_BACKEND + "suggestion/voteToSuggestion/" + suggestionID.toString());
        System.out.println("voteNode is " + voteNode);
        if (voteNode == null || voteNode.has("error")) {
            if (voteNode == null) flash("error", "Add tag to suggestion error.");
            else flash("error", voteNode.get("error").textValue());
            return redirect(routes.WorkflowController.main());
        }
        flash("success", "Add tag successfully.");
        return ok("{\"success\":\"success\"}");
    }

    public static Result parseXML(Long id){
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode jnode = mapper.createObjectNode();
        String wfTitle = "";
        String desc= "";
        String tagstr = "";
        String code = "";
        String imgPath = "/public/images/service.jpeg";
        try {
            URL url = new URL("http://www.myexperiment.org/workflow.xml?id=" + id.toString());
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.connect();
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder= dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(con.getInputStream());
            doc.getDocumentElement().normalize();

            //get each article label information
            NodeList artList = doc.getDocumentElement().getChildNodes();
            for (int i=0; i<artList.getLength(); i++)
            {
                Node oneArticle = artList.item(i);
                if(oneArticle instanceof Element){
                    switch(oneArticle.getNodeName()){
                        case "title": wfTitle = (oneArticle.getTextContent()); break;
                        case "description":desc = (oneArticle.getTextContent()); break;
                        case "content-uri": code = (oneArticle.getTextContent()); break;
                        case "preview":
                            imgPath = (oneArticle.getTextContent()); break;
                        case "tags":
                            ArrayList<String> tags = new ArrayList<>();
                            NodeList childArt = oneArticle.getChildNodes();
                            for(int j=0;j<childArt.getLength();j++){
                                Node attr = childArt.item(j);
                                if(attr instanceof Element){
                                    tags.add(attr.getLastChild().getTextContent().trim());
                                }
                            }
                            tagstr = concatWithCommas(tags);
                    }
                }
            }
            con.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
            flash("error", "Workflow not found.");
            return redirect(routes.WorkflowController.main());
        }
        imgPath = imgPath.replaceFirst("public", "assets");
        try {
            jnode.put("userID", session("id"));
            jnode.put("wfTitle", wfTitle);
            jnode.put("wfCategory", "MyExperiment");
            jnode.put("wfCode", code);
            jnode.put("wfDesc", Jsoup.parse(desc).text());
            jnode.put("wfGroupId", 0);
            jnode.put("wfImg", imgPath);
            jnode.put("wfInput", "Please Edit This Field");
            jnode.put("wfUrl", "");
            jnode.put("wfOutput", "Please Edit This Field");
            jnode.put("wfTags", tagstr);
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
}
