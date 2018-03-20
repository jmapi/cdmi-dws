package pw.cdmi.paas.developer.rs;

import org.apache.catalina.Manager;

import lombok.Data;

@Data
public class DeveloperResponse {

	private String id;  	//id
	private String name; 	//开发者名称
							
	private String type;	//开发者类型
	
	private Manager manager;//管理员
	
	private String creatTime;//注册时间
	public DeveloperResponse() {
		
		
		// TODO Auto-generated constructor stub
	}

}
