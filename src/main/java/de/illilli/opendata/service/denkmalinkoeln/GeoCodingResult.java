package de.illilli.opendata.service.denkmalinkoeln;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GeoCodingResult {

	@JsonProperty("osmId")
	public long osmId;
	@JsonProperty("lon")
	public double lon;
	@JsonProperty("lat")
	public double lat;
	@JsonProperty("display_name")
	public String displayName;

	/**
	 * Default Constructor to get everytime a syntactic value
	 */
	public GeoCodingResult() {
		osmId = 0;
		lon = 0.0;
		lat = 0.0;
		displayName = "no geo location found";
	}

	@Override
	public String toString() {
		return "GeoCodingResult [osmId=" + osmId + ", lon=" + lon + ", lat="
				+ lat + ", displayName=" + displayName + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((displayName == null) ? 0 : displayName.hashCode());
		long temp;
		temp = Double.doubleToLongBits(lat);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(lon);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + (int) (osmId ^ (osmId >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GeoCodingResult other = (GeoCodingResult) obj;
		if (displayName == null) {
			if (other.displayName != null)
				return false;
		} else if (!displayName.equals(other.displayName))
			return false;
		if (Double.doubleToLongBits(lat) != Double.doubleToLongBits(other.lat))
			return false;
		if (Double.doubleToLongBits(lon) != Double.doubleToLongBits(other.lon))
			return false;
		if (osmId != other.osmId)
			return false;
		return true;
	}

}
