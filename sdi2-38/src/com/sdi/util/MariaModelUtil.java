package com.sdi.util;

import com.sdi.model.Waypoint;

import com.sdi.model.AddressPoint;

public class MariaModelUtil {

	public static AddressPoint AddressPointFromString(String address, 
			String city, String state, String country, String cp, 
			String coordinates) {
		String[] latLon=coordinates.split(" ");
		AddressPoint addressPoint = new AddressPoint(address, city, state,
				country, cp, new Waypoint(Double.valueOf(latLon[0]),
						Double.valueOf(latLon[1])));
		return addressPoint;
	}
}