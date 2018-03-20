package pw.cdmi.paas.developer.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import pw.cdmi.paas.developer.model.entities.App;
public interface AppRepository extends JpaRepository<App, String>{

	public void deleteById(String id);
	
	public List<App> findByUserId(String userId);
	

}
