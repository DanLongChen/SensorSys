package com.sensor.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import com.sensor.entity.UserEntity;

@Repository
public class RedisDao {

	@Autowired
	private StringRedisTemplate template;

	@Autowired(required = false)
	private UserMapper mapper;

	public void setKey(String key, String value) {
		ValueOperations<String, String> ops = template.opsForValue();
	    ops.set(key, value);
	}

	public String getValue(String key) {
		ValueOperations<String, String> ops = this.template.opsForValue();
		return ops.get(key);
	}
}