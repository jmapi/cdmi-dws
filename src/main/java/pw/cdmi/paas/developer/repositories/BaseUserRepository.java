package pw.cdmi.paas.developer.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import pw.cdmi.paas.developer.model.entities.BaseUser;



public interface BaseUserRepository extends JpaRepository<BaseUser, String>{
	
	/**
	 * account查询
	 * @param accountId
	 * @return
	 */
	public BaseUser findByAccountId(String accountId);
}
