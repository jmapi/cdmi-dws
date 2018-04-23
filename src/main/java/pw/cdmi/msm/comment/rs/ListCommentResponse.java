package pw.cdmi.msm.comment.rs;

import lombok.Data;

import java.util.List;

@Data
public class ListCommentResponse {
    private String id;
    private Owner owner;
    private int  praiseNumber;
    private String content;
    private String create_time;
    private List<ListCommentResponse> nexts;
}

