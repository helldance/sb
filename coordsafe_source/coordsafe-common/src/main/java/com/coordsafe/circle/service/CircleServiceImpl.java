package com.coordsafe.circle.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.coordsafe.circle.dao.CircleDAO;
import com.coordsafe.circle.entity.Circle;
import com.coordsafe.guardian.dao.GuardianUserDAO;
import com.coordsafe.guardian.service.GuardianService;

@Service("circleService")
@Transactional
public class CircleServiceImpl implements CircleService {
    protected static Logger log = LoggerFactory.getLogger(CircleServiceImpl.class);

    @Autowired
    CircleDAO circleDAO;
    
	@Override
	public void add(Circle circle) {
		log.info("In the add circle service...");
		circleDAO.add(circle);
	}

	@Override
	public void delete(Circle circle) {
		log.info("In the delete circle service...");
		circleDAO.delete(circle);

	}

	@Override
	public void update(Circle circle) {
		log.info("In the update circle service...");
		circleDAO.update(circle);

	}

	@Override
	public Circle find(Long circleID) {
		log.info("In the find circle service...");
		return circleDAO.find(circleID);
	}

	@Override
	public List<Circle> findAllCircle() {
		log.info("In the findAllCircle circle service...");
		return circleDAO.findAllCircle();
	}

	@Override
	public List<Circle> findCircleByGuardian(Long guardianID) {
		log.info("In the findCircleByGuardian circle service...");
		return circleDAO.findCircleByGuardian(guardianID);
	}

}
