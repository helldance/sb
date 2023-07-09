package com.coordsafe.gateway.dao;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.coordsafe.gateway.entity.GpsDevice;
import com.coordsafe.gateway.entity.GpsRawData;

public class GpsRawDataDAOImpl implements GpsRawDataDAO
{
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	private static final String SQL_ADD_DEVICE =  "INSERT INTO GPSRAWDATADEVICE " +
			"(DEVICEID,IPORT,CHANNELID) VALUES (:deviceid,:iport,:channelid)";
	
	private static final String SQL_REMOVE_DEVICE = "DELETE FROM GPSRAWDATADEVICE " +
			"WHERE DEVICEID = :deviceid";
	
	private static final String SQL_REMOVE_DEVICES = "DELETE FROM GPSRAWDATADEVICE ";
	
	private static final String SQL_SELECT_DEVICE = "SELECT COUNT(*) FROM GPSRAWDATADEVICE " +
			"WHERE DEVICEID = :deviceid";
	
	private static final String SQL_ADD_RAWDATA =  "INSERT INTO GPSRAWDATA " +
			"(DEVICEID,RAWDATA,GENERATETIME,GPSDATE) VALUES (:deviceid, :rawdata, :generatetime,:gpsdate)";
	
	private static final String SQL_SELECT_CHANNEL = "SELECT DEVICEID,IPORT, CHANNELID FROM GPSRAWDATADEVICE " +
			"WHERE DEVICEID = :deviceid";
	
	// Add device into db
	public void addDevice(GpsDevice gpsDevice){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("deviceid", gpsDevice.getDeviceid());
		params.put("iport", gpsDevice.getIport());
		params.put("channelid", gpsDevice.getChannelid());
		
		namedParameterJdbcTemplate.update(SQL_ADD_DEVICE, params);
		//gpsDevice.setId(queryForIdentity());
	}
	
	//Remove device from db
	public void removeDevice(String deviceID){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("deviceid", deviceID);
		
		namedParameterJdbcTemplate.update(SQL_REMOVE_DEVICE, params);
	}
	
	//Find device from DB
	public int findDeviceNum(String deviceID){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("deviceid", deviceID);
		
		return namedParameterJdbcTemplate.queryForInt(SQL_SELECT_DEVICE, params);
		
	}
	
	//Insert the GPS raw data into db
	public void insert(GpsRawData rawData) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("deviceid", rawData.getDeviceId());
		params.put("rawdata", rawData.getRawData());
		params.put("generatetime", rawData.getGeneratetime());
		params.put("gpsdate", rawData.getGpsDate());
		
		namedParameterJdbcTemplate.update(SQL_ADD_RAWDATA, params);
		
	}



	//Find channel ID by Device
	public int findChannelID(String deviceID) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("deviceid", deviceID);

	
		GpsDevice gpsDevice = (GpsDevice) namedParameterJdbcTemplate
				.queryForObject(SQL_SELECT_CHANNEL, params, new RowMapper<Object>() {

					public Object mapRow(ResultSet resultSet, int rowNum) throws SQLException {

						return new GpsDevice(resultSet.getString("DEVICEID"),
								resultSet.getString("IPORT"), resultSet.getInt("CHANNELID"));

					}

				});

		return gpsDevice.getChannelid();

	}
	
	public ArrayList<GpsRawData> findByGpsRawDataId(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	public void removeDevices() {
		Map<String,Object> params = new HashMap<String,Object>();
		namedParameterJdbcTemplate.update(SQL_REMOVE_DEVICES, params);
	}
	

}