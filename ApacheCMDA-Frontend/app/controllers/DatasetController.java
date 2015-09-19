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

import com.fasterxml.jackson.databind.node.ObjectNode;
import models.DataSet;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import util.APICall;
import util.APICall.ResponseType;
import views.html.climate.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DatasetController extends Controller{
	final static Form<DataSet> dataSetForm = Form
			.form(DataSet.class);
	
	public static Result datasetList() {
		return ok(dataSetList.render(DataSet.all(),
				dataSetForm));
	}
	
	public static Result searchDataset(){
		return ok(searchDataSet.render(dataSetForm));
	}
	
	public static Result getSearchResult(){
		Form<DataSet> dc = dataSetForm.bindFromRequest();
		ObjectNode jsonData = Json.newObject();
		String dataSetName = "";
		String agency = "";
		String instrument = "";
		String physicalVariable = "";
		String gridDimension = "";
		String startTime = "";
		String endTime = "";
		Date dataSetStartTime = new Date(0), dataSetEndTime = new Date();
		
		try {
			dataSetName = dc.field("Dataset Name").value();
			agency = dc.field("Agency").value();
			instrument = dc.field("Instrument").value();
			physicalVariable = dc.field("Physical Variable").value();
			gridDimension = dc.field("Grid Dimension").value();
			startTime = dc.field("Dataset Start Time").value();
			endTime = dc.field("Dataset End Time").value();
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMM");
			
			if (!startTime.isEmpty()) {
				try {
					dataSetStartTime = simpleDateFormat.parse(startTime);
					Date min = new Date(0);
					Date max = new Date();
					if (dataSetStartTime.before(min)) {
						dataSetStartTime = min;
					} else if (dataSetStartTime.after(max)) {
						dataSetStartTime = max;
					}
				} catch (ParseException e) {
					System.out.println("Wrong Date Format :" + startTime);
					return badRequest("Wrong Date Format :" + startTime);
				}
			}
			
			if (!endTime.isEmpty()) {
				try {
					dataSetEndTime = simpleDateFormat.parse(endTime);
					Date min = new Date(0);
					Date max = new Date();
					if (dataSetEndTime.before(min)) {
						dataSetEndTime = min;
					}
					else if (dataSetEndTime.after(max)) {
						dataSetEndTime = max;
					}
				} catch (ParseException e) {
					System.out.println("Wrong Date Format :" + endTime);
					return badRequest("Wrong Date Format :" + endTime);
				}
			}
		} catch (IllegalStateException e) {
			e.printStackTrace();
			Application.flashMsg(APICall
					.createResponse(ResponseType.CONVERSIONERROR));
		} catch (Exception e) {
			e.printStackTrace();
			Application.flashMsg(APICall.createResponse(ResponseType.UNKNOWN));
		}
		List<DataSet> response = DataSet.queryDataSet(dataSetName, agency, instrument, physicalVariable, gridDimension, dataSetStartTime, dataSetEndTime);
		return ok(dataSetList.render(response, dataSetForm));
	}
}
