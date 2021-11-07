package com.creditSuisse.LogFile.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import com.creditSuisse.LogFile.dao.EventRepository;
import com.creditSuisse.LogFile.models.Event;


@SpringBootTest(classes = EventLogService.class)
@RunWith(MockitoJUnitRunner.class)
public class TestEventLogService {
	@Mock
	EventRepository eventRepositoryMock;

	@InjectMocks
	EventLogService eventLogService;

	@Test
	public void testParseAndStoreEvents() {
		String absolutePathFile = new File("src/test/resources/testInput.txt").getAbsolutePath();
		Event eventEntity = new Event();
		eventEntity.setEventId("scsmbstgra");
		eventEntity.setHost("12345");
		eventEntity.setType("APPLICATION_LOG");
		eventEntity.setLongEventFlag(true);
		eventEntity.setTimestampDifference(5);
		Mockito.when(eventRepositoryMock.save(eventEntity)).thenReturn(eventEntity);
		eventLogService.parseAndStoreEvents(absolutePathFile);
	}
	
	@Test
	public void testPrintAllDatabaseEvents() {
		List<Event> eventList=new ArrayList<>();
		Event e1=new Event("scsmbstgrz", 2, "APPLICATION_LOG", "12345", false);
		eventList.add(e1);
		Mockito.when(eventRepositoryMock.findAll()).thenReturn(eventList);
		eventLogService.printAllDatabaseEvents();
		}

	

}
