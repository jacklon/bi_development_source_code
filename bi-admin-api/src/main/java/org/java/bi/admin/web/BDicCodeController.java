package org.java.bi.admin.web;

import io.swagger.annotations.Api;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.java.bi.core.util.ResponseUtil;
import org.java.bi.db.domain.BDicCode;
import org.java.bi.db.service.BDicCodeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin/diccode")
@Api(description = "基础数据/代码详情:/admin/diccode")
public class BDicCodeController {
    private final Log logger = LogFactory.getLog(BDicCodeController.class);

    @Autowired
    private BDicCodeService diccodeService;

    /**
     * 查询字典明细
     * @param dicmainId
     * @param dicmainName
     * @param sort
     * @return
     */
//    @RequiresPermissions("admin:diccode:list")
//    @RequiresPermissionsDesc(menu = {"基础数据", "代码详情"}, button = "查询明细")
    @GetMapping("/list")
    public Object list(Integer dicmainId, String dicmainName,
                       @RequestParam(defaultValue = "update_time desc") String sort
                       ) {
        List<BDicCode> dicCodes= diccodeService.findByDicmainIdOrDicmainName(dicmainId,dicmainName,sort);

        return ResponseUtil.okList(dicCodes);
    }

    /**
     * 根据传入的表及显示字段，返回字典列表，只显示id和name
     * @param tableName //传入的表名
     * @param displayFieldName //传入的显示字段名
     * @return
     */
    @GetMapping("/listCodeByTable")
    public Object listCodeByTable(String tableName, String displayFieldName) {
        List codeList=diccodeService.getListCodeByTable(tableName,displayFieldName);
        return ResponseUtil.okList(codeList);
    }

}
