package com.testdouble.demo;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/geohash")
public class GeoHashAPI {

	@Autowired
	private GeoHash geoHash ;
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Coordinates> geoHash(@RequestParam String lat, @RequestParam String lon) {
		if (StringUtils.isEmpty(lat))
			return ResponseEntity.badRequest().build();
		if (StringUtils.isEmpty(lon))
			return ResponseEntity.badRequest().build();

		Coordinates coordinates = geoHash.calculateNewPosition(Coordinates.parse(lat, lon), LocalDate.now()) ; 
		
		return ResponseEntity.ok(coordinates) ;
	}
	
}
