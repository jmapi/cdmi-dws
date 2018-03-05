package pw.cdmi.msm.praise.model;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import lombok.Data;
@Component
@Data
public class PraiseTarget {
	private String id;
	private String type;
}
