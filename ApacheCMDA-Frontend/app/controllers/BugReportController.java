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

import models.BugReport;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.*;
import util.APICall;
import util.APICall.ResponseType;
import views.html.climate.*;
import java.util.*;

public class BugReportController extends Controller {
	final static Form<BugReport> bugReportForm = Form.form(BugReport.class);

	@play.db.jpa.Transactional
	public static Result reports() {
		return ok(bugReporting.render(bugReportForm));
	}

	@play.db.jpa.Transactional
	public static Result newReport() {
		Form<BugReport> filledForm = bugReportForm.bindFromRequest();
		BugReport report = new BugReport();
		
		try {
			report.setTitle(filledForm.get().getTitle());
			report.setName(filledForm.get().getName());
			report.setEmail(filledForm.get().getEmail());
			report.setOrganization(filledForm.get().getOrganization());
			report.setDescription(filledForm.get().getDescription());
			report.setSolved(0);
			report.save();
			return redirect(routes.BugReportController.list());
		} catch (IllegalStateException e) {
			e.printStackTrace();
			Application.flashMsg(APICall.createResponse(ResponseType.UNKNOWN));
		}	
		return ok(bugReporting.render(filledForm));
	}

	@play.db.jpa.Transactional
	public static Result list() {
		BugReport bugReport = new BugReport();
		List<Object[]> list = BugReport.getAll();
		LinkedList<BugReport> bugList = new LinkedList<BugReport>();

		for (Object[] e : list) {
			BugReport bug = new BugReport();
			bug.setId((Integer)e[0]);
			bug.setTitle(e[1].toString());
			bug.setName(e[2].toString());
			bug.setEmail(e[3].toString());
			bug.setOrganization(e[4].toString());
			bug.setDescription(e[5].toString());
			bug.setSolved((Byte)e[6]);
			bugList.add(bug);
		}
		return ok(bugs.render(bugList));
	}
	
	@play.db.jpa.Transactional
	public static Result deleteReport() {
		DynamicForm df = DynamicForm.form().bindFromRequest();
		
		try {
			int id = Integer.valueOf(df.field("idHolder").value());

			if(BugReport.delete(id)){
				Application.flashMsg(APICall.createResponse(ResponseType.SUCCESS));
			}else {
				Application.flashMsg(APICall.createResponse(ResponseType.DELETEERROR));
			}
		} catch (Exception e) {
			e.printStackTrace();
			Application.flashMsg(APICall.createResponse(ResponseType.UNKNOWN));
		}
		return redirect(routes.BugReportController.list());
	}
	
	@play.db.jpa.Transactional
	public static Result solveReport() {
		DynamicForm df = DynamicForm.form().bindFromRequest();
		
		try {
			int id = Integer.valueOf(df.field("idHolder").value());

			if(BugReport.solve(id)){
				Application.flashMsg(APICall.createResponse(ResponseType.SUCCESS));
			}else {
				Application.flashMsg(APICall.createResponse(ResponseType.RESOLVEERROR));
			}
		} catch (Exception e) {
			e.printStackTrace();
			Application.flashMsg(APICall.createResponse(ResponseType.UNKNOWN));
		}
		return redirect(routes.BugReportController.list());
	}
}