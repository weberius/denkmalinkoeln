package de.illilli.opendata.service.denkmalinkoeln;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
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
	private StringBuffer errors = new StringBuffer();

	public DenkmalinkoelnFacade() throws MalformedURLException, IOException {
		AskForDenkmallisteKoeln askforDenkmalListeKoeln = new AskForDenkmallisteKoeln();
		Denkmal[] denkmalArray = askforDenkmalListeKoeln.getDenkmalArray();
		AskForDenkmalGeocoding askforDenkmalGeocoding = null;

		for (Denkmal denkmal : denkmalArray) {
			askforDenkmalGeocoding = new AskForDenkmalGeocoding(denkmal.plz,
					"Köln", denkmal.strasse, denkmal.nummer);
			GeoCodingResult geoCoding = askforDenkmalGeocoding
					.geoCodingResult();
			errors.append(askforDenkmalGeocoding.getError() + "\n");

			if (geoCoding.osmId > 0) {
				Feature feature = new Feature();
				GeoJsonObject geometry = new Point(new LngLatAlt(geoCoding.lon,
						geoCoding.lat));
				feature.setGeometry(geometry);
				feature.setId(Long.toString(denkmal.denkmalnummer));
				Map<String, Object> properties = new Hashtable<String, Object>();
				properties.put("plz", denkmal.plz);
				properties.put("ort", denkmal.ort);
				properties.put("strasse", denkmal.strasse);
				properties.put("nummer", denkmal.nummer);
				properties.put("baujahr", denkmal.baujahr);
				properties.put("baujahrZusatz", denkmal.baujahrZusatz);
				properties.put("kurzbezeichnung", denkmal.kurzbezeichnung);
				properties.put("untergruppe1", denkmal.untergruppe1);
				properties.put("untergruppe1", denkmal.untergruppe1);
				properties.put("bezirk", denkmal.bezirk);
				feature.setProperties(properties);

				// System.out.println(denkmal.toString());
				featureCollection.add(feature);
			}
		}
		writeErrors(errors.toString());
	}

	/**
	 * this code is caught from <a href=
	 * "http://www.mkyong.com/java/how-to-write-to-file-in-java-bufferedwriter-example/"
	 * >How to write to file in Java – BufferedWriter [mkyong.com]</a>
	 * 
	 * @throws IOException
	 */
	static void writeErrors(String errors) throws IOException {
		File file = new File("errors.txt");

		// if file doesnt exists, then create it
		if (!file.exists()) {
			file.createNewFile();
		}
		FileWriter fileWriter = new FileWriter(file.getAbsoluteFile());
		BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
		bufferedWriter.write(errors);
		bufferedWriter.close();
		String msg = "file " + file.getAbsoluteFile() + " written!";
		logger.info(msg);
	}

	@Override
	public String getJson() {

		try {
			json = new ObjectMapper().writeValueAsString(featureCollection);
		} catch (JsonProcessingException e) {
			logger.error(e);
		}

		return json;
	}
}
