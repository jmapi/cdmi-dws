package pw.cdmi.paas.developer.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pw.cdmi.paas.developer.model.entities.DeveloperManager;
import pw.cdmi.paas.developer.repositories.ManagerRepository;
import pw.cdmi.paas.developer.service.ManagerDetailService;
@Component
public class ManagerDetailServiceImp implements ManagerDetailService {
	@Autowired 
	private ManagerRepository managerRepository;
	
	public ManagerDetailServiceImp() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void managerObject(DeveloperManager developerManager) {
		// TODO Auto-generated method stub
		managerRepository.save(developerManager);
	}

	@Override
	public DeveloperManager findManger(String userId) {
		// TODO Auto-generated method stub
		return managerRepository.findByUserId(userId);
	}

}
