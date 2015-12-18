package controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.Gson;
import models.Group;
import models.SearchResult;
import models.Workflow;
import org.apache.commons.lang3.time.DateUtils;
import play.api.mvc.*;
import play.data.Form;
import play.mvc.Result;
import util.APICall;
import util.Constants;
import views.html.*;
import play.mvc.Controller;
import models.PMessage;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import models.User;


/**
 * Created by stain on 12/4/2015.
 */
public class NotificationController extends Controller {


    public static Result main()
    {
        List<User> requests = getFriendRequests();
        List<PMessage> inbox = getMail("getInbox");
        List<PMessage> outbox = getMail("getOutbox");

        return ok(notification.render(requests,inbox, outbox, session("username"), (session("id"))));
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

    private static List<User> getFriendRequests()
    {
        JsonNode response = APICall.callAPI(Constants.NEW_BACKEND + "users/getFriendRequests/userId/" + session("id"));
        ArrayList<User> requests = new ArrayList<User>();
        if (response == null || !response.has("friendRequestSender"))
        {
            flash("error", "No response from server!");
            return requests;
        }
        for (JsonNode ni : response.get("friendRequestSender") )
        {
            User obj = new User();
            JsonNode n = ni.get("User");
            obj.setUserName(n.get("userName").textValue());
            try {
                obj.setEmail(n.get("email").textValue());
            } catch (Exception e){
                obj.setEmail("");
            }
            obj.setAvatar(n.get("avatar").textValue());
            obj.setId(Long.parseLong(n.get("id").textValue()));
            requests.add(obj);
        }
        return requests;
    }

    private static List<PMessage> getMail(String type)
    {
        String apiquery = Constants.NEW_BACKEND + "mail/"+ type + "/" + session("id") + "/json";
        JsonNode response = APICall.callAPI(apiquery);
        List<PMessage> mails = new ArrayList<PMessage>();
        if (response == null)
            return mails;
        for (JsonNode n : response)
        {
            PMessage msg = new PMessage(n);
            mails.add(msg);
        }
        return mails;
    }

    final static Form<PMessage> f_msg = Form.form(PMessage.class);

    private static String getCurrentTimeStamp() {
        return DateUtils.truncate(new Date(), java.util.Calendar.DAY_OF_MONTH).toString();
    }

    public static Result sendMessage()
    {
        Form<PMessage> form = f_msg.bindFromRequest();
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode jnode = mapper.createObjectNode();

        try {
            jnode.put("fromUserMail", session("email"));
            jnode.put("toUserMail", form.field("toUserMail").value());
            jnode.put("mailTitle", form.field("mailTitle").value());
            jnode.put("mailContent", form.field("mailContent").value());
            jnode.put("mailDate", getCurrentTimeStamp());
        }catch(Exception e) {
            flash("error", "Form value invalid");
        }
        String apiquery = Constants.NEW_BACKEND + "mail/sendMail";

        JsonNode response = APICall.postAPI(apiquery, jnode);

        if (response == null || response.has("error")) {
            if (response == null) flash("error", "Mail sent error.");
            else flash("error", response.get("error").textValue());
            return redirect(routes.NotificationController.main());
        }
        flash("success", "Mail sent successfully.");
        return redirect(routes.NotificationController.main());
    }

    public static Result pmdetailpage(Long id)
    {
        String apiquery = Constants.NEW_BACKEND + "mail/getMailDetail/mailId/" + id.toString();
        JsonNode response = APICall.callAPI(apiquery);
        PMessage message = new PMessage(response);
        return ok(mail_detail.render(message, session("username"), session("id")));
    }


    public static List<Workflow> getTop3Workflow() {
        List<Workflow> result = new ArrayList<>();
        JsonNode response = APICall.callAPI(Constants.NEW_BACKEND + "workflow/getTop3WorkFlow");
        for (JsonNode n : response) {
            Workflow cur = new Workflow(n);
            result.add(cur);
        }
        return result;
    }

    public static Result getNotifications()
    {
        List<User> requests = getFriendRequests();
        List<PMessage> inbox = getMail("getInbox");

        int mailcount = 0;
        for (PMessage pm : inbox)
        {
            if (pm.isReadstatus() == false)
                mailcount++;
        }

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode jnode = mapper.createObjectNode();
        jnode.put("friendRequest", requests.size());
        jnode.put("mail", mailcount);
        return ok(jnode.toString());
    }

}
