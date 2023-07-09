package com.coordsafe.wardevent.service;

import java.util.Date;
import java.util.List;

import com.coordsafe.wardevent.entity.WardEvent;

/**
 * @author Yang Wei
 * @Date Dec 13, 2013
 */
public interface WardEventService {
	public void create (WardEvent event);
	public void update (WardEvent event);
	public WardEvent findById (long wardEventId);
	public List<WardEvent> findByWardId (long wardId);
	public List<WardEvent> findByTime (long wardId, String startTime, String endTime);
}
