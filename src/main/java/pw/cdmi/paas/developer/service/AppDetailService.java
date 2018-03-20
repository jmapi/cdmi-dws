package pw.cdmi.paas.developer.service;

import java.util.List;

import pw.cdmi.paas.developer.rs.AppRequst;
import pw.cdmi.paas.developer.rs.ListAppResponce;

public interface AppDetailService {
	/**
	 * 添加一个接入app
	 * @param appRequst
	 */
	public void appObject(AppRequst appRequst,String userId);
	/**
	 * 删除一个接入app
	 */
	public void deleteObject(String id);
	/**
	 * 返回该开发者所有接入app
	 * @return
	 */
	public List<ListAppResponce> listObject(String developerId);
}
