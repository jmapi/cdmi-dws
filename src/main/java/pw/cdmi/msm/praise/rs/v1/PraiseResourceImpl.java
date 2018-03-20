package pw.cdmi.msm.praise.rs.v1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import pw.cdmi.core.exception.InvalidParamException;
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
import pw.cdmi.msm.praise.service.PraiseService;
//import pw.cdmi.open.ClientError;
@RestController
@RequestMapping("/praise/v1")
public class PraiseResourceImpl implements PraiseResource {

	@Autowired
	private PraiseService praiseService;
	@Autowired
	private CommentService commentService;
	@PostMapping
	@Override
	public void praise(@RequestBody PraiseRequest praise) {
		//TODO 参数合法性检查
		
		if (praise == null || praise.getOwner()==null || praise.getTarget() == null
				|| StringUtils.isBlank(praise.getTarget().getId())
				|| StringUtils.isBlank(praise.getTarget().getType())
				|| StringUtils.isBlank(praise.getOwner().getId())
				|| StringUtils.isBlank(praise.getOwner().getName())
				) {
			// FIXME 修改为客户端必要参数缺失，请检查
			throw  new InvalidParamException("参数错误");
		}
		
		//TODO 检查type是否在支持列表中
		
		SupportTargetType support_target_type = SupportTargetType.fromName(praise.getTarget().getType());
		if (support_target_type == null) {
			// FIXME 修改为不支持的点赞目标类型
			throw new NullPointerException("SupperTargetType is null");
		}
		//包装点赞对象
		
		// 根据点赞目标类型，判断点赞目标对象是否存在
		synchronized (this){
		switch (support_target_type) {
		case Tenancy_File:
			
			//TODO 
			//该用户是否已经对该租户文件有点赞信息
			if(praiseService.inspectExist(praise.getOwner().getId(), praise.getTarget())){
				// FIXME 已经存在该用户对该租户文件点赞记录
					throw new SecurityException("已经点赞");
			}
			//TODO 
			// 如果租戶文件存在，新增一個點贊信息，釋放鎖定刪除	
			praiseService.praiseObject(praise);
			
			break;
		case Tenancy_Comment:
					
			
			//TODO 该用户是否已经对该租户文件有点赞信息
			if(praiseService.inspectExist(praise.getOwner().getId(), praise.getTarget())){
				// FIXME 已经存在该用户对该租户文件点赞记录
				throw new  SecurityException("已经点赞");
			}
			// 如果租戶文件存在，新增一個點贊信息，釋放鎖定刪除	
			praiseService.praiseObject(praise);
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
	public @ResponseBody Map<String, Long> getPraiseAmount(@PathVariable("target_id") String id,@RequestParam("type") String type) {
		//TODO 参数合法性检查
		if (StringUtils.isBlank(id) || StringUtils.isBlank(type)) {
			// FIXME 修改为客户端必要参数缺失，请检查
			throw  new InvalidParamException("参数错误");
		}
			
		Map<String, Long> hashMap = new HashMap<String,Long>();
		hashMap.put("amount",praiseService.getPrainseNumber(id, type));
		return hashMap;
		
	}
	@GetMapping(value="/{target_id}/praiser")
	@Override
	public @ResponseBody List<ListPraiserResponse> listPraiser(@PathVariable("target_id") String id,@RequestParam("type") String type) {
		//TODO 参数合法性检查
		if (StringUtils.isBlank(id) || StringUtils.isBlank(type)) {
			// FIXME 修改为客户端必要参数缺失，请检查
			throw  new InvalidParamException("参数错误");
		}
		//获取praise list
		List<Praise> praiseList = praiseService.listPraiser(id, type);
		if(praiseList==null){
			throw new NullPointerException("praiseList is null");
		}
		//转换
		return toListPraiserResponse(praiseList);
	}
	@GetMapping(value="/users/{userId}/praised/{target_id}")	
	public @ResponseBody Map<String, Boolean> inspectExist(@PathVariable("userId") String userId,@PathVariable("target_id") String id,@RequestParam("type") String type) {
		//TODO 参数合法性检查
		if (StringUtils.isBlank(id) || StringUtils.isBlank(type)) {
			// FIXME 修改为客户端必要参数缺失，请检查
			throw  new InvalidParamException("参数错误");
		}
		Map<String, Boolean> hashMap = new HashMap<String, Boolean>();
		
		PraiseTarget target = new PraiseTarget();
		target.setId(id);
		target.setType(type);
		
		boolean b = praiseService.inspectExist(userId, target);
	
		hashMap.put("praised", b);
		return hashMap;
	}
	/**
	 * 转化器
	 * @param praiseList
	 * @return
	 */
	private List<ListPraiserResponse> toListPraiserResponse(List<Praise> praiseList) {	
		//TODO 转化
		List<ListPraiserResponse> Responselist = new ArrayList<ListPraiserResponse>();
		Iterator<Praise> iterator = praiseList.iterator();
		
		while(iterator.hasNext()){
			
			ListPraiserResponse response = new ListPraiserResponse();
			Praise next = iterator.next();
			response.setId(next.getUserUid());
			response.setHeadImage(next.getHeadImage());
			response.setName(next.getUserName());
			Responselist.add(response);
		}
		return Responselist;
	}
}
