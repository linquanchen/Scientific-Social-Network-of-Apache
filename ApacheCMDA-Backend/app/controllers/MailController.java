
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

import com.fasterxml.jackson.databind.JsonNode;
import com.google.gson.Gson;
import models.*;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import util.Common;

@Named
@Singleton
public class MailController extends Controller {


    private final MailRepository mailRepository;
    private final UserRepository userRepository;

    @Inject
    public MailController(final MailRepository mailRepository,
                          UserRepository userRepository) {
        this.mailRepository = mailRepository;
        this.userRepository = userRepository;
    }

    //post: send mail
    public Result sendMail() {
        JsonNode json = request().body().asJson();
        if (json == null) {
            return Common.badRequestWrapper("mail not sent, expecting Json data");
        }

        String fromUserMail = json.path("fromUserMail").asText();
        String toUserMail = json.path("toUserMail").asText();
        User fromUser = userRepository.findByEmail(fromUserMail);
        User toUser = userRepository.findByEmail(toUserMail);
        if (fromUser == null || toUser == null) {
            Map<String, String> map = new HashMap<>();
            map.put("error", "No Access!");
            String error = new Gson().toJson(map);
            return ok(error);
        }

        String mailTitle = json.path("mailTitle").asText();
        String mailContent = json.path("mailContent").asText();
        String dateString = json.path("mailDate").asText();
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date mailDate = new Date();
        try {
            mailDate = dateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Mail mail = new Mail(fromUserMail, toUserMail, mailTitle, mailContent, mailDate);
        mailRepository.save(mail);
        return created(new Gson().toJson("success"));
    }

    //get: read mail
    public Result readMail(Long mailId) {

        Mail mail = mailRepository.findById(mailId);
        if (mail == null) {
            Map<String, String> map = new HashMap<>();
            map.put("error", "No Access!");
            String error = new Gson().toJson(map);
            return ok(error);
        }
        mail.setReadStatus(true);
        mailRepository.save(mail);

        return created(new Gson().toJson(mail));
    }

    //get: get inbox
    public Result getInbox(Long userID, String format) {
        if (userID == null) {
            return Common.badRequestWrapper("user id is null or empty!");
        }

        User user = userRepository.findOne(userID);
        String userEmail = user.getEmail();
        List<Mail> inbox = mailRepository.findByToUserMail(userEmail);

        if (inbox == null) {
            return Common.badRequestWrapper("The inbox does not exist!");
        }

        String result = new String();
        if (format.equals("json")) {
            result = new Gson().toJson(inbox);
        }

        return ok(result);
    }

    //get: get outbox
    public Result getOutbox(Long userID, String format) {
        if (userID == null) {
            return Common.badRequestWrapper("user id is null or empty!");
        }

        User user = userRepository.findOne(userID);
        String userEmail = user.getEmail();
        List<Mail> outbox = mailRepository.findByFromUserMail(userEmail);

        if (outbox == null) {
            return Common.badRequestWrapper("The outbox does not exist!");
        }

        String result = new String();
        if (format.equals("json")) {
            result = new Gson().toJson(outbox);
        }

        return ok(result);
    }
}