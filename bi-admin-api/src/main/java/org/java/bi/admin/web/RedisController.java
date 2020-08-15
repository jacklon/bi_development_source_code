package org.java.bi.admin.web;

import java.util.List;
import java.util.Map;

import io.swagger.annotations.Api;
import org.java.bi.admin.redis.RedisInfo;
import org.java.bi.admin.redis.RedisService;
import org.java.bi.core.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/admin/redis")
@Validated
@Api(description = "后台管理/Redis监控:/admin/redis")
public class RedisController {

    @Autowired
    private RedisService redisService;

    /**
     * Redis详细信息
     * @return
     * @throws Exception
     */
    @GetMapping("info")
    public Object getRedisInfo() throws Exception {
        List<RedisInfo> infoList = this.redisService.getRedisInfo();
        return ResponseUtil.okList(infoList);
    }

    @GetMapping("keysSize")
    public Object getKeysSize() throws Exception {
        Map<String, Object> result= redisService.getKeysSize();
        return ResponseUtil.ok(result);
    }

    @GetMapping("memoryInfo")
    public Object getMemoryInfo() throws Exception {
        Map<String, Object> result= redisService.getMemoryInfo();
        return ResponseUtil.ok(result);
    }
}
