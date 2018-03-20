package pw.cdmi.paas.developer.rs;

import lombok.Data;

@Data
public class VoucherRequest {
	private String id;
	
	private String appKey;
	
	private String secretkey;
	
	public VoucherRequest() {
		// TODO Auto-generated constructor stub
	}

}
