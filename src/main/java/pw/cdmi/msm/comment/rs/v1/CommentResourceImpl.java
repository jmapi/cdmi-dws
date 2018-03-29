package pw.cdmi.msm.comment.rs.v1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import pw.cdmi.core.exception.InvalidParameterException;
import pw.cdmi.msm.comment.model.entities.Comment;
import pw.cdmi.msm.comment.rs.CommentRequest;
import pw.cdmi.msm.comment.rs.ListCommentResponse;
import pw.cdmi.msm.comment.rs.Owner;
import pw.cdmi.msm.comment.rs.TestCommentRequest;
import pw.cdmi.msm.comment.service.CommentService;
import pw.cdmi.msm.comment.model.SupportTargetType;

@RestController
@RequestMapping("comments/v1")
public class CommentResourceImpl {

	@Autowired
	private CommentService commentService;
	
	
	@PostMapping
	public void comment(@RequestBody CommentRequest comment) {
		// TODO Auto-generated method stub
		// 参数合法性检查
				
				if (comment == null ||  StringUtils.isBlank(comment.getOwnerId())|| comment.getTarget() == null
						|| StringUtils.isBlank(comment.getTarget().getId())
						|| StringUtils.isBlank(comment.getTarget().getType())) {
					// FIXME 修改为客户端必要参数缺失，请检查
					throw new InvalidParameterException("参数错误");
				}
				
				// 检查type是否在支持列表中
				
				SupportTargetType support_target_type = SupportTargetType.fromName(comment.getTarget().getType());
				if (support_target_type == null) {
					// FIXME 修改为不支持的点赞目标类型
					throw  new NullPointerException("SupportTargetType is null");
				
				}
				Comment comment2 = new Comment();
				comment2.setUserAid(comment.getOwnerId());
				comment2.setContent(comment.getContent());
				comment2.setTargetId(comment.getTarget().getId());
				comment2.setTargetType(comment.getTarget().getType());
				
				comment2.setAppId("test");
				//包装点赞对象
					switch (support_target_type) {
					case Tenancy_File:
						
						//TODO 补充				
						
						
						break;
					case Tenancy_Comment:
						
						//TODO 补充
				//		commentService.commentObject(comment2);
		
						break;
					case Tenancy_User:
						
						break;
					default:
						throw new SecurityException();
					}
					commentService.commentObject(comment2);
	}
	@GetMapping("/{target_id}/amount")
	public @ResponseBody Map<String, Object> getCommentAmount(@PathVariable("target_id")String id,@RequestParam("type") String type) {
		// TODO 参数合法性检查
		if (StringUtils.isBlank(id) || StringUtils.isBlank(type)) {
			// FIXME 修改为客户端必要参数缺失，请检查
			throw new InvalidParameterException("参数错误");
		}
		Comment comment = new Comment();
		comment.setTargetId(id);
		comment.setTargetType(type);
		comment.setAppId("test");
		
		Map<String, Object> hashMap = new HashMap<String,Object>();
		hashMap.put("amount",commentService.countComment(comment));
		return hashMap;
	}
	@GetMapping("{target_id}/comment")
	public List<ListCommentResponse> listComment(@PathVariable("target_id") String id, @RequestParam("type") String type,
			@RequestParam("cursor")int cursor,@RequestParam("maxcount")int maxcount) {
		// TODO 参数合法性检查
		if (StringUtils.isBlank(id) || StringUtils.isBlank(type)) {
			// FIXME 修改为客户端必要参数缺失，请检查
			throw new InvalidParameterException("参数错误");
		}
		Comment comment = new Comment();
		comment.setTargetId(id);
		comment.setTargetType(type);
		comment.setAppId("test");
		return toListCommentResponse(commentService.commentList(comment,cursor,maxcount));
	}
	
