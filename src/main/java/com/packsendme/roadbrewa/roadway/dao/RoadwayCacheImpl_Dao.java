package com.packsendme.roadbrewa.roadway.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.packsendme.roadbrewa.entity.Roadway;
import com.packsendme.roadbrewa.roadway.config.Redis_Config;

@Repository
@Transactional
public class RoadwayCacheImpl_Dao implements ICrudCache<Roadway>{
	
	private final boolean RESULT_SUCCESS = true;
	private final boolean RESULT_ERROR = false;

	@Autowired
	private RedisTemplate<String, Roadway> redisTemplate;
	
	@Autowired
	private Redis_Config cacheConfig;
	
	@Override
	public boolean add(String hashKey, Roadway entity) {
		try {
			redisTemplate.opsForHash().delete(cacheConfig.NAME_CACHE,hashKey);  
			redisTemplate.opsForHash().put(cacheConfig.NAME_CACHE, hashKey, entity);
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
			result = redisTemplate.opsForHash().delete(cacheConfig.NAME_CACHE,hashKey);
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
			return (Roadway) redisTemplate.opsForHash().get(cacheConfig.NAME_CACHE, hashKey);
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

 
}
