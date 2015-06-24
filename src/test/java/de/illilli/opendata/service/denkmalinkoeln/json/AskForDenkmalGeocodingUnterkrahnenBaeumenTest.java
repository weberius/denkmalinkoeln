package de.illilli.opendata.service.denkmalinkoeln.json;

import java.io.InputStream;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.illilli.opendata.service.denkmalinkoeln.GeoCodingResult;

public class AskForDenkmalGeocodingUnterkrahnenBaeumenTest {

	private GeoCodingResult geoCodingResult;

	@Before
	public void setUp() throws Exception {
		InputStream inputStream = this.getClass().getResourceAsStream(
				"/unterkrahnenBaeumen.json");
		AskForDenkmalGeocoding askfor = new AskForDenkmalGeocoding(inputStream);
		geoCodingResult = askfor.geoCodingResult();
	}

	@Test
	public void testOsmId() {
		long expected = 237084005;
		long actual = geoCodingResult.osmId;
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testLat() {
		Double expected = 50.9475923;
		Double actual = geoCodingResult.lat;
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testLon() {
		Double expected = 6.95960834535519;
		Double actual = geoCodingResult.lon;
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testDisplayName() {
		String expected = "61-73, Unter Krahnenbäumen, Kuniberts-Viertel, Altstadt-Nord, Innenstadt, Köln, Regierungsbezirk Köln, Nordrhein-Westfalen, 50668, Deutschland";
		String actual = geoCodingResult.displayName;
		Assert.assertEquals(expected, actual);
	}

}
