package pw.cdmi.msm.praise.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;

import com.mysql.fabric.xmlrpc.base.Array;

import pw.cdmi.msm.praise.model.PraiseTarget;
import pw.cdmi.msm.praise.model.entities.Praise;
import pw.cdmi.msm.praise.repositories.PraiseRepsitory;
import pw.cdmi.msm.praise.service.PraiseService;

@Component
public class PraiseServiceImpl implements PraiseService {

	@Inject
    private PraiseRepsitory praiseRepsitory;  
	
	@Override
	public void praiseObject(String account_id, PraiseTarget target) {
		//FIXME 自动创建包含当前用户请求环境信息，并设置到对象中。
		Praise praise = new Praise();
		praise.setUserUid(account_id);
		praise.setTargetId(target.getId());
		praise.setTargetType(target.getType());
		praise.setCreatetime(new Date());
		praiseRepsitory.save(praise);	
		
	}

	@Override
	public long getPrainseNumber(String target_id, String target_type) {
		return praiseRepsitory.countByTargetIdAndTargetType(target_id,target_type);
		
	}
	@Override
	public List<String> listPraiser(String target_id,String target_type) {
		//FIXME 根据认证条件自动组成查询范围条件
		List<Praise> findList = praiseRepsitory.findByTargetIdAndTargetType(target_id,target_type);
		Iterator<Praise> it_praise = findList.iterator();
		List<String> idList = new ArrayList<String>();
	
		while (it_praise.hasNext()) {
			Praise praise = (Praise) it_praise.next();
			idList.add(praise.getUserUid());
		}
		
		return idList;
//		return praiseRepsitory.findAll();
	}

	@Override
	public boolean inspectExist(String account_id, PraiseTarget target) {
		// TODO Auto-generated method stub
		 
		if(praiseRepsitory.countByUserUidAndTargetIdAndTargetType(account_id, target.getId(), target.getType())>0)
			return true;
		else
		return false;
	}


}
