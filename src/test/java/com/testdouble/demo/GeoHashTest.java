package com.testdouble.demo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class GeoHashTest {

	@Mock
	private DowWorker dowWorker ;
	
	private GeoHash geoHash ;
	
	@BeforeEach
	public void setup() {
		geoHash = new GeoHash(dowWorker) ;
	}
	
	@Test
	public void calculateNewPosition() {
		final LocalDate today = LocalDate.of(2020, 9, 23) ;
		final Coordinates initialCoordinates = Coordinates.parse("37.421542", "-122.085589") ;
		
		when(dowWorker.retrieveDowValue(today)).thenReturn(new DowValue(10000.0)) ;

		assertThat(geoHash.calculateNewPosition(initialCoordinates, today)).isNotNull() ;
		assertThat(geoHash.calculateNewPosition(initialCoordinates, today))
			.isEqualTo(new Coordinates(37.14424381907851458, -122.72463020554306)) ;
	}
	
	@Test
	public void encode() {
		assertThat(geoHash.encode("2020-09-23-10000.0")).isNotNull() ;
		assertThat(geoHash.encode("2020-09-23-10000.0")).isEqualTo("c82db2ebea0eb6670a0e67552ae9a3a1") ;
	}

	@Test
	public void splitInHalf() {
		String[] halfs = geoHash.splitInHalf("c82db2ebea0eb6670a0e67552ae9a3a1") ;
		assertThat(halfs).hasSize(2) ;
		assertThat(halfs[0]).isEqualTo("c82db2ebea0eb667") ;
		assertThat(halfs[1]).isEqualTo("0a0e67552ae9a3a1") ;
	}
	
	@Test
	public void makeDecimal() {
		assertThat(geoHash.makeDecimalString("c82db2ebea0eb667")).isEqualTo("0.14424381907851458151") ;
	}
	
	@Test
	public void calculateCoordinates() {
		final String md5Value = "c82db2ebea0eb6670a0e67552ae9a3a1" ;
		
		assertThat(geoHash.calculateCoordinates(Coordinates.parse("37.421542", "-122.085589"), md5Value))
			.isNotNull() ;
		assertThat(geoHash.calculateCoordinates(Coordinates.parse("37.421542", "-122.085589"), md5Value))
			.isEqualTo(new Coordinates(37.14424381907851458, -122.72463020554306)) ;
	}
	
}
