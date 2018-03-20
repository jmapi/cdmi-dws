package pw.cdmi.msm.comment.rs;

import java.util.Date;

import lombok.Data;

@Data
public class CommentRequest {
	private CommentTarget target;
	private String content;
	private Owner owner;
	
}
