package de.illilli.opendata.service.denkmalinkoeln;

import java.io.IOException;
import java.net.MalformedURLException;

import org.junit.Before;
import org.junit.Test;

public class DenkmalinkoelnFacadeIntegrationTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testGetJson() throws MalformedURLException, IOException {
		Facade facade = new DenkmalinkoelnFacade();
		System.out.println(facade.getJson());
	}

}
