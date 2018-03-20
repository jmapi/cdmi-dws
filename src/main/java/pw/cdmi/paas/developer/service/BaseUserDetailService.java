package pw.cdmi.paas.developer.service;

import pw.cdmi.paas.developer.model.entities.BaseUser;
import pw.cdmi.paas.developer.rs.BaseUserRequest;
import pw.cdmi.paas.developer.rs.DeveloperRequest;

public interface BaseUserDetailService {

	/**
	 * 保存baseUser
	 * @param developerRequest
	 */
	public void  baseUserObject(BaseUserRequest baseUserRequest);
	/**
	 * 查找
	 * @param userName
	 * @return
	 */
	public BaseUser findBaseUser(String accountId);
	/**
	 * 补充信息
	 * @param developerRequest
	 */
	public void DeveloperRequestObject(DeveloperRequest developerRequest,String accountId);
}
