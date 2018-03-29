package pw.cdmi.msm.praise.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;
import javax.transaction.Transactional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import pw.cdmi.msm.praise.model.PraiseTarget;
import pw.cdmi.msm.praise.model.entities.Praise;
import pw.cdmi.msm.praise.repositories.PraiseRepsitory;
import pw.cdmi.msm.praise.rs.PraiseRequest;
import pw.cdmi.msm.praise.service.PraiseService;

@Component
@Transactional
public class PraiseServiceImpl implements PraiseService {

	@Inject
    private PraiseRepsitory praiseRepsitory;  
	
	@Override
	public void praiseObject(Praise praise) {
		//FIXME 自动创建包含当前用户请求环境信息，并设置到对象中。

		praise.setCreateTime(new Date());
		praiseRepsitory.save(praise);	
		
	}

	@Override
	public long getPrainseNumber(Praise praise) {
		
		return praiseRepsitory.count(Example.of(praise));		
	}
	@Override
	public Iterator<Praise> listPraiser(Praise praise,int cursor,int maxcount) {
		//FIXME 根据认证条件自动组成查询范围条件
		
		
		Sort.Order order =  new Sort.Order(Sort.Direction.DESC,"createTime");
        Sort sort = new Sort(order);	
        Pageable pageRequest = new PageRequest(cursor,maxcount,sort);
		Page<Praise> findAll = praiseRepsitory.findAll(Example.of(praise), pageRequest);
		
		return findAll.getContent().iterator();
//		return praiseRepsitory.findAll();
	}

	@Override
	public Praise inspectExist(Praise praise) {
		
		return praiseRepsitory.findOne(Example.of(praise))
			 ;
		
	}

	@Override
	public void deletePraise(Praise praise) {
		Praise findOne = praiseRepsitory.findOne(Example.of(praise));
		if(findOne==null){
			throw new SecurityException("没有删除目标");
		}
		praiseRepsitory.delete(praise.getId());
	}



}
