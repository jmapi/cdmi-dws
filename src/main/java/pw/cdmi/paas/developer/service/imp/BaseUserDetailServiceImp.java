package pw.cdmi.paas.developer.service.imp;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pw.cdmi.paas.developer.model.entities.BaseUser;
import pw.cdmi.paas.developer.model.entities.Developer;
import pw.cdmi.paas.developer.model.entities.DeveloperManager;
import pw.cdmi.paas.developer.repositories.BaseUserRepository;
import pw.cdmi.paas.developer.rs.BaseUserRequest;
import pw.cdmi.paas.developer.rs.DeveloperRequest;
import pw.cdmi.paas.developer.service.BaseUserDetailService;
import pw.cdmi.paas.developer.service.DeveloperDetailService;
import pw.cdmi.paas.developer.service.ManagerDetailService;
@Component
public class BaseUserDetailServiceImp implements BaseUserDetailService{
	@Autowired
	private BaseUserRepository baseUserRepository;
	
	@Autowired 
	private DeveloperDetailService developerDetailService;
	
	@Autowired
	private ManagerDetailService managerDetailService;
	
	public BaseUserDetailServiceImp() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void baseUserObject(BaseUserRequest baseUserRequest) {
		// TODO Auto-generated method stub
		BaseUser baseUser = new BaseUser();
		baseUser.setAccountId(baseUserRequest.getAccountId());
		baseUser.setPassWord(baseUserRequest.getPassword());
		baseUser.setCreateTime(new Date());
		baseUserRepository.save(baseUser);
	}

	@Override
	public BaseUser findBaseUser(String accountId) {
		// TODO Auto-generated method stub
		return baseUserRepository.findByAccountId(accountId);
	}

	@Override
	public void DeveloperRequestObject(DeveloperRequest developerRequest,String accountId) {
		// TODO Auto-generated method stub
		BaseUser baseUser=baseUserRepository.findByAccountId(accountId);
		
		Developer developer = new Developer();
		DeveloperManager manager = new DeveloperManager();
		
		developer.setDeveloperName(developerRequest.getName());
		developer.setDeveloperType(developerRequest.getType());
		developer.setUserId(baseUser.getId());
		
		manager.setManagerName(developerRequest.getManager().getName());
		manager.setManagerEmail(developerRequest.getManager().getEmail());
		manager.setManagerPhone(developerRequest.getManager().getMoblie());
		manager.setUserId(baseUser.getId());
		
		
		developerDetailService.developerObject(developer);
		managerDetailService.managerObject(manager);
		
		
	}

}
