package pw.cdmi.msm.praise.repositories.mongo;


import org.springframework.data.mongodb.repository.Query;



public interface MongoPraiseRepsitory {
	
    
    @Query(count=true ,value="{'name' : ?0}")
    public long countByName(String name);
    
}