	@DeleteMapping("/{id}")
	public void delComment(@RequestParam(value="user_id")String userId,@PathVariable("id")String commentId){
		//TODO 参数合法性检查
		if (StringUtils.isBlank(userId) || StringUtils.isBlank(commentId)) {
			// FIXME 修改为客户端必要参数缺失，请检查
			throw new InvalidParameterException("参数错误");
		}
		
		//TODO  从Auth信息中获得请求用户的信息
		//判断该用户是否具有管理权限，可以删除他人发布的评论信息
		/*
		if(userId != null){
			//管理员删除指定用户发布的评论信息
			Comment findComment = commentService.findComment(commentId);
			if(findComment==null){
				throw new NullPointerException("该评论不存在");
			}
			if(findComment.getCommentatorId().equals(userId))
				commentService.deleteComment(commentId);
			else
				System.out.println("失败");
			//	throw new AWSClientException(ClientError.InvalidParameter, ClientReason.InvalidParameter);
			
		}else{
			//TODO  从Auth信息中获得请求用户的信息
			//判断该用户是否具有管理权限，可以删除他人发布的评论信息
			
			
			commentService.deleteComment(commentId);
		}
		*/
		Comment comment = new Comment();
		comment.setAppId("test");
		comment.setId(commentId);
		comment.setUserAid(userId); 
		
		commentService.deleteComment(comment);
		
		
	}

	/*
	//@DeleteMapping("/{id}")
	public void delComment(@PathVariable("id")String commentId){
		//TODO 参数合法性检查
		if ( StringUtils.isBlank(commentId)) {
			// FIXME 修改为客户端必要参数缺失，请检查
			throw new InvalidParameterException("参数错误");
		}
		//TODO 从Auth信息中获取当前操作用户
		//   检查要删除的评论id是否为当前操作用户所拥有
		//	Comment comment = commentService.get(commentId);
		
		commentService.deleteComment(commentId);
	}
	
	*/
	private List<ListCommentResponse> toListCommentResponse(Iterator<Comment> it) {
	
		List<ListCommentResponse> listResponse = new ArrayList<ListCommentResponse>();
		while(it.hasNext()){
			ListCommentResponse response = new ListCommentResponse();
			Comment comment = (Comment)it.next();
			
			response.setPraiseNumber(comment.getPraiseNumber());
			response.setContent(comment.getContent());
			 java.text.SimpleDateFormat simpleDateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss a");
			
			response.setCreate_time(simpleDateFormat.format(comment.getCreateTime()));
			response.setId(comment.getId());
			Owner owner = new Owner();
			owner.setId(comment.getUserAid());
			
			//TODO 评论人头像
			owner.setHeadImage(comment.getHeadImage());
			//TODO 评论人名字
			owner.setName(comment.getUserName());
			
			response.setOwner(owner);
			listResponse.add(response);
		}
		
		return listResponse;
	}
	//---------------------------test
	@PostMapping("/test")
	public void testcomment(@RequestBody TestCommentRequest comment) {
		// TODO Auto-generated method stub
		// 参数合法性检查
				
				if (comment == null || comment.getTarget() == null||comment.getOwner() == null
						
						|| StringUtils.isBlank(comment.getOwner().getId())
						|| StringUtils.isBlank(comment.getTarget().getId())
						|| StringUtils.isBlank(comment.getTarget().getType())) {
					// FIXME 修改为客户端必要参数缺失，请检查
					throw new InvalidParameterException("参数错误");
				}
				
				// 检查type是否在支持列表中
				
				SupportTargetType support_target_type = SupportTargetType.fromName(comment.getTarget().getType());
				if (support_target_type == null) {
					// FIXME 修改为不支持的点赞目标类型
					throw  new NullPointerException("SupportTargetType is null");
				
				}
				Comment comment2 = new Comment();
				comment2.setUserAid(comment.getOwner().getId());
				comment2.setUserName(comment.getOwner().getName());
				comment2.setHeadImage(comment.getOwner().getHeadImage());
				comment2.setContent(comment.getContent());
				comment2.setTargetId(comment.getTarget().getId());
				comment2.setTargetType(comment.getTarget().getType());
				
				comment2.setAppId("test");
				//包装点赞对象
					
				commentService.commentObject(comment2);
	}

}
