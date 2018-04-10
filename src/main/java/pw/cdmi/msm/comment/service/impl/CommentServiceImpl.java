package pw.cdmi.msm.comment.service.impl;

import java.util.Date;
import java.util.Iterator;

import javax.transaction.Transactional;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import pw.cdmi.msm.comment.model.SupportTargetType;
import pw.cdmi.msm.comment.model.entities.Comment;
import pw.cdmi.msm.comment.repositories.CommentRepsitory;
import pw.cdmi.msm.comment.service.CommentService;
import pw.cdmi.msm.message.model.EventContent;
import pw.cdmi.msm.message.model.EventObject;
import pw.cdmi.msm.message.model.EventUser;
import pw.cdmi.msm.message.model.MessageContent;
import pw.cdmi.msm.message.model.ReferEvent;
import pw.cdmi.msm.message.model.entity.NotifyUserMessage;
import pw.cdmi.msm.message.service.MessageService;

@Component
@Transactional
public class CommentServiceImpl implements CommentService {
	@Autowired
	private CommentRepsitory commentRepsitory;
	@Autowired
	private MessageService messageService;
	
	@Override
	public void commentObject(Comment comment) {	
		comment.setPraiseNumber(0);
		comment.setCreateTime(new Date());
		Comment save = commentRepsitory.save(comment);

		send(save);
		
	}
	
	private NotifyUserMessage toNotifyUserMessage(Comment save){
		MessageContent messageContent = new MessageContent();
		
		messageContent.setTitle("评论");
		
		messageContent.setContent(save.getUserName()+"回复了你");
		
		ReferEvent referEvent = new ReferEvent();
		
		EventContent eventContent = new EventContent();
		EventUser eventUser = new EventUser();
		EventObject eventObject = new EventObject();
		
		eventContent.setId(save.getId());
		eventContent.setText(save.getContent());
		eventContent.setType("Tenancy_Comment");
		
		eventObject.setId(save.getTargetId());
		eventObject.setType(save.getTargetType());
		
		eventUser.setId(save.getUserAid());
		eventUser.setName(save.getUserName());
		eventUser.setType(save.getUserType());
		eventUser.setHeadImageUrl(save.getHeadImage());
		
		referEvent.setUser(eventUser);
		referEvent.setContent(eventContent);
		referEvent.setTarget(eventObject);
		
		messageContent.setEvent(referEvent);
		
		NotifyUserMessage notifyUserMessage = NotifyUserMessage.messageContent(JSONObject.fromObject(messageContent).toString());
		
		notifyUserMessage.setAppId(save.getAppId());
		notifyUserMessage.setTenantId(save.getTenantId());
		notifyUserMessage.setSiteId(save.getSiteId());
		
		notifyUserMessage.setIsBroadcast(true);
		notifyUserMessage.setNotifyAid(save.getOwnerId());
		return notifyUserMessage;
		
		
		
	}

	private void send(Comment comment) {
		SupportTargetType fromName = SupportTargetType.fromName(comment
				.getTargetType());
		Comment comment2 = null;
		if (fromName == null) {
			throw new SecurityException("SupportTargetType is null");
		}
		if(!StringUtils.isBlank(comment.getOwnerId())){
			
			messageService.createNotifyUserMessage(toNotifyUserMessage(comment));
		}

		comment2 = commentRepsitory.findOne(comment.getTargetId());
		Comment comment3 = new Comment();
		if(comment2 != null){
			comment3.setOwnerId(comment2.getOwnerId());
			
			//object
			comment3.setTargetId(comment2.getTargetId());
			comment3.setTargetType(comment2.getTargetType());
			
			//content
			comment3.setId(comment.getId());
			comment3.setContent(comment.getContent());

			//user
			comment3.setUserAid(comment.getUserAid());
			comment3.setUserName(comment.getUserName());
			comment3.setUserType(comment.getUserType());
			comment3.setHeadImage(comment.getHeadImage());
			//
			comment3.setAppId(comment2.getAppId());
			comment3.setTenantId(comment2.getTenantId());
			comment3.setSiteId(comment2.getSiteId());
		
		switch (fromName) {
			case Tenancy_Comment:

				send(comment3);
				break;
			default:
				break;
		}
		}
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
		messageService.deleteByTargetId(comment.getId());
		
		Comment commentNext = new Comment();
		commentNext.setTargetId(comment.getId());
		
		Iterable<Comment> findAll = commentRepsitory.findAll(Example.of(commentNext,matching));
		Iterator<Comment> it = findAll.iterator();
		while(it.hasNext()){
			Comment next = it.next();
			deleteComment(next);
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
