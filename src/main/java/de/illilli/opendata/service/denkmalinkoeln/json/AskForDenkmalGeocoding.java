package de.illilli.opendata.service.denkmalinkoeln.json;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.illilli.opendata.service.denkmalinkoeln.GeoCodingResult;

public class AskForDenkmalGeocoding {

	private static final Logger logger = Logger
			.getLogger(AskForDenkmalGeocoding.class);

	String url = "http://localhost:8080/denkmalgeocoding/service/";

	/**
	 * initialize Object to insure there is a valid object.
	 */
	private GeoCodingResult geoCodingResult = new GeoCodingResult();
	private String error = "";

	public AskForDenkmalGeocoding(int postcode, String city, String street,
			String housenumber) {

		try {
			URL url = new UrlBuilder(postcode, city, street, housenumber)
					.getUrl();
			InputStream inputStream = url.openStream();
			ObjectMapper mapper = new ObjectMapper();
			geoCodingResult = mapper.readValue(inputStream,
					GeoCodingResult.class);
		} catch (MalformedURLException e) {
			logger.error(e);
		} catch (UnsupportedEncodingException e) {
			logger.error(e);
		} catch (IOException e) {
			// schreibe alle problematischen Anfragen auf.
			error = e.toString();
			logger.error(e);
		}
	}

	public AskForDenkmalGeocoding(InputStream inputStream) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		geoCodingResult = mapper.readValue(inputStream, GeoCodingResult.class);
		logger.debug(geoCodingResult.toString());
	}

	public GeoCodingResult geoCodingResult() {
		return geoCodingResult;
	}

	public String getError() {
		return error;
	}

}
