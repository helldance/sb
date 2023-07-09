/**
 * @author Yang Wei
 * @Date Jul 11, 2013
 */
package com.coordsafe.api.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coordsafe.api.dao.ApiRequestDAO;
import com.coordsafe.api.entity.ApiRequest;
import com.coordsafe.api.dao.ApiKeyDAO;
import com.coordsafe.api.entity.ApiKey;

/**
 * @author Yang Wei
 *
 */
@Service
public class ApiRequestServiceImpl implements ApiRequestService {
	
	@Autowired
	private ApiRequestDAO reqDao;
	
	@Autowired
	private ApiKeyDAO keyDao;

	@Override
	public void create(ApiRequest req) {
		// TODO Auto-generated method stub
		reqDao.create(req);
	}

	@Override
	public void update(ApiRequest req) {
		// TODO Auto-generated method stub
		reqDao.update(req);
	}

	@Override
	public void delete(ApiRequest req) {
		// TODO Auto-generated method stub
		reqDao.delete(req);
	}

	@Override
	public ApiRequest findById(long id) {
		// TODO Auto-generated method stub
		return reqDao.findById(id);
	}

	@Override
	public List<ApiRequest> findRequestByTime(long keyId, Date start, Date end) {
		// TODO Auto-generated method stub
		return reqDao.findRequestByTime(keyId, start, end);
	}

	@Override
	public List<Map<ApiKey, Integer>> findAllUsageByTime(Date _dtStart,
			Date _dtEnd) {
		// TODO Auto-generated method stub
		List<ApiKey> keys = keyDao.getAll();
		List<Map<ApiKey, Integer>> allCount = new ArrayList<Map<ApiKey, Integer>>();

		for (ApiKey key : keys){
			Map<ApiKey, Integer> kc = new HashMap<ApiKey, Integer>();
			kc.put(key, reqDao.findUseCountByTime(key.getId(), _dtStart, _dtEnd));
			
			allCount.add(kc);
		}
		
		return allCount;
	}

}
