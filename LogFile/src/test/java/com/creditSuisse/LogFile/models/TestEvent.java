package com.creditSuisse.LogFile.models;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;



@SpringBootTest(classes = Event.class)
@RunWith(MockitoJUnitRunner.class)
public class TestEvent {
	
	@Test
	public void testEventEntity() {
		Event entry = new Event("id", 1L, "APPLICATION_LOG", "12345", true);
		assertNotNull(entry.getEventId());
		assertNotNull(entry.getTimestampDifference());
		assertNotNull(entry.getHost());
		assertNotNull(entry.getType());
		assertTrue(entry.isLongEventFlag());
		
		Event entry1 = new Event();
		entry1.setEventId("id2");
		entry1.setHost("12346");
		entry1.setLongEventFlag(true);
		entry1.setTimestampDifference(2L);
		entry1.setType("APPLICATION_LOG");
		assertEquals("id2", entry1.getEventId());
		assertEquals(2L, entry1.getTimestampDifference());
		assertEquals("APPLICATION_LOG", entry1.getType());
		assertEquals("12346", entry1.getHost());
		assertEquals(true, entry1.isLongEventFlag());
	}

}
