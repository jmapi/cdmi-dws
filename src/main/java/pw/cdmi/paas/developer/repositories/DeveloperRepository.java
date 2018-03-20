package pw.cdmi.paas.developer.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import pw.cdmi.paas.developer.model.entities.Developer;

public interface DeveloperRepository extends JpaRepository<Developer, Long> {
	
	/**
	 * 以id查询
	 * @param id
	 * @return
	 */
	public Developer findById(String id);
	/**
	 * 查询该平台账号用户所有的开发者
	 * @param userUid
	 * @return
	 */
	public Developer findByUserId(String userId);
	

}
