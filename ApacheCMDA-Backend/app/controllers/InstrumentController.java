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

import java.util.Date;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.persistence.PersistenceException;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.gson.Gson;

import models.Instrument;
import models.InstrumentRepository;
import play.mvc.*;

@Named
@Singleton
public class InstrumentController extends Controller {
	
	private final InstrumentRepository instrumentRepository;
	
	@Inject
	public InstrumentController(InstrumentRepository instrumentRepository) {
		this.instrumentRepository = instrumentRepository;
	}
	
	public Result addInstrument() {
		JsonNode json = request().body().asJson();
    	if (json == null) {
    		System.out.println("Instrument not saved, expecting Json data");
			return badRequest("Instrument not saved, expecting Json data");
    	}
    	String name = json.findPath("name").asText();
    	String description = json.findPath("description").asText();
    	long launchDateNumber = json.findPath("launchDate").asLong();
    	Date launchDate = new Date(launchDateNumber);
    	try {
			Instrument instrument = new Instrument(name, description,launchDate);
			Instrument savedinstrument = instrumentRepository.save(instrument);
			System.out.println("Instrument saved: "+ savedinstrument.getId());
			return created(new Gson().toJson(instrument.getId()));
		} catch (PersistenceException pe) {
			pe.printStackTrace();
			System.out.println("Instrument not created");
			return badRequest("Instrument Configuration not created");
		}
    	
	}
	
    public Result updateInstrumentById(long id) {
    	if (id < 0) {
    		System.out.println("id is negative!");
			return badRequest("id is negative!");
    	}
	    JsonNode json = request().body().asJson();
		if (json == null) {
			System.out.println("Instrument not saved, expecting Json data");
			return badRequest("Instrument Configuration not saved, expecting Json data");
		}
		long instrumentId = json.findPath("id").asLong();
		String name = json.findPath("name").asText();
    	String description = json.findPath("description").asText();
    	long launchDateNumber = json.findPath("launchDate").asLong();
    	Date launchDate = new Date(launchDateNumber);
		try {
			Instrument instrument = instrumentRepository.findOne(instrumentId);
			instrument.setDescription(description);
			instrument.setLaunchDate(launchDate);
			instrument.setName(name);
			Instrument savedInstrument = instrumentRepository.save(instrument);
			
			System.out.println("Instrument updated: "+ savedInstrument.getId());
			return created("Instrument updated: "+ savedInstrument.getId());
		} catch (PersistenceException pe) {
			pe.printStackTrace();
			System.out.println("Instrument not saved: "+id);
			return badRequest("Instrument not saved: "+id);
		}			
    }

	
    public Result deleteInstrument(long id) {
    	if (id < 0) {
    		System.out.println("id is negative!");
			return badRequest("id is negative!");
    	}
    	Instrument instrument = instrumentRepository.findOne(id);
    	if (instrument == null) {
    		System.out.println("Instrument not found with id: " + id);
			return notFound("Instrument not found with id: " + id);
    	}
    	
    	instrumentRepository.delete(instrument);
    	System.out.println("Instrument is deleted: " + id);
		return ok("Instrument is deleted: " + id);
    }
    
    public Result getInstrument(long id, String format) {
    	if (id < 0) {
    		System.out.println("id is negative!");
			return badRequest("id is negative!");
    	}
    	Instrument instrument = instrumentRepository.findOne(id);
    	if (instrument == null) {
    		System.out.println("Instrument not found with name: " + id);
			return notFound("Instrument not found with name: " + id);
    	}
    	
    	String result = new String();
    	if (format.equals("json")) {
    		result = new Gson().toJson(instrument);
    	}
    	
    	return ok(result);
    }

    
    public Result getAllInstruments(String format) {
    	try {
    		Iterable<Instrument>instruments =  instrumentRepository.findAll();
    		String result = new String();
    		result = new Gson().toJson(instruments);
    		return ok(result);
    	} catch (Exception e) {
    		return badRequest("Service Configurations not found");
    	}
    }
	
}