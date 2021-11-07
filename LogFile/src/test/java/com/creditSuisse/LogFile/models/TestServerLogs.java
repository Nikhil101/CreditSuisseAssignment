package com.creditSuisse.LogFile.models;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = TestServerLogs.class)
@RunWith(MockitoJUnitRunner.class)
public class TestServerLogs {
	
	@Test
	public void testServerLogsPojo() {
		ServerLogsPOJO serverLogsPOJO = new ServerLogsPOJO();
		serverLogsPOJO.setHost("123");
		serverLogsPOJO.setId("id");
		serverLogsPOJO.setState("STARTED");
		serverLogsPOJO.setTimestamp(1L);
		serverLogsPOJO.setType("APPLICATION_LOG");
		
		assertNotNull(serverLogsPOJO.getHost());
		assertNotNull(serverLogsPOJO.getId());
		assertNotNull(serverLogsPOJO.getState());
		assertNotNull(serverLogsPOJO.getTimestamp());
		assertNotNull(serverLogsPOJO.getType());
	}

}
