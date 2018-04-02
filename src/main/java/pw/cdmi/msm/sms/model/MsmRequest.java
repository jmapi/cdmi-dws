package pw.cdmi.msm.sms.model;

import lombok.Data;

@Data
public class MsmRequest {
	private String mobile;
	private String templatesId;
}
