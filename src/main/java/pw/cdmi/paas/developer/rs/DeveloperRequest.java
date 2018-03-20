package pw.cdmi.paas.developer.rs;

import lombok.Data;

@Data
public class DeveloperRequest {
	private String name; //开发者名称
	
	private String type; //开发者类型，是个人还是企业
	
	private Manager manager; //管理者
	
	private String createTime; //创建时间
	
	public DeveloperRequest() {
		// TODO Auto-generated constructor stub
	}

}
