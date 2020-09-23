package com.testdouble.demo;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class CoordinatesTest {

	@Test
	public void parse() {
		Coordinates parsedCoordinates = Coordinates.parse("37.421542", "-122.085589") ;
		
		assertThat(parsedCoordinates.getLat()).isEqualTo(37.421542) ;
		assertThat(parsedCoordinates.getLon()).isEqualTo(-122.085589) ;
	}
}
