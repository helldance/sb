package com.coordsafe.tcpgateway.codec;

import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.rmi.ServerException;
import java.util.Date;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

import com.coordsafe.tcpgateway.locator.AuthenticationMsg;
import com.coordsafe.tcpgateway.locator.CommonPayload;
import com.coordsafe.tcpgateway.locator.GpsLocation;
import com.coordsafe.tcpgateway.locator.GpsPayload;
import com.coordsafe.tcpgateway.locator.LocationMsg;
import com.coordsafe.tcpgateway.locator.LocatorMsgHeader;
import com.coordsafe.tcpgateway.locator.VerificationMsg;
import com.coordsafe.tcpgateway.locator.VerificationPayload;
import com.coordsafe.tcpgateway.locator.Version;
import com.coordsafe.tcpgateway.util.CommonConstants;

public class LocatorMsgDecoder extends CumulativeProtocolDecoder {

	@Override
	protected boolean doDecode(IoSession session, IoBuffer in,
			ProtocolDecoderOutput output) throws Exception {
		
		System.out.println("Before decoding, size " + in.remaining() + ", bytes:\n");
		
		for (byte b : in.array()){
			System.out.print(b + " ");
		}
		
		System.out.println();

		int pktsize = in.remaining();
		int actsize;

		if (pktsize < 4) {
			return false;
		} else {
			actsize = in.getInt();
		}
		
		if (in.remaining() >= actsize) {
			LocatorMsgHeader locatorHdr = new LocatorMsgHeader();
			locatorHdr.setServiceId(in.get());
			locatorHdr.setDirIndicator(in.get());
			locatorHdr.setMsgTypeId(in.get());
			Charset charset = Charset.forName("UTF-8");
			CharsetDecoder decoder = charset.newDecoder();
			locatorHdr.setImeiCode(in.getString(15, decoder));
			locatorHdr.setSerialNo(in.get());
			locatorHdr.setServerTime(in.getLong() * 1000);
			//locatorHdr.setPayloadLength(in.getShort());

			/*
			 * Decode CommonPayload
			 */
			GpsLocation gpsLocation = new GpsLocation();
			gpsLocation.setLatitude(in.getDouble());
			gpsLocation.setLongitude(in.getDouble());

			CommonPayload commonPayload = new CommonPayload();
			commonPayload.setGpsLocation(gpsLocation);
			//commonPayload.setStatus(in.getShort());
			commonPayload.setGprs(in.get());
			commonPayload.setGps(in.get());
			commonPayload.setPower(in.get());
			commonPayload.setOther(in.get());

			switch (locatorHdr.getMsgTypeId()) {
			case CommonConstants.AUTH_MSG:
				AuthenticationMsg authMsg = new AuthenticationMsg();
				authMsg.setLocatorMsgHeader(locatorHdr);
				authMsg.setCommonPayload(commonPayload);
				output.write(authMsg);
				break;
			case CommonConstants.VER_MSG:
				VerificationPayload veriPayload = new VerificationPayload(
						new Version(in.get(), in.get()), new Version(in.get(),
								in.get()), new Version(in.get(), in.get()));

				VerificationMsg veriMsg = new VerificationMsg();
				veriMsg.setLocatorMsgHeader(locatorHdr);
				veriMsg.setCommonPayload(commonPayload);
				veriMsg.setVerPayload(veriPayload);
				output.write(veriMsg);
				break;
			case CommonConstants.LOC_MSG:
				GpsPayload gpsPayload = new GpsPayload();
				
				gpsPayload.setHasGpsFix(in.get() == 0x01? true: false);
				gpsPayload.setNoSatellite(in.get());
				gpsPayload.setAccuracy(in.getFloat());
				gpsPayload.setAltitude(in.getDouble());
				gpsPayload.setBearing(in.getFloat());
				gpsPayload.setmDistance(in.getFloat());
				gpsPayload.setmInitialBearing(in.getFloat());
				gpsPayload.setSpeed(in.getFloat());
				
				LocationMsg locMsg = new LocationMsg();
				
				locMsg.setGpsPayload(gpsPayload);
				locMsg.setLocatorMsgHeader(locatorHdr);
				locMsg.setCommonPayload(commonPayload);
				
				output.write(locMsg);

				break;
			case CommonConstants.PANIC_ALARM_MSG:
				break;
			default:
				output.write(locatorHdr);
				break;
			}
			
			System.out.println("\nafter decoding, size " + in.remaining()+ "\n");
			
			// append remaining bytes to next packet.
			if (in.remaining() > 0){
				return true; 
			}
			
			//in.skip(arg0)
			
			return false;

		} 
		else {
			return false;
		}
	}
}
