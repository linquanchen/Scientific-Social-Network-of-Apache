/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package controllers;

import java.util.Iterator;
import java.util.Map.Entry;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import play.api.mvc.Request;
import play.mvc.*;
import views.html.*;
import play.data.*;
import util.APICall;
import util.Constants;
import util.APICall.ResponseType;
import play.Logger;
import models.User;


public class Application extends Controller {

    public static boolean notpass() {
        if (session("id") == null) {
            return true;
        }
        return false;
    }

    public static Result index() {
        if (notpass()) return redirect(routes.Application.login());
        return ok(home.render(session("username"), session("id")));
    }

    public static class Login {

        public String username;
        public String password;

        // public String validate(){return null;}
    }

    public static Result login()
    {
        return ok(login.render(Form.form(Login.class)));
    }

    public static Result logout() {
        String curruser = session("id");
        if (curruser != null) {
            session().clear();
            return redirect(routes.Application.login());
        }
        return badRequest();
    }

    public static Result home() {
        if (notpass()) return redirect(routes.Application.login());

        return ok(home.render(session("id"), "I am Id"));
    }

    public static Result authenticate() {
        String USER_LOGIN = Constants.NEW_BACKEND + "users/login";
        Form<Login> loginForm = Form.form(Login.class).bindFromRequest();
        if (loginForm.hasErrors()) {
            return badRequest(login.render(loginForm));
        } else {
            String email = loginForm.data().get("email");
            String password = loginForm.data().get("password");
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode queryJson = mapper.createObjectNode();
            queryJson.put("email", email);
            queryJson.put("password", password);
            JsonNode response = APICall.postAPI(USER_LOGIN, queryJson);
            if (response == null || response.has("error")) {
                Logger.debug("Auth failed!");
                return redirect(routes.Application.login());
            }
            session().clear();
            session("id", response.get("id").toString());
            session("username", response.get("username").toString());
            session("email", loginForm.data().get("email"));
            return redirect(
                    routes.Application.index()
            );
        }
    }

    public static void flashMsg(JsonNode jsonNode){
        Iterator<Entry<String, JsonNode>> it = jsonNode.fields();
        while (it.hasNext()) {
            Entry<String, JsonNode> field = it.next();
            flash(field.getKey(),field.getValue().asText());
        }
    }
}