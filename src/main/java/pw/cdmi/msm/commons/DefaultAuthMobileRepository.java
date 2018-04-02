package pw.cdmi.msm.commons;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

@Component
public class DefaultAuthMobileRepository  implements AuthMobileRepository{

	@Autowired
	private CacheManager cacheManager;
	
	@Override
	public void save(String key, String value) {
		Cache cache = cacheManager.getCache("msm-cache");
		cache.put(key, value);		
	}

	@Override
	public void deleteObject(String key) {
		Cache cache = cacheManager.getCache("msm-cache");
		cache.evict(key);
		
	}

	@Override
	public Object getValue(String key) {
		Cache cache = cacheManager.getCache("msm-cache");
		ValueWrapper valueWrapper = cache.get(key);
		if(valueWrapper==null){
			return null;
		}
		return valueWrapper.get();
	}

	
}
