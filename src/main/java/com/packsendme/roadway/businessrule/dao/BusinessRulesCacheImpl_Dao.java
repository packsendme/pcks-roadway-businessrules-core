package com.packsendme.roadway.businessrule.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.packsendme.roadway.commons.entity.Roadway;
import com.packsendme.roadway.businessrule.config.Redis_Config;

@Repository
@Transactional
public class BusinessRulesCacheImpl_Dao implements ICrudCache<Roadway>{
	
	private final boolean RESULT_SUCCESS = true;
	private final boolean RESULT_ERROR = false;

	@Autowired(required=true)
	private RedisTemplate<Object, Object> redisTemplate;

	@Autowired
	private Redis_Config cacheConfig;
	
	@Override
	public boolean add(String hashKey, Roadway entity) {
		try {
			redisTemplate.opsForHash().delete(cacheConfig.CACHE_ROADWAY,hashKey);  
			redisTemplate.opsForHash().put(cacheConfig.CACHE_ROADWAY, hashKey, entity);
			return RESULT_SUCCESS;
		}
		catch (Exception e) {
			e.printStackTrace();
			return RESULT_ERROR;
		}
	}

	@Override
	public boolean delete(String hashKey) {
		long result = 0;
		try {
			result = redisTemplate.opsForHash().delete(cacheConfig.CACHE_ROADWAY,hashKey);
			System.out.println(" ===========================================================  ");
			System.out.println(" RESULT DELETE  "+ result);
			System.out.println(" ===========================================================  ");
			if(result > 0) {
				return RESULT_SUCCESS;
			}
			else {
				return RESULT_ERROR;
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			return RESULT_ERROR;
		}
	}

	@Override
	public Roadway findOne(String hashKey) {
		try {
			return (Roadway) redisTemplate.opsForHash().get(cacheConfig.CACHE_ROADWAY, hashKey);
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

 
}
