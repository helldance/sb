package com.coordsafe.tcpgateway.codec;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

import com.coordsafe.tcpgateway.locator.AuthenticationMsg;
import com.coordsafe.tcpgateway.locator.LocationMsg;
import com.coordsafe.tcpgateway.locator.LocatorMessage;
import com.coordsafe.tcpgateway.locator.VerificationMsg;

public class LocatorMsgEncoder implements ProtocolEncoder {

	@Override
	public void dispose(IoSession arg0) throws Exception {

	}

	@Override
	public void encode(IoSession session, Object message, ProtocolEncoderOutput output)
			throws Exception {
		IoBuffer buf = IoBuffer.allocate(64).setAutoExpand(true);
		
		LocatorMessage locatorMsg = (LocatorMessage)message;
		//putHeaderCommonPayload(buf,locatorMsg);

		if (message instanceof AuthenticationMsg) {
			AuthenticationMsg authMsg = (AuthenticationMsg)message;
			buf.putInt(47);
			putHeaderCommonPayload(buf,locatorMsg);
		}
		
		if (message instanceof VerificationMsg) {
			VerificationMsg verificationMsg = (VerificationMsg)message;	
			//buf.putInt(12);
			putHeaderCommonPayload(buf,locatorMsg);
			buf.put(verificationMsg.getVerPayload().getFirmwareVersion().getMajor());
			buf.put(verificationMsg.getVerPayload().getFirmwareVersion().getMinor());
			buf.put(verificationMsg.getVerPayload().getAppsVersion().getMajor());
			buf.put(verificationMsg.getVerPayload().getAppsVersion().getMinor());
			buf.put(verificationMsg.getVerPayload().getConfigVersion().getMajor());
			buf.put(verificationMsg.getVerPayload().getConfigVersion().getMinor());
		}
		
		if (message instanceof LocationMsg){
			LocationMsg locMsg = (LocationMsg)message;
			buf.putInt(77);
			putHeaderCommonPayload(buf,locatorMsg);
			buf.put((byte) (locMsg.getGpsPayload().isHasGpsFix()? 0x01 : 0x00));
			buf.put(locMsg.getGpsPayload().getNoSatellite());
			buf.putFloat(locMsg.getGpsPayload().getAccuracy());
			buf.putDouble(locMsg.getGpsPayload().getAltitude());
			buf.putFloat(locMsg.getGpsPayload().getBearing());
			//buf.putDouble(locMsg.getGpsPayload().getLatitude());
			//buf.putDouble(locMsg.getGpsPayload().getLongitude());
			buf.putFloat(locMsg.getGpsPayload().getmDistance());
			buf.putFloat(locMsg.getGpsPayload().getmInitialBearing());
			/*for (float f : locMsg.getGpsPayload().getmResults()){
				buf.putFloat(f);
			}*/
			//buf.put(locMsg.getGpsPayload().getmResults());
			buf.putFloat(locMsg.getGpsPayload().getSpeed());
		}
		
		buf.flip();

		output.write(buf);
		
		System.out.println("After encode, bytes: ");
		
		for (byte b : buf.array()){
			System.out.print(b + " ");
		}
	}
	
	private void putHeaderCommonPayload(IoBuffer buf, LocatorMessage msg) {
		buf.put(msg.getLocatorMsgHeader().getServiceId());
		buf.put(msg.getLocatorMsgHeader().getDirIndicator());
		buf.put(msg.getLocatorMsgHeader().getMsgTypeId());
		buf.put(msg.getLocatorMsgHeader().getImeiCode().getBytes());
		buf.put(msg.getLocatorMsgHeader().getSerialNo());
		buf.putLong(msg.getLocatorMsgHeader().getServerTime() / 1000);
		//buf.putShort(msg.getLocatorMsgHeader().getPayloadLength());
		buf.putDouble(msg.getCommonPayload().getGpsLocation().getLatitude());
		buf.putDouble(msg.getCommonPayload().getGpsLocation().getLongitude());
		//buf.putShort(msg.getCommonPayload().getStatus());
		buf.put(msg.getCommonPayload().getGprs());
		buf.put(msg.getCommonPayload().getGps());
		buf.put(msg.getCommonPayload().getPower());
		buf.put(msg.getCommonPayload().getOther());
	}

}
