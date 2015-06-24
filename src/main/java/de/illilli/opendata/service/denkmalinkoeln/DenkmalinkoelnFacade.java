package de.illilli.opendata.service.denkmalinkoeln;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Hashtable;
import java.util.Map;

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

public class DenkmalinkoelnFacade implements Facade {

	private static final Logger logger = Logger
			.getLogger(DenkmalinkoelnFacade.class);

	private String json = "{}";
	FeatureCollection featureCollection = new FeatureCollection();

	public DenkmalinkoelnFacade() throws MalformedURLException, IOException {
		AskForDenkmallisteKoeln askforDenkmalListeKoeln = new AskForDenkmallisteKoeln();
		Denkmal[] denkmalArray = askforDenkmalListeKoeln.getDenkmalArray();
		AskForDenkmalGeocoding askforDenkmalGeocoding = null;

		for (Denkmal denkmal : denkmalArray) {
			askforDenkmalGeocoding = new AskForDenkmalGeocoding(denkmal.plz,
					denkmal.ort, denkmal.strasse, denkmal.nummer);
			GeoCodingResult geoCoding = askforDenkmalGeocoding
					.geoCodingResult();

			if (geoCoding.osmId > 0) {
				Feature feature = new Feature();
				GeoJsonObject geometry = new Point(new LngLatAlt(geoCoding.lon,
						geoCoding.lat));
				feature.setGeometry(geometry);
				feature.setId(Long.toString(geoCoding.osmId));
				Map<String, Object> properties = new Hashtable<String, Object>();
				properties.put("plz", denkmal.plz);
				properties.put("ort", denkmal.ort);
				properties.put("strasse", denkmal.strasse);
				properties.put("nummer", denkmal.nummer);
				feature.setProperties(properties);

				// System.out.println(denkmal.toString());
				featureCollection.add(feature);
			}

		}

	}

	public String getJson() {

		try {
			json = new ObjectMapper().writeValueAsString(featureCollection);
		} catch (JsonProcessingException e) {
			logger.error(e);
		}

		return json;
	}
}
