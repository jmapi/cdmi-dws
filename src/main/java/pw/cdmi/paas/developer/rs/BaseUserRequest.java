package pw.cdmi.paas.developer.rs;

import lombok.Data;

@Data
public class BaseUserRequest {

	
	private String accountId;
	private String password;
	public BaseUserRequest() {
		// TODO Auto-generated constructor stub
	}

}
