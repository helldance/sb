package com.coordsafe.circle.dao;

import java.util.List;

import com.coordsafe.circle.entity.Circle;


public interface CircleDAO {
	
	public void add(Circle circle);
	public void delete(Circle circle);
	public void update(Circle circle);
	public Circle find(Long circleID);
	public List<Circle> findAllCircle();
	public List<Circle> findCircleByGuardian(Long guardianID);

	

}
