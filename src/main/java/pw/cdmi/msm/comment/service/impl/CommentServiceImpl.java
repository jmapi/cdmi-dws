package pw.cdmi.msm.comment.service.impl;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pw.cdmi.msm.comment.model.entities.Comment;
import pw.cdmi.msm.comment.repositories.CommentRepsitory;
import pw.cdmi.msm.comment.rs.CommentRequest;
import pw.cdmi.msm.comment.service.CommentService;
@Component
@Transactional
public class CommentServiceImpl implements CommentService {
	@Autowired
	private CommentRepsitory commentRepsitory;

	@Override
	public void commentObject(CommentRequest comment) {
		// TODO Auto-generated method stub
		Comment entityComment = new Comment();
		entityComment.setTargetId(comment.getTarget().getId());
		entityComment.setTargetType(comment.getTarget().getType());
		entityComment.setContent(comment.getContent());
		
		entityComment.setCommentatorId(comment.getOwner().getId());
		entityComment.setComentatorName(comment.getOwner().getName());
		entityComment.setHeadImage(comment.getOwner().getHeadImage());
		entityComment.setCreateTime(new Date());
		commentRepsitory.save(entityComment);
	}

	@Override
	public List<Comment> commentList(String target_id,String target_type) {
		// TODO Auto-generated method stub
		return commentRepsitory.findByTargetIdAndTargetType(target_id,target_type);
	}

	@Override
	public void deleteComment(String id) {
		// TODO Auto-generated method stub
		commentRepsitory.deleteById(id);
		
	}

	@Override
	public long countComment(String target_id,String target_type) {
		// TODO Auto-generated method stub
		return commentRepsitory.countByTargetIdAndTargetType(target_id, target_type);
	}

	@Override
	public boolean inspectExist(String id, String type) {
		// TODO Auto-generated method stub
		if(commentRepsitory.countByTargetIdAndTargetType(id, type)>0)
			return true;
		return false;
	}

	@Override
	public Comment findComment(String Id) {
		// TODO Auto-generated method stub
		return commentRepsitory.findById(Id);
	}
	
}
