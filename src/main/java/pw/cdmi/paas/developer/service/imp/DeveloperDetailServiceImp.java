package pw.cdmi.paas.developer.service.imp;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pw.cdmi.paas.developer.model.entities.Developer;
import pw.cdmi.paas.developer.repositories.DeveloperRepository;
import pw.cdmi.paas.developer.service.DeveloperDetailService;


@Component
public class DeveloperDetailServiceImp implements DeveloperDetailService{
	@Autowired
	private DeveloperRepository developerRepository;
	@Override
	public void developerObject(Developer developer){
		developerRepository.save(developer);
	}
	@Override
	public Developer findDeveloper(String userId){	
			return developerRepository.findByUserId(userId);
		
	}

	public DeveloperDetailServiceImp() {
		// TODO Auto-generated constructor stub
	}


}
