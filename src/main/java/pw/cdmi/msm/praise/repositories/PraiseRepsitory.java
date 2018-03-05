package pw.cdmi.msm.praise.repositories;

import java.util.List;

import org.springframework.data.domain.Example;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.stereotype.Repository;

import pw.cdmi.msm.praise.model.entities.Praise;
/**
 * **********************************************************
 * 接口类,提供对点赞数据表的系列操作
 * @author wilson
 * @version CDMI Service Platform, 2018年1月27日
 ***********************************************************
 */

 public interface PraiseRepsitory extends PagingAndSortingRepository<Praise, String>,QueryByExampleExecutor<Praise>{
	
	 public long countByTargetIdAndTargetType(String targetId,String TargetType);
	 
	 public List<Praise> findByTargetIdAndTargetType(String targetId,String TargetType);
	//检查是否已经点赞
	 public long countByUserUidAndTargetIdAndTargetType(String userUid,String targetId,String targetType);
}
