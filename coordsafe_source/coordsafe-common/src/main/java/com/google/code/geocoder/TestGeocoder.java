package com.google.code.geocoder;

import java.math.BigDecimal;

import com.google.code.geocoder.model.GeocodeResponse;
import com.google.code.geocoder.model.GeocoderRequest;
import com.google.code.geocoder.model.LatLng;

public class TestGeocoder {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Geocoder geoCoder = new Geocoder();
		GeocoderRequest gr = new GeocoderRequest();
		gr.setLocation(new LatLng(new BigDecimal(1.2931222),new BigDecimal(103.7775598)));
		GeocodeResponse gResponse = geoCoder.geocode(gr);
		System.out.println(gResponse.getResults().get(0).getFormattedAddress());

	}

}
