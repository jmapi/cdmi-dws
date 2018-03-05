package pw.cdmi.msm.comment.rs;

import lombok.Data;

@Data
public class ListCommentResponse {
	private String id;
	private Owner owner;
	private String content;
	private String create_time;
}
