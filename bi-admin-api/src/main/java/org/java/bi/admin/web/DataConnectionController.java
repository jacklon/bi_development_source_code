package org.java.bi.admin.web;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.java.bi.admin.annotation.RequiresPermissionsDesc;
import org.java.bi.admin.dto.TableDespAllinone;
import org.java.bi.admin.util.JdbcConstants;
import org.java.bi.core.util.ResponseUtil;
import org.java.bi.db.domain.BiDataConnection;
import org.java.bi.db.service.BiDataConnectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * jdbc数据源配置控制器层
 *
 * @author zhouhongfa@gz-yibo.com
 * @version v1.0
 * @since 2019-07-30
 */
@RestController
@RequestMapping("/admin/dataConnection")
@Validated
@Api(description = "后台管理/数据/同步/数据连接:/admin/dataConnection")
public class DataConnectionController {

    @Autowired
    private BiDataConnectionService biDataConnectionService;

    @GetMapping("/list")
    public Object list(String datasourceName,
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer limit,
                       @RequestParam(defaultValue = "update_time desc") String sort
    ) {

        List<BiDataConnection> result=  biDataConnectionService.querySelective(datasourceName,page,limit,sort);
        return ResponseUtil.okList(result);
    }

    /**
     * 获取所有数据源
     * @return
     */
    @ApiOperation("获取所有数据源")
    @GetMapping("/all")
    public Object all() {
        List<BiDataConnection> result=  biDataConnectionService.queryAll();
        return ResponseUtil.okList(result);
    }

    @GetMapping("/read")
    public Object read(@NotNull Integer id) {
        BiDataConnection result= biDataConnectionService.findById(id);
        return ResponseUtil.ok(result);
    }


    @PostMapping("/create")
    public Object create(@RequestBody BiDataConnection biDataConnection) {
        biDataConnectionService.add(biDataConnection);
        return ResponseUtil.ok();
    }

    @PostMapping("/update")
    public Object update(@RequestBody BiDataConnection biDataConnection) {
        biDataConnectionService.updateById(biDataConnection);
        return ResponseUtil.ok();
    }

    @PostMapping("/delete")
    public Object delete(@RequestBody BiDataConnection biDataConnection) {
        biDataConnectionService.deleteById(biDataConnection.getId());
        return ResponseUtil.ok();
    }


    @PostMapping("/test")
    public Object dataSourceTest (@RequestBody BiDataConnection biDataConnection) throws IOException {
//        Boolean result=testDataConnection(biDataConnection);
        return ResponseUtil.ok(true);
    }

//    private Boolean testDataConnection(BiDataConnection biDataConnection){
//        if (JdbcConstants.HBASE.equals(biDataConnection.getDatasource())) {
//            return new HBaseQueryTool(jobDatasource).dataSourceTest();
//        }
//        String userName =biDataConnection.getJdbcUsername();
//        String pwd = biDataConnection.getJdbcPassword();
//        if (JdbcConstants.MONGODB.equals(biDataConnection.getDatasource())) {
//            return new MongoDBQueryTool(jobDatasource).dataSourceTest(jobDatasource.getDatabaseName());
//        }
//        BaseQueryTool queryTool = QueryToolFactory.getByDbType(jobDatasource);
//        return queryTool.dataSourceTest();
//    }
}
