package com.creditSuisse.LogFile.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import java.util.HashMap;

import java.util.Map;

import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.util.StringUtils;

import com.creditSuisse.LogFile.dao.EventRepository;
import com.creditSuisse.LogFile.models.Event;
import com.creditSuisse.LogFile.models.ServerLogsPOJO;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EventLogService {

	@Autowired
	EventRepository eventRepository;

	/**
	 * @param args - takes command line argument as file path
	 */
	public void parseAndStoreEvents(String args) {
		String fileName = args;
		Gson gson = new Gson();
		try {

			// Creating Parallel stream of data from file
			log.info("Reading file from the path to map into Java Object");
			Stream<ServerLogsPOJO> stream = Files.lines(Paths.get(fileName)).parallel().filter(StringUtils::hasText)
					.map(x -> gson.fromJson(x, ServerLogsPOJO.class));
			// Creating unique events map to store uniquely identified events from the
			// stream
			Map<String, ServerLogsPOJO> uniqueEvents = new HashMap<>();
			// Looping on every object from the stream
			log.info("Streaming over each event");
			stream.forEach(streamObj -> {
				boolean flag = false;
				long timeDifference = 0L;
				//Below condition is to check if there is invalid event based on event id
				if (streamObj.getId() != null) {
					/*
					 * Checking if there is already an event in unique events map based on Id. If
					 * not adding it to map
					 */
					if (!uniqueEvents.containsKey(streamObj.getId())) {
						uniqueEvents.put(streamObj.getId(), streamObj);
					}
					/*
					 * If key is not unique, calculate time difference and set boolean value for
					 * event alert
					 */
					else {
						ServerLogsPOJO singleServerLog = uniqueEvents.remove(streamObj.getId());
						timeDifference = Math.abs(streamObj.getTimestamp() - singleServerLog.getTimestamp());

						if (timeDifference > 4)
							flag = true;

					}

					// Mapping a new event entity to store the details in events table
					Event eventEntity = new Event();
					eventEntity.setEventId(streamObj.getId());
					eventEntity.setHost(streamObj.getHost());
					eventEntity.setType(streamObj.getType());
					eventEntity.setLongEventFlag(flag);
					eventEntity.setTimestamp(timeDifference);
					log.info("Saving the record into database");
					// Saving the record in database
					if(eventRepository.save(eventEntity)!=null) log.info("Event saved into the database - "+eventEntity.getEventId());

				}else {
					log.warn("Invalid event as Id is not availble for the event - " +streamObj+" Ignoring the event and moving to next");
				}
				
			});

		} catch (IOException | IllegalArgumentException | JsonSyntaxException e) {
			log.error("Issue with reading the file", e);
		}

	}
	public void printAllDatabaseEvents() {
		//Getting all the attributes and printing them
		log.info("Printing the values in database");
		Iterable<Event> allEvents=eventRepository.findAll();
		System.out.println("\n\n****************************Result****************************\n\n");
		allEvents.forEach(System.out::println);
		System.out.println("\n\n**************************************************************\n\n");
	}

}
