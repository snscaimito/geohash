package com.testdouble.demo;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class Coordinates {

	private double longitude ;
	private double latitude ;
	
	public Coordinates(double lat, double lon) {
		longitude = lon ;
		latitude = lat ;
	}

	public static Coordinates parse(String lat, String lon) {
		double parsedLongitude = Double.parseDouble(lon) ;
		double parsedLatitude = Double.parseDouble(lat) ;
		
		return new Coordinates(parsedLatitude, parsedLongitude) ;
	}

	public double getLon() {
		return longitude ;
	}
	
	public double getLat() {
		return latitude;
	}
	
	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj, false) ;
	}
	
	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this, false) ;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this) ;
	}

}
