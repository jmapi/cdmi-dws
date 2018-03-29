package pw.cdmi.msm.praise.rs.v1;

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
import pw.cdmi.msm.comment.repositories.CommentRepsitory;
import pw.cdmi.msm.comment.service.CommentService;
import pw.cdmi.msm.praise.model.PraiseTarget;
//import pw.cdmi.core.http.exception.AWSClientException;
//import pw.cdmi.core.http.exception.AWSServiceException;
//import pw.cdmi.core.http.exception.SystemReason;
//import pw.cdmi.msm.geo.ClientReason;
import pw.cdmi.msm.praise.model.SupportTargetType;
import pw.cdmi.msm.praise.model.entities.Praise;
import pw.cdmi.msm.praise.rs.ListPraiserResponse;
import pw.cdmi.msm.praise.rs.PraiseRequest;
import pw.cdmi.msm.praise.rs.TestPraiseRequest;
import pw.cdmi.msm.praise.service.PraiseService;
//import pw.cdmi.open.ClientError;
@RestController
@RequestMapping("/praise/v1")
public class PraiseResourceImpl {

	@Autowired
	private PraiseService praiseService;
	@Autowired
	private CommentRepsitory commentService;
	
	
	
	@PostMapping
	public void praise(@RequestBody PraiseRequest praise) {
		//TODO 参数合法性检查
		
		if (praise == null || praise.getTarget() == null
				|| StringUtils.isBlank(praise.getTarget().getId())
				|| StringUtils.isBlank(praise.getTarget().getType())
				|| StringUtils.isBlank(praise.getOwnerId())
				) {
			// FIXME 修改为客户端必要参数缺失，请检查
			throw  new InvalidParameterException("参数错误");
		}
		
		//TODO 检查type是否在支持列表中
		
		SupportTargetType support_target_type = SupportTargetType.fromName(praise.getTarget().getType());
		if (support_target_type == null) {
			// FIXME 修改为不支持的点赞目标类型
			throw new NullPointerException("SupperTargetType is null");
		}
		//包装点赞对象
		Praise praise2 = new Praise();
		praise2.setUserAid(praise.getOwnerId());
		praise2.setAppId("test");
		praise2.setTargetId(praise.getTarget().getId());
		praise2.setTargetType(praise.getTarget().getType());
		// 根据点赞目标类型，判断点赞目标对象是否存在
		synchronized (this){
		switch (support_target_type) {
		case Tenancy_File:
			
			//TODO 
			//该用户是否已经对该租户文件有点赞信息
			if(praiseService.inspectExist(praise2)!=null){
				// FIXME 已经存在该用户对该租户文件点赞记录
					throw new SecurityException("已经点赞");
			}
						
			break;
		case Tenancy_Comment:				
			
			//TODO 该用户是否已经对该租户文件有点赞信息
			if(praiseService.inspectExist(praise2)!=null){
				// FIXME 已经存在该用户对该租户文件点赞记录
				throw new  SecurityException("已经点赞");
			}
			//TODO 记录评论的点赞数
			Comment findOne = commentService.findOne(praise.getTarget().getId());
			if(findOne!=null){
				findOne.setPraiseNumber(findOne.getPraiseNumber()+1);
				commentService.save(findOne);
			}
			
			break;
		case Tenancy_User:
			// 获取租户用户，並鎖定刪除，以及鎖定當前操作用戶刪除
			
			// 如果租戶用戶存在，新增一個點贊信息，釋放鎖定刪除
			break;
		default:
		//	throw new AWSServiceException(SystemReason.UnImplemented);
		}
		praiseService.praiseObject(praise2);
	}
		

	}
	@GetMapping(value="/{target_id}/amount")
	public @ResponseBody Map<String, Long> getPraiseAmount(@PathVariable("target_id") String id,@RequestParam("type") String type) {
		//TODO 参数合法性检查
		if (StringUtils.isBlank(id) || StringUtils.isBlank(type)) {
			// FIXME 修改为客户端必要参数缺失，请检查
			throw  new InvalidParameterException("参数错误");
		}
		Praise praise = new Praise(); 
		praise.setTargetId(id);
		praise.setTargetType(type);
		praise.setAppId("test");
		Map<String, Long> hashMap = new HashMap<String,Long>();
		hashMap.put("amount",praiseService.getPrainseNumber(praise));
		return hashMap;
		
	}
	@GetMapping(value="/{target_id}/praiser")
	public @ResponseBody List<ListPraiserResponse> listPraiser(@PathVariable("target_id") String id,@RequestParam("type") String type,
			@RequestParam("cursor")int cursor,@RequestParam("maxcount")int maxcount) {
		//TODO 参数合法性检查
		if (StringUtils.isBlank(id) || StringUtils.isBlank(type)) {
			// FIXME 修改为客户端必要参数缺失，请检查
			throw  new InvalidParameterException("参数错误");
		}
		Praise praise = new Praise(); 
		praise.setTargetId(id);
		praise.setTargetType(type);
		praise.setAppId("test");
		//获取praise list
		Iterator<Praise> listPraiser = praiseService.listPraiser(praise,cursor,maxcount);
		
		//转换
		return toListPraiserResponse(listPraiser);
	}
	@GetMapping(value="/users/{userId}/praised/{target_id}")	
	public @ResponseBody Map<String, Object> inspectExist(@PathVariable("userId") String userId,@PathVariable("target_id") String id,@RequestParam("type") String type) {
		//TODO 参数合法性检查
		if (StringUtils.isBlank(id) || StringUtils.isBlank(type)) {
			// FIXME 修改为客户端必要参数缺失，请检查
			throw  new InvalidParameterException("参数错误");
		}
		Map<String, Object> hashMap = new HashMap<String, Object>();
		
		Praise praise = new Praise();
		praise.setUserAid(userId);
		praise.setAppId("test");
		praise.setTargetId(id);
		praise.setTargetType(type);
		
		Praise inspectExist = praiseService.inspectExist(praise);
		
		hashMap.put("praised", inspectExist!=null);
		if(inspectExist!=null){
			hashMap.put("id",inspectExist.getId());
		}
		return hashMap;
	}
	@DeleteMapping(value="/{id}")
	public void deletePraise(@PathVariable("id") String praiseId,@RequestParam("user_id") String  userId){
		Praise praise = new Praise();
		praise.setAppId("test");
		praise.setId(praiseId);
		praise.setUserAid(userId);
		praiseService.deletePraise(praise);
	}
	/**
	 * 转化器
	 * @param praiseList
	 * @return
	 */
	private List<ListPraiserResponse> toListPraiserResponse(Iterator<Praise> iterator) {	
		//TODO 转化
		
		List<ListPraiserResponse> list = new ArrayList<ListPraiserResponse>(); 
		while(iterator.hasNext()){
			
			ListPraiserResponse response = new ListPraiserResponse();
			Praise next = iterator.next();
			response.setId(next.getUserAid());
			response.setHeadImage(next.getHeadImage());
			response.setName(next.getUserName());
			list.add(response);
		}
		return list;
	}
	//-------------------------------------test
	@PostMapping("/test")
	public void testpraise(@RequestBody TestPraiseRequest praise) {
		//TODO 参数合法性检查
		
		if (praise == null || praise.getTarget() == null || praise.getOwner() == null
				|| StringUtils.isBlank(praise.getTarget().getId())
				|| StringUtils.isBlank(praise.getTarget().getType())
				|| StringUtils.isBlank(praise.getOwner().getId())
				) {
			// FIXME 修改为客户端必要参数缺失，请检查
			throw  new InvalidParameterException("参数错误");
		}
		
		//TODO 检查type是否在支持列表中
		
		SupportTargetType support_target_type = SupportTargetType.fromName(praise.getTarget().getType());
		if (support_target_type == null) {
			// FIXME 修改为不支持的点赞目标类型
			throw new NullPointerException("SupperTargetType is null");
		}
		//包装点赞对象
		Praise praise2 = new Praise();
		praise2.setUserAid(praise.getOwner().getId());
		praise2.setUserName(praise.getOwner().getName());
		praise2.setHeadImage(praise.getOwner().getHeadImage());
		praise2.setAppId("test");
		praise2.setTargetId(praise.getTarget().getId());
		praise2.setTargetType(praise.getTarget().getType());
		// 根据点赞目标类型，判断点赞目标对象是否存在
		synchronized (this){
		switch (support_target_type) {
		case Tenancy_File:
			
			//TODO 
			//该用户是否已经对该租户文件有点赞信息
			if(praiseService.inspectExist(praise2)!=null){
				// FIXME 已经存在该用户对该租户文件点赞记录
					throw new SecurityException("已经点赞");
			}
						
			break;
		case Tenancy_Comment:				
			
			//TODO 该用户是否已经对该租户文件有点赞信息
			if(praiseService.inspectExist(praise2)!=null){
				// FIXME 已经存在该用户对该租户文件点赞记录
				throw new  SecurityException("已经点赞");
			}
			//TODO 记录评论的点赞数
			Comment findOne = commentService.findById(praise.getTarget().getId());
			if(findOne==null){
				throw new  SecurityException("没有评论信息");
			}
			findOne.setPraiseNumber(findOne.getPraiseNumber()+1);
			commentService.save(findOne);
			
			break;
		case Tenancy_User:
			// 获取租户用户，並鎖定刪除，以及鎖定當前操作用戶刪除
			
			// 如果租戶用戶存在，新增一個點贊信息，釋放鎖定刪除
			break;
		default:
		//	throw new AWSServiceException(SystemReason.UnImplemented);
		}
		praiseService.praiseObject(praise2);
	}
	}
}
