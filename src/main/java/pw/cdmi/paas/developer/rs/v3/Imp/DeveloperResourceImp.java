package pw.cdmi.paas.developer.rs.v3.Imp;


import javax.ws.rs.POST;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pw.cdmi.paas.developer.rs.BaseUserRequest;
import pw.cdmi.paas.developer.rs.v3.DeveloperResource;
import pw.cdmi.paas.developer.service.BaseUserDetailService;
@RestController
@RequestMapping("/test")
public class DeveloperResourceImp implements DeveloperResource{
	@Autowired
	private BaseUserDetailService baseUserDetailService;
	@POST
	@RequestMapping("/abc")
	public void save(@RequestBody BaseUserRequest baseUserRequest){
		System.out.println("我被运行了");
		baseUserDetailService.baseUserObject(baseUserRequest);
	}
	
	public DeveloperResourceImp() {
		// TODO Auto-generated constructor stub
	}

}
