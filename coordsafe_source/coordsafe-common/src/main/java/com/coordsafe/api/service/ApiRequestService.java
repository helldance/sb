/**
 * @author Yang Wei
 * @Date Jul 11, 2013
 */
package com.coordsafe.api.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.coordsafe.api.entity.ApiRequest;
import com.coordsafe.api.entity.ApiKey;

public interface ApiRequestService {
	public void create (ApiRequest req);
	public void update (ApiRequest req);
	public void delete (ApiRequest req);
	public ApiRequest findById(long id);
	
	public List<ApiRequest> findRequestByTime(long keyId, Date start, Date end);
	public List<Map<ApiKey, Integer>> findAllUsageByTime(Date _dtStart, Date _dtEnd);
}
