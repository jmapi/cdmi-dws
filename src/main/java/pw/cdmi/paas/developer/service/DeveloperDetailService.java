package pw.cdmi.paas.developer.service;

import pw.cdmi.paas.developer.model.entities.Developer;

public interface DeveloperDetailService {

	/**
	 * 添加一个开发者
	 * @param develogerRequest
	 * @param userName
	 */
	public void developerObject(Developer developer);
	/**
	 * 根据id、userUid查询一个开发者
	 * @param id
	 * @param username
	 * @return
	 */
	public Developer findDeveloper(String userId);
	
	
	
}
