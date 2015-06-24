package de.illilli.opendata.service.denkmalinkoeln;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

@Path("/service")
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
	 */
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	@Path("/")
	public String getDefaultValue() {
		return json.toString();
	}
}
