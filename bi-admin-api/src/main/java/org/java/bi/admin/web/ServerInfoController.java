package org.java.bi.admin.web;

import com.sun.management.OperatingSystemMXBean;
import io.swagger.annotations.Api;
import org.java.bi.core.util.CharUtil;
import org.java.bi.core.util.DateUtil;
import org.java.bi.core.util.ResponseUtil;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.lang.management.ManagementFactory;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/admin/serverInfo")
@Validated
@Api(description = "后台管理/ServerInfo:/admin/serverInfo")
public class ServerInfoController {

    /**
     * 查询磁盘空间情况
     * @return
     * @throws Exception
     */
    @GetMapping("disk")
    public Object getDiskInfo() throws Exception {
        File diskPartition = new File(".");
        long totalCapacity = diskPartition.getTotalSpace();
        long freePartitionSpace = diskPartition.getFreeSpace();
        long usablePatitionSpace = diskPartition.getUsableSpace();

        DecimalFormat df = new DecimalFormat("#.00");
        Map<String,Object> result=new HashMap<>();

        result.put("total",df.format(totalCapacity/1024/1024/1024.0));
        result.put("free",df.format(freePartitionSpace/1024/1024/1024.0));
        result.put("used",df.format(usablePatitionSpace/1024/1024/1024.0));
        result.put("time", DateUtil.getDateyyyMMddHHmmss_Format(LocalDateTime.now())) ;
        return ResponseUtil.ok(result);
    }

    @GetMapping("memory")
    public Object getCpuInfo() throws Exception {
        int kb = 1024;
        DecimalFormat df = new DecimalFormat("#.00");
        // 可使用内存
        long totalMemory = Runtime.getRuntime().totalMemory() / kb;
        // 最大可使用内存
        long maxMemory = Runtime.getRuntime().maxMemory() / kb;
        OperatingSystemMXBean osmxb = (OperatingSystemMXBean) ManagementFactory
                .getOperatingSystemMXBean();
        // 操作系统
        String osName = System.getProperty("os.name");
        // 剩余的物理内存
        long freePhysicalMemorySize = osmxb.getFreePhysicalMemorySize() / kb;
        long usedMemory = (osmxb.getTotalPhysicalMemorySize() - osmxb
                .getFreePhysicalMemorySize())
                / kb;
        // 构造返回对象
        Map<String,Object> result=new HashMap<>();
        result.put("usedMemory", CharUtil.objectConverToString(df.format(usedMemory/1024.0)));
        result.put("freePhysicalMemorySize", CharUtil.objectConverToString(df.format(freePhysicalMemorySize/1024.0)));
        result.put("osName", CharUtil.objectConverToString(osName));
        result.put("time", DateUtil.getDateyyyMMddHHmmss_Format(LocalDateTime.now()));

        return ResponseUtil.ok(result);
    }


}


