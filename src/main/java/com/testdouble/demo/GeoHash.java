package com.testdouble.demo;

import java.math.BigInteger;
import java.time.LocalDate;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Component;

@Component
public class GeoHash {
	private DowWorker dowWorker ;
	
	public GeoHash(DowWorker dowWorker) {
		this.dowWorker = dowWorker ;
	}
	
	public Coordinates calculateNewPosition(Coordinates initialCoordinates, LocalDate forDate) {
		DowValue dowValue = dowWorker.retrieveDowValue(forDate) ;
		
		String dowAndDateCombined = String.format("%s-%s", forDate, dowValue.getDowValue()) ;
		String md5Encoded = encode(dowAndDateCombined) ;
		
		return calculateCoordinates(initialCoordinates, md5Encoded) ;
	}

	protected Coordinates calculateCoordinates(Coordinates initialCoordinates, String md5Encoded) {
		String[] halfs = splitInHalf(md5Encoded) ;

		int latMajor = (int)initialCoordinates.getLat() ;
		int lonMajor = (int)initialCoordinates.getLon() ;
		
		String latMinor = extractMinor(makeDecimalString(halfs[0])) ;
		String lonMinor = extractMinor(makeDecimalString(halfs[1])) ;
		
		return Coordinates.parse(String.format("%s.%s", latMajor, latMinor), String.format("%s.%s", lonMajor, lonMinor));
	}

	private String extractMinor(String decimalString) {
		return decimalString.substring(decimalString.indexOf('.') + 1) ;
	}

	protected String makeDecimalString(String md5) {
		return String.format("0.%d", new BigInteger(md5, 16)) ;
	}

	protected String encode(String toBeEncoded) {
		return DigestUtils.md5Hex(toBeEncoded).toLowerCase();
	}

	protected String[] splitInHalf(String toBeSplitted) {
		int halfPosition = toBeSplitted.length() / 2 ;
		
		String firstHalf = toBeSplitted.substring(0, halfPosition) ;
		String secondHalf = toBeSplitted.substring(halfPosition) ;

		return new String[] { firstHalf, secondHalf } ;
	}

}
