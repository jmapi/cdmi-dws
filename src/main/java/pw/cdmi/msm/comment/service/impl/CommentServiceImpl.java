package pw.cdmi.msm.comment.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import pw.cdmi.msm.comment.model.entities.Comment;
import pw.cdmi.msm.comment.repositories.CommentRepsitory;
import pw.cdmi.msm.comment.rs.CommentRequest;
import pw.cdmi.msm.comment.service.CommentService;

public class CommentServiceImpl implements CommentService {
	@Autowired
	private CommentRepsitory commentRepsitory;

	@Override
	public void commentObject(CommentRequest comment) {
		// TODO Auto-generated method stub
		Comment entityComment = new Comment();
		entityComment.setTargetId(comment.getTarget().getId());
		entityComment.setTargetType(comment.getTarget().getType());
		entityComment.setCommentatorId(comment.getOwner_id());
		entityComment.setContent(comment.getContent());
		commentRepsitory.save(entityComment);
	}

	@Override
	public List<Comment> commentList(String target_id) {
		// TODO Auto-generated method stub
		return commentRepsitory.findByTargetId(target_id);
	}

	@Override
	public boolean deleteComment(String user_id, String Coment_id) {
		// TODO Auto-generated method stub
		Comment comment = commentRepsitory.findById(Coment_id);
		
		if(comment.getCommentatorId().equals(user_id)){
			commentRepsitory.deleteById(Coment_id);
			return true;
		}	
		return false;
	}

	@Override
	public long countComment(String target_id) {
		// TODO Auto-generated method stub
		return commentRepsitory.countByTargetId(target_id);
	}

	@Override
	public boolean inspectExist(String id, String type) {
		// TODO Auto-generated method stub
		if(commentRepsitory.countByTargetIdAndTargetType(id, type)>0)
			return true;
		return false;
	}
	
}
