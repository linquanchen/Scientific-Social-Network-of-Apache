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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.persistence.PersistenceException;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.gson.Gson;

import models.ClimateService;
import models.ClimateServiceRepository;
import models.Dataset;
import models.DatasetEntryRepository;
import models.DatasetRepository;
import models.Instrument;
import models.InstrumentRepository;
import play.mvc.*;

@Named
@Singleton
public class DatasetController extends Controller {
	public static final String WILDCARD = "%";
	
	private final ClimateServiceRepository climateServiceRepository;
	private final InstrumentRepository instrumentRepository;
	private final DatasetRepository datasetRepository;
	private final DatasetEntryRepository datasetEntryRepository;
	
	@Inject
	public DatasetController(ClimateServiceRepository climateServiceRepository, InstrumentRepository instrumentRepository, DatasetRepository datasetRepository, DatasetEntryRepository datasetEntryRepository) {
		this.climateServiceRepository = climateServiceRepository;
		this.instrumentRepository = instrumentRepository;
		this.datasetRepository = datasetRepository;
		this.datasetEntryRepository = datasetEntryRepository;
	}
	
	public Result addDataset() {
		JsonNode json = request().body().asJson();
    	if (json == null) {
    		System.out.println("Dataset not saved, expecting Json data");
			return badRequest("Dataset not saved, expecting Json data");
    	}
    	String name = json.findPath("name").asText();
    	String agencyId = json.findPath("agencyId").asText();
    	long instrumentId = json.findPath("instrumentId").asLong();
    	String url = json.findPath("url").asText();
    	long publishTimeStampNumber = json.findPath("publishTimeStamp").asLong();
    	
    	long startTimeNumber = json.findPath("dataSetStartTime").asLong();
    	long endTimeNumber = json.findPath("dataSetEndTime").asLong();
    	
    	String physicalVariable = json.findPath("physicalVariable").asText();
    	String CMIP5VarName = json.findPath("CMIP5VarName").asText();
    	String units = json.findPath("units").asText();
    	String gridDimension = json.findPath("gridDimension").asText();
    	String status = json.findPath("status").asText();
    	String responsiblePerson = json.findPath("responsiblePerson").asText();
    	String variableNameInWebInterface = json.findPath("variableNameInWebInterface").asText();
    	String dataSourceInputParameterToCallScienceApplicationCode = json.findPath("dataSourceInputParameterToCallScienceApplicationCode").asText();
    	String variableNameInputParameterToCallScienceApplicationCode = json.findPath("variableNameInputParameterToCallScienceApplicationCode").asText();
    	String dataSourceNameinWebInterface = json.findPath("dataSourceNameinWebInterface").asText();
    	String comment  = json.findPath("comment").asText();
    	Date publishTimeStamp = new Date(publishTimeStampNumber);
    	
    	Date startTime = new Date(startTimeNumber);
    	Date endTime = new Date(endTimeNumber);
    	
    	JsonNode ClimateServices = json.findPath("ServiesId");
    	List<Long> climateServicesId = new ArrayList<Long>();
    	for(int i = 0; i < ClimateServices.size(); i++) {
    		climateServicesId.add(ClimateServices.get(i).asLong());
    	}
    	try {
			Instrument instrument = instrumentRepository.findOne(instrumentId);
			List<ClimateService>climateServiceSet = new ArrayList<ClimateService>();
			for(int i=0;i<climateServicesId.size();i++) {
				climateServiceSet.add(climateServiceRepository.findOne(climateServicesId.get(i)));
			}
			Dataset dataset = new Dataset(name, dataSourceNameinWebInterface, agencyId, instrument, climateServiceSet, publishTimeStamp, url, physicalVariable, CMIP5VarName, units, gridDimension, dataSourceNameinWebInterface, status, responsiblePerson, variableNameInWebInterface, dataSourceInputParameterToCallScienceApplicationCode, variableNameInputParameterToCallScienceApplicationCode, comment, startTime, endTime);
			Dataset savedServiceConfiguration = datasetRepository.save(dataset);
			System.out.println("Dataset saved: "+ savedServiceConfiguration.getId());
			return created(new Gson().toJson(dataset.getId()));
		} catch (PersistenceException pe) {
			pe.printStackTrace();
			System.out.println("Dataset not created");
			return badRequest("Dataset not created");
		}
    	
	}
	
