package controllers;

/**
 * Created by stain on 11/6/2015.
 */

import java.util.Iterator;
import java.util.Map.Entry;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.JsonObject;
import play.api.mvc.Request;
import play.libs.Json;
import play.mvc.*;
import views.html.*;
import play.data.*;
import util.APICall;
import util.Constants;
import util.APICall.ResponseType;
import play.Logger;
import models.User;

public class SignupController  extends Controller {
    final static Form<User> f_user = Form.form(User.class);

    public static Result signUp() {
        return ok(signup.render());
    }

    public static Result register() {
        Form<User> form = f_user.bindFromRequest();
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
