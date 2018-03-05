package pw.cdmi.msm.praise.repositories;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import pw.cdmi.msm.praise.model.PraiseTarget;
import pw.cdmi.msm.praise.model.entities.Target;
public interface FileRepsitory extends PagingAndSortingRepository<Target, String>,QueryByExampleExecutor<Target>{

		//查询目标文件是否存在
		public long countByTargetIdAndType(String targetId,String Type);
}
