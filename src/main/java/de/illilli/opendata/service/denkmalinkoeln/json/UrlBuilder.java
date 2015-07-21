package de.illilli.opendata.service.denkmalinkoeln.json;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class UrlBuilder {

	// String nominatimUrl = "http://nominatim.openstreetmap.org/search";
	String geocodingUrl = "http://localhost:8080/denkmalgeocoding/service/";

	String spaceCharakter = "%20";
	String charSet = "UTF-8";

	private StringBuilder url;

	public UrlBuilder(int postcode, String city, String street,
			String housenumber) throws UnsupportedEncodingException {
		url = new StringBuilder(geocodingUrl);

		url.append(Integer.toString(postcode));
		url.append("/");
		url.append(URLEncoder.encode(city.trim(), charSet));
		url.append("/");
		url.append(URLEncoder.encode(street.trim(), charSet));
		url.append("/");
		url.append(URLEncoder.encode(housenumber.trim(), charSet));

	}

	public URL getUrl() throws MalformedURLException {
		return new URL(url.toString());
	}
}
