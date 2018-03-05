package pw.cdmi.msm.comment.rs.v1;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
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
import pw.cdmi.msm.comment.model.entities.Comment;
import pw.cdmi.msm.comment.rs.CommentRequest;
import pw.cdmi.msm.comment.rs.ListCommentResponse;
import pw.cdmi.msm.comment.rs.Owner;
import pw.cdmi.msm.comment.service.CommentService;
import pw.cdmi.msm.comment.model.SupportTargetType;
import pw.cdmi.msm.praise.service.FileService;
@RestController
@RequestMapping("comments/v1")
public class CommentResourceImpl implements CommentResource {

	@Autowired
	private CommentService commentService;
	@Autowired
	private FileService fileService;
	
	@PostMapping
	@Override
	public void comment(@RequestBody CommentRequest comment) {
		// TODO Auto-generated method stub
		// 参数合法性检查
				/*
				if (comment == null || StringUtils.isBlank(comment.getOwnerId()) || comment.getTarget() == null
						|| StringUtils.isBlank(comment.getTarget().getId())
						|| StringUtils.isBlank(comment.getTarget().getType())) {
					// FIXME 修改为客户端必要参数缺失，请检查
					throw new AWSClientException(ClientError.InvalidParameter, ClientReason.InvalidParameter);
				}
				*/
				// 检查type是否在支持列表中
				
				SupportTargetType support_target_type = SupportTargetType.fromName(comment.getTarget().getType());
				if (support_target_type == null) {
					// FIXME 修改为不支持的点赞目标类型
				//	throw new AWSClientException(ClientError.InvalidParameter, ClientReason.InvalidParameter);
				
				}
				//包装点赞对象
				synchronized (this) {
					switch (support_target_type) {
					case Tenancy_File:
						
						// 获取租户文件，並鎖定刪除，以及鎖定當前操作用戶刪除
						
						//判断是否文件存在
						if(fileService.inspectExist(comment.getTarget().getId(), comment.getTarget().getType())){
							// FIXME 该租户文件被删除
							//	throw new AWSClientException(ClientError.InvalidParameter, ClientReason.InvalidParameter);
							System.out.println("目标不存在");
						}		
											
						// 如果租戶文件存在，新增一個點贊信息，釋放鎖定刪除	
						commentService.commentObject(comment);
						
						break;
					case Tenancy_Comment:
						
						// 获取评论信息，並鎖定刪除，以及鎖定當前操作用戶刪除
						if(commentService.inspectExist(comment.getTarget().getId(), comment.getTarget().getType())){
							// FIXME 该租户文件被删除
							//	throw new AWSClientException(ClientError.InvalidParameter, ClientReason.InvalidParameter);
							System.out.println("目标不存在");
						}	
						commentService.commentObject(comment);
						
						
						// 如果评论存在，新增一個點贊信息，釋放鎖定刪除
						break;
					case Tenancy_User:
						// 获取租户用户，並鎖定刪除，以及鎖定當前操作用戶刪除
						
						// 如果租戶用戶存在，新增一個點贊信息，釋放鎖定刪除
						break;
					default:
					//	throw new AWSServiceException(SystemReason.UnImplemented);
					}
				}
	}
	@GetMapping("/target/{target_id}/amount")
	@Override
	public @ResponseBody long getCommentAmount(@PathVariable("target_id")String id,@RequestParam("type") String type) {
		// TODO Auto-generated method stub
		return commentService.countComment(id,type);
	}
	@GetMapping("target/{id}")
	@Override
	public List<ListCommentResponse> listComment(@PathVariable("id") String id, @RequestParam("type") String type) {
		// TODO Auto-generated method stub
		return toListCommentResponse(commentService.commentList(id,type));
	}
	
	@DeleteMapping("/{id}")
	public void delComment(@RequestParam(value="userid",required=true)String userId,@PathVariable("id")String commentId){
		//TODO  从Auth信息中获得请求用户的信息
		//判断该用户是否具有管理权限，可以删除他人发布的评论信息
		
		if(userId != null){
			//管理员删除指定用户发布的评论信息
			Comment findComment = commentService.findComment(commentId);
			
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
	}

	
	//@DeleteMapping("/{id}")
	public void delComment(@PathVariable("id")String commentId){
		//删除用户自己发布过的评论信息
		//TODO 从Auth信息中获取当前操作用户
		//   检查要删除的评论id是否为当前操作用户所拥有
	//	Comment comment = commentService.get(commentId);
		
		commentService.deleteComment(commentId);
	}
	
	
	private List<ListCommentResponse> toListCommentResponse(List<Comment> listComment) {
		Iterator<Comment> it = listComment.iterator();
		List<ListCommentResponse> listResponse = new ArrayList<ListCommentResponse>();
		while(it.hasNext()){
			ListCommentResponse response = new ListCommentResponse();
			Comment comment=(Comment)it.next();
			response.setContent(comment.getContent());
			response.setCreate_time(comment.getCreateTime().toString());
			response.setId(comment.getId());
			Owner owner = new Owner();
			owner.setId(comment.getCommentatorId());
			//TODO 评论人类型
			
			//TODO 评论人头像
			
			//TODO 评论人名字
			
			
			response.setOwner(owner);
			listResponse.add(response);
		}
		
		return listResponse;
	}

}
