package pw.cdmi.msm.sms.repository;

import java.util.Map;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import pw.cdmi.common.cache.CacheClient;
import pw.cdmi.common.cache.CacheClientFactory;
import pw.cdmi.common.cache.config.CacheConfig;
import lombok.Data;

@Data
@Configuration
@ConfigurationProperties(prefix = "cache")
public class MemCacheConfig {
	private String servers;

	private int port;

	private boolean supported;

	private Map<String, Object> pool;

	@Bean
	    public CacheClient cachedClient(){
	    	System.out.println(servers+pool.get("timeout").toString() + pool.toString());
	    	
	        return CacheClientFactory.getInstance((String) pool.get("name"), servers, port, 
	        		(int)pool.get("maxConnections"), (int)pool.get("socketTimeout"), (int)pool.get("socketConnectTimeout"), 
	        		Long.valueOf(pool.get("timeout").toString()), (String)pool.get("prefix"),  (boolean)pool.get("binaryProtocal"), 
	        		(boolean)pool.get("aliveCheck"), (boolean)pool.get("failback"),  (boolean)pool.get("failover"), (int)pool.get("opTimeout"));
	    }

}
