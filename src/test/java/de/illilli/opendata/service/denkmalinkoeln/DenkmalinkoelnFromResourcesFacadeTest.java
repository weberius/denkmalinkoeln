package de.illilli.opendata.service.denkmalinkoeln;

import java.io.IOException;
import java.net.MalformedURLException;

import org.junit.Assert;
import org.junit.Test;

public class DenkmalinkoelnFromResourcesFacadeTest {

	@Test
	public void testGetJson() throws MalformedURLException, IOException {
		Facade facade = new DenkmalinkoelnFromResourcesFacade();
		String json = facade.getJson();
		boolean condition = json.contains("FeatureCollection");
		Assert.assertTrue(condition);
	}

}
