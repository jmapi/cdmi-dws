package pw.cdmi.paas.developer.service;

import pw.cdmi.paas.developer.model.entities.DeveloperManager;

public interface ManagerDetailService {
	/**
	 * 保存
	 */
	public void managerObject(DeveloperManager developerManager);
	
	/**
	 * 查询
	 * @param userId
	 * @return
	 */
	public DeveloperManager findManger(String userId);
}
