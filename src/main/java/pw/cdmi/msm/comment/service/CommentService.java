package pw.cdmi.msm.comment.service;

import java.util.List;


import pw.cdmi.msm.comment.model.entities.Comment;
import pw.cdmi.msm.comment.rs.CommentRequest;


public interface CommentService {
	
	/**
	 * 查询对应id的comment
	 * @param CommentId
	 * @return
	 */
	public Comment findComment(String commentId);
	/**
	 * 保存评论
	 * @param target
	 */
	public void commentObject(CommentRequest comment);
	/**
	 * 获取对应目标的all评论
	 * @param target_id
	 * @return
	 */
	public List<Comment> commentList(String target_id,String target_type);
	/**
	 * 删除对应评论
	 * @param Coment_id
	 */
	public void deleteComment(String Coment_id);
	/**
	 * 目标所有的评论
	 * @param target_id
	 * @return
	 */
	public long countComment(String target_id,String target_type);
	/**
	 * 判断评论是否存在
	 * @param id
	 * @param type
	 * @return
	 */
	public boolean inspectExist(String id, String type);
	
}
