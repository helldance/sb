/**
 * 
 */
package com.coordsafe.api.dao;

import java.util.Date;
import java.util.List;

import com.coordsafe.api.entity.ApiRequest;

/**
 * @author Yang Wei
 *
 */
public interface ApiRequestDAO {
	public void create (ApiRequest req);
	public void update (ApiRequest req);
	public void delete (ApiRequest req);
	public ApiRequest findById(long id);
	
	public List<ApiRequest> findRequestByTime(long keyId, Date start, Date end);
	public int findUseCountByTime(long keyId, Date _dtStart, Date _dtEnd);
}
