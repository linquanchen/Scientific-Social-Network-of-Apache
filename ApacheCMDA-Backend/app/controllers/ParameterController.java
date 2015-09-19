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

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.persistence.PersistenceException;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.gson.Gson;

import models.ClimateService;
import models.ClimateServiceRepository;
import models.Parameter;
import models.ParameterRepository;
import play.mvc.Controller;
import play.mvc.Result;

/**
 * The main set of web services.
 */
@Named
@Singleton
public class ParameterController extends Controller {
	private final ParameterRepository parameterRepository;
    private final ClimateServiceRepository climateServiceRepository;
	
	// We are using constructor injection to receive a repository to support our desire for immutability.
    @Inject
    public ParameterController(final ParameterRepository parameterRepository,
    		final ClimateServiceRepository climateServiceRepository) {
        this.parameterRepository = parameterRepository;
        this.climateServiceRepository = climateServiceRepository;
    }
    
    public Result addParameter() {
    	JsonNode json = request().body().asJson();
    	if (json == null) {
    		System.out.println("Parameter not saved, expecting Json data");
			return badRequest("Parameter not saved, expecting Json data");
    	}

    	//Parse JSON file
    	long serviceId = json.findPath("serviceId").asLong();
    	long indexInService = json.findPath("indexInService").asLong();
		String name = json.findPath("name").asText();
		String dataRange = json.findPath("dataRange").asText();
		String rule = json.findPath("rule").asText();
		String purpose = json.findPath("purpose").asText();
		
		try {
			ClimateService climateService = climateServiceRepository.findOne(serviceId);
			Parameter parameter = new Parameter(climateService, indexInService, name,
					dataRange, rule, purpose);
			Parameter savedParameter = parameterRepository.save(parameter);
			
			System.out.println("Parameter saved: " + savedParameter.getName());
			return created(new Gson().toJson(savedParameter.getId()));
		} catch (PersistenceException pe) {
			pe.printStackTrace();
			System.out.println(serviceId);
			System.out.println(pe.getClass().toString());
			System.out.println("Parameter not saved: " + name);
			return badRequest("Parameter not saved: " + name);
		}			
    }
    
    public Result deleteParameterByName(long serviceId, String name) {
    	Parameter parameter = parameterRepository.findByNameAndClimateService_Id(name,serviceId);
    	if (parameter == null) {
    		System.out.println("Parameter not found with name: " + name);
			return notFound("Parameter not found with name: " + name);
    	}
    	
    	parameterRepository.delete(parameter);
    	System.out.println("Parameter is deleted: " + name);
		return ok("Parameter is deleted: " + name);
    }
    
    public Result updateParameterByName(String oldName) {
    	JsonNode json = request().body().asJson();
    	if (json == null) {
    		System.out.println("Parameter not updated, expecting Json data");
			return badRequest("Parameter not updated, expecting Json data");
    	}

    	//Parse JSON file
    	long serviceId = json.findPath("serviceId").asLong();
    	long indexInService = json.findPath("indexInService").asLong();
		String name = json.findPath("name").asText();
		Iterator<JsonNode> elements = json.findPath("dataType").elements();
		StringBuffer dataType = new StringBuffer();
		while (elements.hasNext()) {
			dataType.append(elements.next().asText());
			dataType.append(",");
		}
		dataType.deleteCharAt(dataType.length() - 1);
		String dataRange = json.findPath("dataRange").asText();
		String rule = json.findPath("rule").asText();
		String purpose = json.findPath("purpose").asText();
		
		if (oldName == null || oldName.length() == 0) {
    		System.out.println("Parameter Name is null or empty!");
			return badRequest("Parameter Name is null or empty!");
    	}
		
		try {
			ClimateService climateService = climateServiceRepository.findOne(serviceId);
			
			Parameter parameter = parameterRepository.findByNameAndClimateService_Id(oldName, serviceId);
			parameter.setClimateService(climateService);
			parameter.setIndexInService(indexInService);
			parameter.setName(name);
			parameter.setDataRange(dataRange);
			parameter.setRule(rule);
			parameter.setPurpose(purpose);
			
			Parameter savedParameter = parameterRepository.save(parameter);
			
			System.out.println("Parameter updated: " + savedParameter.getName());
			return created("Parameter updated: " + savedParameter.getName());
		} catch (PersistenceException pe) {
			pe.printStackTrace();
			System.out.println("Parameter not updated: " + name);
			return badRequest("Parameter not updated: " + name);
		}			
    }
    
    public Result updateParameterById(long id) {
    	JsonNode json = request().body().asJson();
    	if (json == null) {
    		System.out.println("Parameter not updated, expecting Json data");
			return badRequest("Parameter not updated, expecting Json data");
    	}

    	//Parse JSON file
    	long serviceId = json.findPath("serviceId").asLong();
    	long indexInService = json.findPath("indexInService").asLong();
		String name = json.findPath("name").asText();
		Iterator<JsonNode> elements = json.findPath("dataType").elements();
		StringBuffer dataType = new StringBuffer();
		while (elements.hasNext()) {
			dataType.append(elements.next().asText());
			dataType.append(",");
		}
		dataType.deleteCharAt(dataType.length() - 1);
		String dataRange = json.findPath("dataRange").asText();
		String rule = json.findPath("rule").asText();
		String purpose = json.findPath("purpose").asText();
		
		try {
			ClimateService climateService = climateServiceRepository.findOne(serviceId);
			
			Parameter parameter = parameterRepository.findOne(id);
			parameter.setClimateService(climateService);
			parameter.setIndexInService(indexInService);
			parameter.setName(name);
			parameter.setDataRange(dataRange);
			parameter.setRule(rule);
			parameter.setPurpose(purpose);
			
			Parameter savedParameter = parameterRepository.save(parameter);
			
			System.out.println("Parameter updated: " + savedParameter.getName());
			return created("Parameter updated: " + savedParameter.getName());
		} catch (PersistenceException pe) {
			pe.printStackTrace();
			System.out.println("Parameter not updated: " + name);
			return badRequest("Parameter not updated: " + name);
		}			
    }
    
    public Result getParameterByName(long serviceId, String name, String format) {
    	if (name == null || name.length() == 0) {
    		System.out.println("Parameter Name is null or empty!");
			return badRequest("Parameter Name is null or empty!");
    	}
    	
    	Parameter parameter = parameterRepository.findByNameAndClimateService_Id(name, serviceId);
    	if (parameter == null) {
    		System.out.println("Parameter not found with name: " + name);
			return notFound("Parameter not found with name: " + name);
    	}
    	
    	String result = new String();
    	if (format.equals("json")) {
    		result = new Gson().toJson(parameter);
    	}
    	
    	return ok(result);
    }
    
    public Result getParameterById(Long id, String format) {
    	if (id < 0) {
    		System.out.println("id is negative!");
			return badRequest("id is negative!");
    	}
    	
    	Parameter parameter = parameterRepository.findOne(id);
    	if (parameter == null) {
    		System.out.println("Parameter not found with id: " + id);
			return notFound("Parameter not found with id: " + id);
    	}
    	
    	String result = new String();
    	if (format.equals("json")) {
    		result = new Gson().toJson(parameter);
    	}
    	
    	return ok(result);
    }
    
    public Result getAllParameters(String format) {
    	
    	String result = new String();
    	
    	if (format.equals("json")) {
    		result = new Gson().toJson(parameterRepository.findAll());
    	}
    			
    	return ok(result);
    }
}
