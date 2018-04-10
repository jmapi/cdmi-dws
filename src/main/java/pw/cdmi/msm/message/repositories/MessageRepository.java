package pw.cdmi.msm.message.repositories;

import java.util.Collection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import pw.cdmi.msm.message.model.entity.Message;

@NoRepositoryBean
public interface MessageRepository extends PagingAndSortingRepository<Message, String>,QueryByExampleExecutor<Message>{

	
}
