package pw.cdmi.msm.comment.rs.v1;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pw.cdmi.msm.comment.model.entities.Comment;
import pw.cdmi.msm.comment.rs.CommentRequest;
import pw.cdmi.msm.comment.rs.ListCommentResponse;
import pw.cdmi.msm.comment.service.CommentService;
import pw.cdmi.msm.comment.model.SupportTargetType;
import pw.cdmi.msm.comment.model.entities.Target;
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
	public void comment(CommentRequest comment) {
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
						
						if(commentService.inspectExist(comment.getTarget().getId(), comment.getTarget().getType())){
							// FIXME 该租户文件被删除
							//	throw new AWSClientException(ClientError.InvalidParameter, ClientReason.InvalidParameter);
							System.out.println("目标不存在");
						}	
						// 获取评论信息，並鎖定刪除，以及鎖定當前操作用戶刪除
						
						
						
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
	@Override
	public int getCommentAmount(String id, String type) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<ListCommentResponse> listComment(String id, String type) {
		// TODO Auto-generated method stub
		return null;
	}

}
