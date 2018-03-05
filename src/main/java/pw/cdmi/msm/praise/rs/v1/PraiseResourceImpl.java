package pw.cdmi.msm.praise.rs.v1;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
//import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import pw.cdmi.msm.comment.service.CommentService;
//import pw.cdmi.core.http.exception.AWSClientException;
//import pw.cdmi.core.http.exception.AWSServiceException;
//import pw.cdmi.core.http.exception.SystemReason;
//import pw.cdmi.msm.geo.ClientReason;
import pw.cdmi.msm.praise.model.SupportTargetType;
import pw.cdmi.msm.praise.rs.ListPraiserResponse;
import pw.cdmi.msm.praise.rs.PraiseRequest;
import pw.cdmi.msm.praise.service.FileService;
import pw.cdmi.msm.praise.service.PraiseService;
//import pw.cdmi.open.ClientError;
@RestController
@RequestMapping("/praise/v1")
public class PraiseResourceImpl implements PraiseResource {

	@Autowired
	private PraiseService praiseService;
	@Autowired
	private FileService fileService;
	
	@Autowired
	private CommentService commentService;
	@PostMapping
	@Override
	public void praise(@RequestBody PraiseRequest praise) {
		// 参数合法性检查
		/*
		if (praise == null || StringUtils.isBlank(praise.getOwnerId()) || praise.getTarget() == null
				|| StringUtils.isBlank(praise.getTarget().getId())
				|| StringUtils.isBlank(praise.getTarget().getType())) {
			// FIXME 修改为客户端必要参数缺失，请检查
			throw new AWSClientException(ClientError.InvalidParameter, ClientReason.InvalidParameter);
		}
		*/
		// 检查type是否在支持列表中
		
		SupportTargetType support_target_type = SupportTargetType.fromName(praise.getTarget().getType());
		if (support_target_type == null) {
			// FIXME 修改为不支持的点赞目标类型
		//	throw new AWSClientException(ClientError.InvalidParameter, ClientReason.InvalidParameter);
		}
		//包装点赞对象
		
		// 根据点赞目标类型，判断点赞目标对象是否存在
		synchronized (this){
		switch (support_target_type) {
		case Tenancy_File:
			
			// 获取租户文件，並鎖定刪除，以及鎖定當前操作用戶刪除
			System.out.println("检查是否存在target");
			//TODO
			if(fileService.inspectExist(praise.getTarget().getId(), praise.getTarget().getType())){
				// FIXME 该租户文件被删除
				//	throw new AWSClientException(ClientError.InvalidParameter, ClientReason.InvalidParameter);
				System.out.println("目标不存在");
			}		
			//该用户是否已经对该租户文件有点赞信息
			if(praiseService.inspectExist(praise.getOwnerId(), praise.getTarget())){
				// FIXME 已经存在该用户对该租户文件点赞记录
				//	throw new AWSClientException(ClientError.InvalidParameter, ClientReason.InvalidParameter);
			}
			// 如果租戶文件存在，新增一個點贊信息，釋放鎖定刪除	
			praiseService.praiseObject(praise.getOwnerId(), praise.getTarget());
			
			break;
		case Tenancy_Comment:
			// 获取租户文件，並鎖定刪除，以及鎖定當前操作用戶刪除
			System.out.println("检查是否存在target");
						
			if(commentService.inspectExist(praise.getTarget().getId(), praise.getTarget().getType())){
				// FIXME 该租户文件被删除
				//	throw new AWSClientException(ClientError.InvalidParameter, ClientReason.InvalidParameter);
				System.out.println("目标不存在");
			}		
				//该用户是否已经对该租户文件有点赞信息
			if(praiseService.inspectExist(praise.getOwnerId(), praise.getTarget())){
							// FIXME 已经存在该用户对该租户文件点赞记录
				//	throw new AWSClientException(ClientError.InvalidParameter, ClientReason.InvalidParameter);
			}
			// 如果租戶文件存在，新增一個點贊信息，釋放鎖定刪除	
			praiseService.praiseObject(praise.getOwnerId(), praise.getTarget());
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
	@GetMapping(value="/{target_id}/amount")
	@Override
	public @ResponseBody long getPraiseAmount(@PathVariable("target_id") String id,@RequestParam("type") String type) {
		// 参数合法性检查
	//	if (StringUtils.isBlank(id) || StringUtils.isBlank(type)) {
			// FIXME 修改为客户端必要参数缺失，请检查
	//		throw new AWSClientException(ClientError.InvalidParameter, ClientReason.InvalidParameter);
	//	}
		
		// 根據目標ID和類型，在點贊記錄表中查找符合條件的記錄
		System.out.println(id+"  "+type);
		
		// 點贊記錄是否存在，存在獲取其數量，不存在返回零
		return  praiseService.getPrainseNumber(id, type);
	}
	@GetMapping(value="/{target_id}/praiser")
	@Override
	public @ResponseBody List<ListPraiserResponse> listPraiser(@PathVariable("target_id") String id,@RequestParam("target_type") String type) {
		// 参数合法性检查
	//	if (StringUtils.isBlank(id) || StringUtils.isBlank(type)) {
			// FIXME 修改为客户端必要参数缺失，请检查
	//		throw new AWSClientException(ClientError.InvalidParameter, ClientReason.InvalidParameter);
	//	}
		//获取praise list
		List<String> praiseList = praiseService.listPraiser(id, type);
		
		return toListPraiserResponse(praiseList);
	}
	private List<ListPraiserResponse> toListPraiserResponse(List<String> praiseList) {
		
		/**
		 * praiselist to ListPraiserResponse 
		 */
		List<ListPraiserResponse> Responselist = new ArrayList<ListPraiserResponse>();
		Iterator<String> iterator = praiseList.iterator();
		
		while(iterator.hasNext()){
			ListPraiserResponse response=new ListPraiserResponse();
			//creat
			response.setId((String)iterator.next());
			Responselist.add(response);
		}
		return Responselist;
	}
}
