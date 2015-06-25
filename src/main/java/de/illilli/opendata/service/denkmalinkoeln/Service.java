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

	private StringBuilder json = new StringBuilder("{}");

	/**
	 * <p>
	 * Beispiel:
	 * </p>
	 * <p>
	 * <a href="http://localhost:8080/denkmalinkoeln/service">http://localhost:
	 * 8080/denkmalinkoeln/service</a>
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
		response.setCharacterEncoding("UTF-8");
		Facade facade = new DenkmalinkoelnFromResourcesFacade();
		return facade.getJson();
	}
}
