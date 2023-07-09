package com.coordsafe.wardevent.dao;

import java.util.Date;
import java.util.List;

import com.coordsafe.wardevent.entity.WardEvent;


public interface WardEventDAO {
	
	public void create(WardEvent wardEvent);
	public void update(WardEvent wardEvent);
	public void delete(WardEvent wardEvent);
	public List<WardEvent> findAll();
	public List<WardEvent> findByWardId(Long wardId);
	public WardEvent findByID(Long wardEventID);
	public List<WardEvent> findByTime(long wardId, Date _start, Date _end);
}
