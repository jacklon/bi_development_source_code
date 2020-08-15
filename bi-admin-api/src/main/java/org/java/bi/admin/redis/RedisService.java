package org.java.bi.admin.redis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;

import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.stereotype.Service;

/**
 * Redis 监控信息获取
 *
 * @author MrBird
 */
@Service("redisService")

public class RedisService {

	@Resource
	private RedisConnectionFactory redisConnectionFactory;

	/**
	 * Redis详细信息
	 */
	public List<RedisInfo> getRedisInfo() throws RedisConnectException {
		Properties info = redisConnectionFactory.getConnection().info();
		List<RedisInfo> infoList = new ArrayList<>();
		RedisInfo redisInfo = null;
		for (Map.Entry<Object, Object> entry : info.entrySet()) {
			redisInfo = new RedisInfo();
			redisInfo.setKey(oConvertUtils.getString(entry.getKey()));
			redisInfo.setValue(oConvertUtils.getString(entry.getValue()));

			infoList.add(redisInfo);
		}
		return infoList;
	}

	public Map<String, Object> getKeysSize() throws RedisConnectException {
		Long dbSize = redisConnectionFactory.getConnection().dbSize();
		Map<String, Object> map = new HashMap<>();
		map.put("create_time", System.currentTimeMillis());
		map.put("dbSize", dbSize);
        System.out.print("--getKeysSize--: " + map.toString());

		return map;
	}

	public Map<String, Object> getMemoryInfo() throws RedisConnectException {
		Map<String, Object> map = null;
		Properties info = redisConnectionFactory.getConnection().info();
		for (Map.Entry<Object, Object> entry : info.entrySet()) {
			String key = oConvertUtils.getString(entry.getKey());
			if ("used_memory".equals(key)) {
				map = new HashMap<>();
				map.put("used_memory", entry.getValue());
				map.put("create_time", System.currentTimeMillis());
			}
		}

		System.out.print("--getMemoryInfo--: " + map.toString());
		return map;
	}
}
