package pw.cdmi.msm.comment.service.impl;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import pw.cdmi.msm.comment.model.entities.Comment;
import pw.cdmi.msm.comment.repositories.CommentRepsitory;
import pw.cdmi.msm.comment.service.CommentService;

@Component
@Transactional
public class CommentServiceImpl implements CommentService {
	@Autowired
	private CommentRepsitory commentRepsitory;

	@Override
	public void commentObject(Comment comment) {
		// TODO Auto-generated method stub
		
		comment.setPraiseNumber(0);
		comment.setCreateTime(new Date());
		commentRepsitory.save(comment);
	}

	@Override
	public Iterator<Comment> commentList(Comment comment,int cursor,int maxcount) {
		// TODO Auto-generated method stub
		Sort.Order order =  new Sort.Order(Sort.Direction.DESC,"createTime");
        Sort sort = new Sort(order);	
        Pageable pageRequest = new PageRequest(cursor,maxcount,sort);
        ExampleMatcher matching = ExampleMatcher.matching().withIgnorePaths("praiseNumber");
  
		Page<Comment> findAll = commentRepsitory.findAll(Example.of(comment,matching), pageRequest);
		
		return findAll.getContent().iterator();
		
	}

	@Override
	public void deleteComment(Comment comment) {
		ExampleMatcher matching = ExampleMatcher.matching().withIgnorePaths("praiseNumber");
		Comment findOne = commentRepsitory.findOne(Example.of(comment,matching));
		if(findOne==null){
			throw new SecurityException("你没有权限");
		}
		commentRepsitory.delete(comment.getId());
		
	}

	@Override
	public long countComment(Comment comment) {
		
		
//		long countByTargetIdAndTargetType = commentRepsitory.countByTargetIdAndTargetType(comment.getTargetId(),comment.getTargetType());
//		return countByTargetIdAndTargetType;
		ExampleMatcher matching = ExampleMatcher.matching().withIgnorePaths("praiseNumber");
		return commentRepsitory.count(Example.of(comment,matching));
	}

	@Override
	public boolean inspectExist(Comment comment) {
		ExampleMatcher matching = ExampleMatcher.matching().withIgnorePaths("praiseNumber");
		if(commentRepsitory.findOne(Example.of(comment,matching))==null)
			return true;
		return false;
	}

	@Override
	public Comment findComment(Comment comment) {
		ExampleMatcher matching = ExampleMatcher.matching().withIgnorePaths("praiseNumber");
		return commentRepsitory.findOne(Example.of(comment,matching));
	}
	
}