    public Result updateDatasetById(long id) {
    	if (id < 0) {
    		System.out.println("id is negative!");
			return badRequest("id is negative!");
    	}
		JsonNode json = request().body().asJson();
		if (json == null) {
			System.out.println("Dataset not saved, expecting Json data");
			return badRequest("Dataset Configuration not saved, expecting Json data");
		}
		String name = json.findPath("name").asText();
    	String agencyId = json.findPath("agencyId").asText();
    	long instrumentId = json.findPath("instrumentId").asLong();
    	String url = json.findPath("url").asText();
    	long publishTimeStampNumber = json.findPath("publishTimeStamp").asLong();
    	
    	long startTimeNumber = json.findPath("dataSetStartTime").asLong();
    	long endTimeNumber = json.findPath("dataSetEndTime").asLong();
    	
    	String physicalVariable = json.findPath("physicalVariable").asText();
    	String CMIP5VarName = json.findPath("CMIP5VarName").asText();
    	String units = json.findPath("units").asText();
    	String gridDimension = json.findPath("gridDimension").asText();
    	String source = json.findPath("source").asText();
    	String status = json.findPath("status").asText();
    	String responsiblePerson = json.findPath("responsiblePerson").asText();
    	String variableNameInWebInterface = json.findPath("variableNameInWebInterface").asText();
    	String dataSourceInputParameterToCallScienceApplicationCode = json.findPath("dataSourceInputParameterToCallScienceApplicationCode").asText();
    	String variableNameInputParameterToCallScienceApplicationCode = json.findPath("variableNameInputParameterToCallScienceApplicationCode").asText();
    	String dataSourceNameinWebInterface = json.findPath("dataSourceNameinWebInterface").asText();
    	String comment  = json.findPath("comment").asText();
    	Date publishTimeStamp = new Date(publishTimeStampNumber);
    	
    	Date startTime = new Date(startTimeNumber);
    	Date endTime = new Date(endTimeNumber);
    	
    	JsonNode ClimateServices = json.findPath("ServiesId");
    	List<Long> climateServicesId = new ArrayList<Long>();
    	for(int i = 0; i < ClimateServices.size(); i++) {
    		climateServicesId.add(ClimateServices.get(i).asLong());
    	}
	
		try {
			Dataset dataset = datasetRepository.findOne(id);
			
			dataset.setName(name);
			dataset.setComment(comment);
			dataset.setDataSourceNameinWebInterface(dataSourceNameinWebInterface);
			dataset.setAgencyId(agencyId);
			Instrument instrument = instrumentRepository.findOne(instrumentId);
			dataset.setInstrument(instrument);
			dataset.setUrl(url);
			dataset.setPublishTimeStamp(publishTimeStamp);
			
			dataset.setStartTime(startTime);
			dataset.setEndTime(endTime);
			
			dataset.setPhysicalVariable(physicalVariable);
			dataset.setCMIP5VarName(CMIP5VarName);
			dataset.setUnits(units);
			dataset.setGridDimension(gridDimension);
			dataset.setSource(source);
			dataset.setStatus(status);
			dataset.setResponsiblePerson(responsiblePerson);
			dataset.setVariableNameInputParameterToCallScienceApplicationCode(variableNameInputParameterToCallScienceApplicationCode);
			dataset.setDataSourceInputParameterToCallScienceApplicationCode(dataSourceInputParameterToCallScienceApplicationCode);
			dataset.setVariableNameInWebInterface(variableNameInWebInterface);
			List<ClimateService>climateServiceSet = new ArrayList<ClimateService>();
			for(int i=0;i<climateServicesId.size();i++) {
				climateServiceSet.add(climateServiceRepository.findOne(climateServicesId.get(i)));
			}
			dataset.setClimateServiceSet(climateServiceSet);
			Dataset savedDataset = datasetRepository.save(dataset);
			System.out.println("Dataset updated: "+ savedDataset.getId());
			return created("Dataset updated: "+ savedDataset.getId());
		} catch (PersistenceException pe) {
			pe.printStackTrace();
			System.out.println("Dataset not saved: "+id);
			return badRequest("Dataset not saved: "+id);
		}			
    }

	
    public Result deleteDataset(long id) {
    	if (id < 0) {
    		System.out.println("id is negative!");
			return badRequest("id is negative!");
    	}
    	Dataset dataset = datasetRepository.findOne(id);
    	if (dataset == null) {
    		System.out.println("Dataset not found with id: " + id);
			return notFound("Dataset not found with id: " + id);
    	}
    	datasetRepository.delete(dataset);
    	System.out.println("Dataset is deleted: " + id);
		return ok("Dataset is deleted: " + id);
    }
    public Result getDataset(long id, String format) {
    	if (id < 0) {
    		System.out.println("id is negative!");
			return badRequest("id is negative!");
    	}
    	Dataset dataset = datasetRepository.findOne(id);
    	if (dataset == null) {
    		System.out.println("Dataset not found with name: " + id);
			return notFound("Dataset not found with name: " + id);
    	}
    	
    	String result = new String();
    	if (format.equals("json")) {
    		result = new Gson().toJson(dataset);
    	}
    	
    	return ok(result);
    }
    
