package com.coordsafe.gateway.dao;

import java.util.ArrayList;

import com.coordsafe.gateway.entity.GpsRawData;

public interface GpsRawDataDAO {
	public void insert(GpsRawData customer);
	public ArrayList<GpsRawData> findByGpsRawDataId(int id);
}
