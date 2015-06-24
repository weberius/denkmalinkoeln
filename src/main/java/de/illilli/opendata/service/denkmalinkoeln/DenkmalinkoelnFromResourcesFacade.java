package de.illilli.opendata.service.denkmalinkoeln;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.Hashtable;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.geojson.Feature;
import org.geojson.FeatureCollection;
import org.geojson.GeoJsonObject;
import org.geojson.LngLatAlt;
import org.geojson.Point;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.illilli.opendata.service.denkmalinkoeln.json.AskForDenkmalGeocoding;
import de.illilli.opendata.service.denkmalinkoeln.json.AskForDenkmallisteKoeln;

public class DenkmalinkoelnFromResourcesFacade implements Facade {

	private static final Logger logger = Logger
			.getLogger(DenkmalinkoelnFromResourcesFacade.class);

	private String json = "{}";

	public DenkmalinkoelnFromResourcesFacade() throws MalformedURLException, IOException {
		InputStream inputStream = this.getClass().getResourceAsStream(
				"/denkmalinkoeln.json");
		json = IOUtils.toString(inputStream, "UTF-8");
	}

	public String getJson() {
		return json;
	}
}
