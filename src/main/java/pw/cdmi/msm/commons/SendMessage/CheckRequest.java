package pw.cdmi.msm.commons.SendMessage;

import lombok.Data;

@Data
public class CheckRequest {
	private String mobile;
	private String code;
}
