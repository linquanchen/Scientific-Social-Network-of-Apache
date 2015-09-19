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
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.metadata.ClimateService;
import play.Logger;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import scala.Console;
import util.APICall;
import util.APICall.ResponseType;
import util.Constants;
import views.html.climate.*;
import play.data.DynamicForm;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ClimateServiceController extends Controller {

	final static Form<ClimateService> climateServiceForm = Form
			.form(ClimateService.class);

	public static Result home(String email, String vfile, String dataset) {
		return ok(home.render(email, vfile, dataset));
	}

	public static Result addClimateServices() {
		return ok(addClimateServices.render(climateServiceForm));
	}

	public static Result tutorial() {
		return ok(tutorial.render());
	}

	public static Result climateServices() {
		return ok(climateServices.render(ClimateService.all(),
				climateServiceForm));
	}
	
	public static Result mostRecentlyAddedClimateServices() {
		return ok(mostRecentlyAddedServices.render(ClimateService.getMostRecentlyAdded(),
				climateServiceForm));
	}
	
	public static Result mostRecentlyUsedClimateServices() {
		return ok(mostRecentlyUsedServices.render(ClimateService.getMostRecentlyUsed(),
				climateServiceForm));
	}
	
	public static Result mostPopularClimateServices() {
		return ok(mostPopularServices.render(ClimateService.getMostPopular(),
				climateServiceForm));
	}

	public static Result newClimateService() {
		Form<ClimateService> dc = climateServiceForm.bindFromRequest();
		ObjectNode jsonData = Json.newObject();
		try {

			String originalClimateServiceName = dc.field("Name").value();
			String newClimateServiceName = originalClimateServiceName.replace(' ', '-');

			if (newClimateServiceName != null && !newClimateServiceName.isEmpty()) {
				jsonData.put("name", newClimateServiceName);
			}
			
			jsonData.put("creatorId", 1);
			jsonData.put("purpose", dc.field("Purpose").value());
			jsonData.put("url", dc.field("Url").value());
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
			Date date = new Date();
			jsonData.put("createTime", dateFormat.format(date));
			jsonData.put("scenario", dc.field("Scenario").value());
			jsonData.put("versionNo", dc.field("Version").value());
			jsonData.put("rootServiceId", dc.field("Root_Service").value());
			JsonNode response = ClimateService.create(jsonData);
			Application.flashMsg(response);
		} catch (IllegalStateException e) {
			e.printStackTrace();
			Application.flashMsg(APICall
					.createResponse(ResponseType.CONVERSIONERROR));
		} catch (Exception e) {
			e.printStackTrace();
			Application.flashMsg(APICall.createResponse(ResponseType.UNKNOWN));
		}
		return redirect("/climate/climateServices");
	}

	public static Result editClimateService() {

		ObjectNode jsonData = Json.newObject();
		try {
			DynamicForm df = DynamicForm.form().bindFromRequest();
			String climateServiceName = df.field("pk").value();

			if (climateServiceName != null && !climateServiceName.isEmpty()) {
				jsonData.put("name", climateServiceName);
			}
			ClimateService originalService = ClimateService.findServiceByName(climateServiceName);
			
			if (originalService == null) {
				Application.flashMsg(APICall.createResponse(ResponseType.UNKNOWN));
				return notFound("not found original climateService " + climateServiceName);
			}

			jsonData.put("creatorId", 1);
			jsonData.put("purpose", originalService.getPurpose());
			jsonData.put("url", originalService.getUrl());
			jsonData.put("scenario", originalService.getScenario());
			jsonData.put("versionNo", originalService.getVersion());
			
			if (originalService.getRootservice() != null)
				
				jsonData.put("rootServiceId", originalService.getRootservice());
			String editField = df.field("name").value();
			
			if (editField != null && !editField.isEmpty()) {
				jsonData.put(editField, df.field("value").value());
			}
			
			if (editField == null || editField.isEmpty()) {
				Application.flashMsg(APICall.createResponse(ResponseType.UNKNOWN));
				return notFound("not found edit field");
			}

			JsonNode response = ClimateService.edit(climateServiceName, jsonData);
			Application.flashMsg(response);

		} catch (IllegalStateException e) {
			e.printStackTrace();
			Application.flashMsg(APICall
					.createResponse(ResponseType.CONVERSIONERROR));
		} catch (Exception e) {
			e.printStackTrace();
			Application.flashMsg(APICall.createResponse(ResponseType.UNKNOWN));
		}
		return ok("updated");

	}


	public static Result deleteClimateService() throws UnsupportedEncodingException {
		DynamicForm df = DynamicForm.form().bindFromRequest();
		String climateServiceId = df.field("idHolder").value();
		Logger.debug(climateServiceId);
		JsonNode response = ClimateService.delete(climateServiceId);
		Application.flashMsg(response);
		return redirect("/climate/climateServices");
	}


	public static Result downloadClimateService() {
		List<ClimateService> user = ClimateService.all();
		ObjectMapper mapper = new ObjectMapper();
		File file = new File("user.json");
		try {
			mapper.writeValue(file, user);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		response().setContentType("application/x-download");
		response().setHeader("Content-disposition",
				"attachment; filename=user.json");
		return ok(file);
	}

	public static Result oneService(String url) {
		return ok(oneService.render("/assets/html/" + url));
	}


}
