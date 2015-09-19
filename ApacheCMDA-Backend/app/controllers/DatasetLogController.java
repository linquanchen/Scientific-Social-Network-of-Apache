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

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.persistence.PersistenceException;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.gson.Gson;

import models.Dataset;
import models.DatasetLog;
import models.DatasetLogRepository;
import models.DatasetRepository;
import play.mvc.*;

@Named
@Singleton
public class DatasetLogController extends Controller {
	
	private final DatasetLogRepository datasetLogRepository;
	private final DatasetRepository datasetRepository;
	
	@Inject
	public DatasetLogController(DatasetRepository datasetRepository, 
			DatasetLogRepository datasetLogRepository) {
		this.datasetLogRepository = datasetLogRepository;
		this.datasetRepository = datasetRepository;
	}
	
	public Result addDatasetLog() {
		JsonNode json = request().body().asJson();
    	if (json == null) {
    		System.out.println("DatasetLog not saved, expecting Json data");
			return badRequest("DatasetLog not saved, expecting Json data");
    	}
    	
    	String plotUrl = json.findPath("plotUrl").asText();
    	String dataUrl = json.findPath("dataUrl").asText();
    	long originalDatasetId = json.findPath("originalDatasetId").asLong();
    	long outputDatasetId = json.findPath("outputDatasetId").asLong();
    	long serviceExecutionLogId = json.findPath("serviceExecutionLogId").asLong();
    	long datasetId = json.findPath("datasetId").asLong();
    	
    	try {
			Dataset originalDataset = datasetRepository.findOne(originalDatasetId);
			Dataset outputDataset = datasetRepository.findOne(outputDatasetId);
			Dataset dataset = datasetRepository.findOne(datasetId);
			DatasetLog datasetLog = new DatasetLog(dataset, plotUrl, dataUrl, originalDataset, outputDataset);
			DatasetLog saveddatasetLog = datasetLogRepository.save(datasetLog);
			System.out.println("DatasetLog saved: "+ saveddatasetLog.getId());
			return created(new Gson().toJson(datasetLog.getId()));
		} catch (PersistenceException pe) {
			pe.printStackTrace();
			System.out.println("DatasetLog not created");
			return badRequest("DatasetLog Configuration not created");
		}
    	
	}
	
    public Result updateDatasetLogById(long id) {
	    JsonNode json = request().body().asJson();
		if (json == null) {
			System.out.println("DatasetLog not saved, expecting Json data");
			return badRequest("DatasetLog Configuration not saved, expecting Json data");
		}
		
    	String plotUrl = json.findPath("plotUrl").asText();
    	String dataUrl = json.findPath("dataUrl").asText();
    	long originalDatasetId = json.findPath("originalDatasetId").asLong();
    	long outputDatasetId = json.findPath("outputDatasetId").asLong();
    	long serviceExecutionLogId = json.findPath("serviceExecutionLogId").asLong();
    	long datasetId = json.findPath("datasetId").asLong();

		try {
			Dataset originalDataset = datasetRepository.findOne(originalDatasetId);
			Dataset outputDataset = datasetRepository.findOne(outputDatasetId);
			Dataset dataset = datasetRepository.findOne(datasetId);
			DatasetLog datasetLog = datasetLogRepository.findOne(id);
			datasetLog.setDataSet(dataset);
			datasetLog.setDataUrl(dataUrl);
			datasetLog.setOriginalDataset(originalDataset);
			datasetLog.setOutputDataset(outputDataset);
			datasetLog.setPlotUrl(plotUrl);
			DatasetLog savedDatasetLog = datasetLogRepository.save(datasetLog);
			
			System.out.println("DatasetLog updated: "+ savedDatasetLog.getId());
			return created("DatasetLog updated: "+ savedDatasetLog.getId());
		} catch (PersistenceException pe) {
			pe.printStackTrace();
			System.out.println("DatasetLog not saved: "+id);
			return badRequest("DatasetLog not saved: "+id);
		}			
    }

	
    public Result deleteDatasetLog(long id) {
    	DatasetLog datasetLog = datasetLogRepository.findOne(id);
    	if (datasetLog == null) {
    		System.out.println("DatasetLog not found with id: " + id);
			return notFound("DatasetLog not found with id: " + id);
    	}
    	
    	datasetLogRepository.delete(datasetLog);
    	System.out.println("DatasetLog is deleted: " + id);
		return ok("DatasetLog is deleted: " + id);
    }
    
    public Result getDatasetLog(long id, String format) {
    	DatasetLog datasetLog = datasetLogRepository.findOne(id);
    	if (datasetLog == null) {
    		System.out.println("DatasetLog not found with name: " + id);
			return notFound("DatasetLog not found with name: " + id);
    	}
    	
    	String result = new String();
    	if (format.equals("json")) {
    		result = new Gson().toJson(datasetLog);
    	}
    	
    	return ok(result);
    }

    public Result getAllDatasetLogs(String format) {
    	try {
    		Iterable<DatasetLog>datasetLogs =  datasetLogRepository.findAll();
    		String result = new String();
    		result = new Gson().toJson(datasetLogs);
    		return ok(result);
    	} catch (Exception e) {
    		return badRequest("DatasetLog not found");
    	}
    }
	
}