/**
 * TripExporterTool.java
 * May 21, 2013
 * Yang Wei
 */
package com.coordsafe.job;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.coordsafe.locator.entity.LocatorLocationHistory;

public class TripExporterTool {
	private static final Log log = LogFactory.getLog(TripExporterTool.class);
	
	public static void exportGpx(List<LocatorLocationHistory> hist){
		LocatorLocationHistory history = hist.get(0);
		long locatorId = history.getLocator_id();
		Date dt = history.getLocation_time();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmm");
		
		try {
			String dtStr = sdf.format(dt);
			// file name is format: yyyyMMdd-locatorId-tripId.gpx
			// /opt/trip/gpx/
			File f = new File(File.separator + "opt" + File.separator + "trip" + File.separator 
					+ "gpx" + File.separator + dtStr + "-" + locatorId + ".gpx");
			// create parent directories if not exist
			f.getParentFile().mkdirs();
			
			//File f = new File("C:\\" + dtStr + "-" + locatorId + ".gpx");
			
			if (!f.exists())
				f.createNewFile();
				
			FileWriter fw = new FileWriter(f);			
			fw.append(MetaInfo.header);
			fw.append("\n\t");
			//fw.append(MetaInfo.trackinfo);
			fw.append(MetaInfo.copyright + "\n");
			fw.append("\n\t<trk>\n\t\t<trkseg>");
	
			for (LocatorLocationHistory his: hist){
				fw.append("\n\t\t\t<trkpt lon=\"" + his.getLng() + "\" lat=\"" + his.getLat() + "\">");
				fw.append("\n\t\t\t\t<ele>" + his.getAltitude() + "</ele>");
				fw.append("\n\t\t\t\t<time>" + his.getLocation_time() + "</time>\n\t\t\t</trkpt>");
			}
			
			fw.append("\n\t\t</trkseg>\n\t</trk>\n</gpx>");
			fw.flush();
			fw.close();
			
			log.info("finish exporting gpx: " + f.getAbsolutePath());
		} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}
	
	public static void exportTcx(){
		//TODO
	}
	
	public static void exportKml(){
		//TODO
	}
	
	static final class MetaInfo {
		static String header = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
		"<gpx version=\"1.1\" creator=\"coordsafe, http://www.coordsafe.com.sg\" xsi:schemaLocation=\"http://www.topografix.com/GPX/1/1\n" +
        "\thttp://www.topografix.com/GPX/1/1/gpx.xsd\n" +
        "\thttp://www.garmin.com/xmlschemas/GpxExtensions/v3\n" +
        "\thttp://www.garmin.com/xmlschemas/GpxExtensionsv3.xsd\n" +
        "\thttp://www.garmin.com/xmlschemas/TrackPointExtension/v1\n" +
        "\thttp://www.garmin.com/xmlschemas/TrackPointExtensionv1.xsd\"\n" +
        "\txmlns=\"http://www.topografix.com/GPX/1/1\"\n" +
        "\txmlns:gpxtpx=\"http://www.garmin.com/xmlschemas/TrackPointExtension/v1\"\n" +
        "\txmlns:gpxx=\"http://www.garmin.com/xmlschemas/GpxExtensions/v3\"\n" +
        "\txmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n";
		
		static String copyright = "<metadata>" +
		"\n\t\t<copyright author=\"www.coordsafe.com.sg\"></copyright>" +    
		"\n\t\t<time>" + new Date().toString() + "</time>" +
		"\n\t</metadata>";
		
		static String trackinfo = "<trk><link href=\"http://www.coordsafe.com.sg/trip/70539319\"></link>";
	}
}