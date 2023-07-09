package com.coordsafe.locator.decorator;

import java.math.BigDecimal;

import org.displaytag.decorator.TableDecorator;

import com.coordsafe.constants.Constants;
import com.coordsafe.core.rbac.entity.User;
import com.coordsafe.locator.entity.Locator;
import com.google.code.geocoder.Geocoder;
import com.google.code.geocoder.model.GeocodeResponse;
import com.google.code.geocoder.model.GeocoderRequest;
import com.google.code.geocoder.model.LatLng;

public class LocatorSearchDecorator extends TableDecorator {
	public String getAddress() {
		Locator locator = (Locator) getCurrentRowObject();
		Geocoder geoCoder = new Geocoder();
		GeocoderRequest gr = new GeocoderRequest();

		if (locator.getGpsLocation() != null) {
			gr.setLocation(new LatLng(new BigDecimal(locator.getGpsLocation()
					.getLatitude()), new BigDecimal(locator.getGpsLocation()
					.getLongitude())));
			GeocodeResponse gResponse = geoCoder.geocode(gr);
			if (!gResponse.getResults().isEmpty()) {
				String address = gResponse.getResults().get(0)
						.getFormattedAddress();

				return address;
			}
		}

		return "NA";
	}

	public String getStatus() {
		Locator locator = (Locator) getCurrentRowObject();
		return "On";
	}

	public String getAction() {
		Locator locator = (Locator) getCurrentRowObject();

		String spaces = "&nbsp&nbsp";

		String location = "<a href=\""
				+ getPageContext().getServletContext().getContextPath()
				+ "/locator/locate?imeiCode=" + locator.getImeiCode()
				+ "\" title=\"Locate on the map\"><img src=\""
				+ getPageContext().getServletContext().getContextPath()
				+ Constants.RESOURCEIMAGEPATH + "/locator/maps.png\"></a>";
		String deleteLocator = "<a href=\""
				+ getPageContext().getServletContext().getContextPath()
				+ "/locator/delete?imeiCode=" + locator.getImeiCode()
				+ "\" title=\"Delete Locator\"><img src=\""
				+ getPageContext().getServletContext().getContextPath()
				+ Constants.RESOURCEIMAGEPATH + "/locator/delete.png\"></a>";

		return location + deleteLocator;
	}
}
