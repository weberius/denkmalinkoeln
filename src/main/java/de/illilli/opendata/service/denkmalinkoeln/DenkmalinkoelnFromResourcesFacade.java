package de.illilli.opendata.service.denkmalinkoeln;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

public class DenkmalinkoelnFromResourcesFacade implements Facade {

	private static final Logger logger = Logger
			.getLogger(DenkmalinkoelnFromResourcesFacade.class);

	private String json = "{}";

	public DenkmalinkoelnFromResourcesFacade() throws MalformedURLException,
			IOException {
		InputStream inputStream = this.getClass().getResourceAsStream(
				"/denkmalinkoelnAll.json");
		json = IOUtils.toString(inputStream, "UTF-8");
	}

	@Override
	public String getJson() {
		return json;
	}
}
