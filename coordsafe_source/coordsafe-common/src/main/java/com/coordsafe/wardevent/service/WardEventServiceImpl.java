package com.coordsafe.wardevent.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.coordsafe.wardevent.dao.WardEventDAO;
import com.coordsafe.wardevent.entity.WardEvent;

/**
 * @author Yang Wei
 * @Date Dec 13, 2013
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class WardEventServiceImpl implements WardEventService {
	private static final Log log = LogFactory.getLog(WardEventServiceImpl.class);
	
	private @Autowired WardEventDAO wardEventDao;

	/* (non-Javadoc)
	 * @see com.coordsafe.wardevent.service.WardEventService#create(com.coordsafe.wardevent.entity.WardEvent)
	 */
	@Override
	public void create(WardEvent event) {
		log.info("create wardevent");
		
		wardEventDao.create(event);
	}

	/* (non-Javadoc)
	 * @see com.coordsafe.wardevent.service.WardEventService#update(com.coordsafe.wardevent.entity.WardEvent)
	 */
	@Override
	public void update(WardEvent event) {
		log.info("update event");
		
		wardEventDao.update(event);
	}

	/* (non-Javadoc)
	 * @see com.coordsafe.wardevent.service.WardEventService#findById(long)
	 */
	@Override
	public WardEvent findById(long wardEventId) {
		log.info("find wardevent by Id " + wardEventId);
		
		return wardEventDao.findByID(wardEventId);
	}

	/* (non-Javadoc)
	 * @see com.coordsafe.wardevent.service.WardEventService#findByWardId(long)
	 */
	@Override
	public List<WardEvent> findByWardId(long wardId) {
		log.info("find wardevent by wardId " + wardId);
		
		return wardEventDao.findByWardId(wardId);
	}

	/* (non-Javadoc)
	 * @see com.coordsafe.wardevent.service.WardEventService#findByTime(long, java.util.Date, java.util.Date)
	 */
	@Override
	public List<WardEvent> findByTime(long wardId, String startTime, String endTime) {
		log.info("find wardevent by time " + wardId + " " + startTime + " " + endTime);
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
		
		Date _start = null, _end = null;
		
		try {
			_start = sdf.parse(startTime);
			_end = sdf.parse(endTime);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (_start != null && _end != null)			
			return wardEventDao.findByTime(wardId, _start, _end);
		
		return new ArrayList<WardEvent>();
	}

}