    public Result queryDatasets() {
    	JsonNode json = request().body().asJson();
    	if (json == null) {
    		System.out.println("Datasets cannot be queried, expecting Json data");
    		return badRequest("Datasets cannot be queried, expecting Json data");
    	}
    	String result = new String();
    	try {
    		//Parse JSON file
    		String name = json.path("name").asText();
    		if (name.isEmpty()) {
    			name = WILDCARD;
    		}
    		else {
    			name = WILDCARD+name+WILDCARD;
    		}
    		String agencyId = json.path("agencyId").asText();
    		if (agencyId.isEmpty()) {
    			agencyId = WILDCARD;
    		}
    		else {
    			agencyId = WILDCARD+agencyId+WILDCARD;
    		}
    		String gridDimension = json.path("gridDimension").asText();
    		if (gridDimension.isEmpty()) {
    			gridDimension = WILDCARD;
    		}
    		else {
    			gridDimension = WILDCARD+gridDimension+WILDCARD;
    		}
    		String physicalVariable = json.path("physicalVariable").asText();
    		if (physicalVariable.isEmpty()) {
    			physicalVariable = WILDCARD;
    		}
    		else {
    			physicalVariable = WILDCARD+physicalVariable+WILDCARD;
    		}
    		
    		Date startTime = new Date(0);
			Date endTime = new Date();
			long startTimeNumber = json.findPath("dataSetStartTime").asLong();
			long endTimeNumber = json.findPath("dataSetEndTime").asLong();
    		
			if (startTimeNumber >= 0 ) {
				startTime = new Date(startTimeNumber);
			}
			if (endTimeNumber >= 0) {
				endTime = new Date(endTimeNumber);
			}
			
			String source = json.path("instrument").asText();
			if (source.isEmpty()) {
				source = WILDCARD;
    		}
			else {
				source = WILDCARD+source+WILDCARD;
			}
    		
    		List<Dataset> datasets;
    		if (source.isEmpty()) {
    			datasets = datasetRepository.findDataset(name, agencyId, gridDimension, physicalVariable, startTime, endTime);
    					
    		} else {
    			datasets = datasetRepository.findDatasetWithInstrument(name, agencyId, gridDimension, physicalVariable, source, startTime, endTime);
    		}
    		result = new Gson().toJson(datasets);
    	} catch (Exception e) {
    		System.out.println("ServiceExecutionLog cannot be queried, query is corrupt");
    		return badRequest("ServiceExecutionLog cannot be queried, query is corrupt");
    	}

    	return ok(result);
    }

    
    public Result getAllDatasets(String format) {
    	try {
    		Iterable<Dataset>datasets =  datasetRepository.findAll();
    		String result = new String();
    		result = new Gson().toJson(datasets);
    		return ok(result);
    	} catch (Exception e) {
    		return badRequest("Dataset not found");
    	}
    }
	
}