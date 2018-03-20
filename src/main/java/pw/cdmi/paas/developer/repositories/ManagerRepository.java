package pw.cdmi.paas.developer.repositories;

import org.springframework.data.jpa.repository.JpaRepository;


import pw.cdmi.paas.developer.model.entities.DeveloperManager;

public interface ManagerRepository extends JpaRepository<DeveloperManager, String> {
	
	
			/**
			 * userId查找
			 * @param userId
			 * @return
			 */
	public DeveloperManager findByUserId(String userId);
	
}
