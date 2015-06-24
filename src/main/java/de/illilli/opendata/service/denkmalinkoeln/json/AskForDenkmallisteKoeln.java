package de.illilli.opendata.service.denkmalinkoeln.json;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.illilli.opendata.service.denkmalinkoeln.Denkmal;

public class AskForDenkmallisteKoeln {

	String url = "http://localhost:8080/denkmallistekoeln/service";
	private static final Logger logger = Logger
			.getLogger(AskForDenkmallisteKoeln.class);
	Denkmal[] denkmalArray;

	/**
	 * Dieser Konstruktor öffnet die angegebene url.
	 * 
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	public AskForDenkmallisteKoeln() throws MalformedURLException, IOException {

		InputStream inputStream = new URL(url).openStream();
		ObjectMapper mapper = new ObjectMapper();
		denkmalArray = mapper.readValue(inputStream, Denkmal[].class);
		logger.debug(denkmalArray.toString());
	}

	/**
	 * Dieser Konstruktor erwartet ein funktionsfähigen InputStream.
	 * 
	 * @param inputStream
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public AskForDenkmallisteKoeln(InputStream inputStream)
			throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		denkmalArray = mapper.readValue(inputStream, Denkmal[].class);
		logger.debug(denkmalArray.toString());
	}

	public Denkmal[] getDenkmalArray() {
		return denkmalArray;
	}

}
