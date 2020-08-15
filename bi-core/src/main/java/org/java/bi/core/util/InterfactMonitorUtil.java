package org.java.bi.core.util;

import org.java.bi.db.domain.SysInterfaceMonitor;
import org.java.bi.db.service.SysInterfaceMonitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class InterfactMonitorUtil {

    @Autowired
    private SysInterfaceMonitorService interfaceMonitorService;

    public void writeCRMInterfaceMonitorError(String strLogType,String strLogContent,String errMsg){
        SysInterfaceMonitor interfaceMonitor =new SysInterfaceMonitor();
        interfaceMonitor.setLogSource("机场CRM");
        interfaceMonitor.setLogDirection("调用他方接口");
        interfaceMonitor.setLogType(strLogType);
        interfaceMonitor.setLogContent(strLogContent);
        interfaceMonitor.setLogSyncType("手工同步");
        interfaceMonitor.setLogStateDesc("失败");
        interfaceMonitor.setLogStartTime(LocalDateTime.now());
        interfaceMonitor.setLogEndTime(LocalDateTime.now());
        interfaceMonitor.setErrorDate(LocalDateTime.now());
        interfaceMonitor.setErrorMsg(errMsg);
        interfaceMonitor.setAddTime(LocalDateTime.now());
        interfaceMonitor.setUpdateTime(LocalDateTime.now());

        interfaceMonitorService.add(interfaceMonitor);
    }
}
