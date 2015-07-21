package de.illilli.opendata.service.denkmalinkoeln;

import java.io.IOException;
import java.net.MalformedURLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

@Path("/")
public class Service {

	private final static Logger logger = Logger.getLogger(Service.class);

	@Context
	private HttpServletRequest request;

	@Context
	private HttpServletResponse response;

	/**
	 * This method returns the merged data from denkmallistekoeln and
	 * denkmalgeocoding in geojson format. Therefor it needs a running
	 * nominatim-service. It is possible to return just the result from file
	 * 'denkmalinkoelnAll.json' by using the parameter 'res'. This file has to
	 * be found in the classpath of the application.
	 * <p>
	 * example without 'res':
	 * </p>
	 * <p>
	 * <a href="http://localhost:8080/denkmalinkoeln/service">http://localhost:
	 * 8080/denkmalinkoeln/service</a>
	 * </p>
	 * <p>
	 * example with 'res':
	 * </p>
	 * <p>
	 * <a href="http://localhost:8080/denkmalinkoeln/service?res">http://
	 * localhost: 8080/denkmalinkoeln/service?res</a>
	 * </p>
	 * 
	 * @return
	 * @throws IOException
	 * @throws MalformedURLException
	 */
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	@Path("/")
	public String getDefaultValue() throws MalformedURLException, IOException {
		// setze das Character-Encoding fuer die Antwort auf UTF-8
		boolean fromResource = request.getParameter("res") != null ? true
				: false;
		response.setCharacterEncoding("UTF-8");
		Facade facade;
		if (fromResource) {
			facade = new DenkmalinkoelnFromResourcesFacade();
		} else {
			facade = new DenkmalinkoelnFacade();
		}
		return facade.getJson();
	}
}
