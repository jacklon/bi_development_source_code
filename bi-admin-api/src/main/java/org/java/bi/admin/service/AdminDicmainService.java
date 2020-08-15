package org.java.bi.admin.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.java.bi.admin.dto.BDicMainAllinone;
import org.java.bi.admin.util.GetCurrentUser;
import org.java.bi.core.util.ResponseUtil;
import org.java.bi.db.domain.BDicCode;
import org.java.bi.db.domain.BDicMain;
import org.java.bi.db.service.BDicCodeService;
import org.java.bi.db.service.BDicMainService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.java.bi.admin.util.AdminResponseCode.DICMAIN_NAME_EXIST;

@Service
public class AdminDicmainService {
    private final Log logger = LogFactory.getLog(AdminDicmainService.class);

    @Autowired
    private BDicMainService litemallDicmainService;
    @Autowired
    private BDicCodeService litemallDiccodeService;


    public Object list(String name,
                       Integer page, Integer limit, String sort) {
        List<BDicMain> dicMainsList = litemallDicmainService.querySelective(name, page, limit, sort);
        return ResponseUtil.okList(dicMainsList);
    }

    public Object queryAll() {
        List<BDicMain> dicMainsList = litemallDicmainService.queryAll();
        return ResponseUtil.okList(dicMainsList);
    }

    private Object validate(BDicMainAllinone BDicMainAllinone) {
        BDicMain litemallDicMain = BDicMainAllinone.getDicmain();
        String name = litemallDicMain.getName();
        if (StringUtils.isEmpty(name)) {
            return ResponseUtil.fail(401, "字典表名称不不能为空");
        }
        BDicCode[] litemallDicCodes = BDicMainAllinone.getDiccodes();
        for (BDicCode litemallDicCode : litemallDicCodes) {
            String codeName = litemallDicCode.getName();
            if (StringUtils.isEmpty(codeName)) {
                return ResponseUtil.fail(401, "字典表代码名称不能为空");
            }
        }

        return null;
    }

    /**
     * 编辑字典表
     * <p>
     * TODO
     * 编辑字典表，需要判断是否明细主键ID已经存在，如果存在，则更新信息，如果不存在，则插入数据
     */
    @Transactional
    public Object update(BDicMainAllinone BDicMainAllinone) {
        Object error = validate(BDicMainAllinone);
        if (error != null) {
            return error;
        }
        BDicMain dicMain= BDicMainAllinone.getDicmain();
        BDicCode[] dicCodes = BDicMainAllinone.getDiccodes();

        Integer mainId = dicMain.getId();
        if(mainId==null){
            dicMain.setAddBy(GetCurrentUser.getCurrentUserName());
            dicMain.setUpdateBy(GetCurrentUser.getCurrentUserName());
            litemallDicmainService.add(dicMain);
        }else
        {
            dicMain.setUpdateBy(GetCurrentUser.getCurrentUserName());
            litemallDicmainService.updateById(dicMain);
        }

        // 商品规格表litemall_goods_specification
        for (BDicCode litemallDicCode : dicCodes) {
            litemallDicCode.setMainid(dicMain.getId());
            litemallDicCode.setMainname(dicMain.getName());
            Integer codeId=litemallDicCode.getId();
            if(codeId==null){
                litemallDicCode.setAddBy(GetCurrentUser.getCurrentUserName());
                litemallDicCode.setUpdateBy(GetCurrentUser.getCurrentUserName());
                litemallDiccodeService.add(litemallDicCode);
            } else
            {
                litemallDicCode.setUpdateBy(GetCurrentUser.getCurrentUserName());
                litemallDiccodeService.updateById(litemallDicCode);
            }
        }

        return ResponseUtil.ok();
    }

    /**
     * 删除字典表
     * @param litemallDicMain
     * @return
     */
    @Transactional
    public Object delete(BDicMain litemallDicMain) {
        Integer id = litemallDicMain.getId();
        if (id == null) {
            return ResponseUtil.badArgument();
        }
        Integer mainId = litemallDicMain.getId();
        litemallDicmainService.deleteById(mainId);
        litemallDiccodeService.deleteByMainId(mainId);
        return ResponseUtil.ok();
    }

    @Transactional
    public Object create(BDicMainAllinone BDicMainAllinone) {
        Object error = validate(BDicMainAllinone);
        if (error != null) {
            return error;
        }

        BDicMain dicMain = BDicMainAllinone.getDicmain();
        BDicCode[] dicCodes = BDicMainAllinone.getDiccodes();

        String name = dicMain.getName();
        if (litemallDicmainService.checkExistByName(name)) {
            return ResponseUtil.fail(DICMAIN_NAME_EXIST, "字典名称已经存在");
        }
        //保存字典主表
        dicMain.setAddBy(GetCurrentUser.getCurrentUserName());
        dicMain.setUpdateBy(GetCurrentUser.getCurrentUserName());
        litemallDicmainService.add(dicMain);
        // 字典明细表
        for (BDicCode litemallDicCode : dicCodes) {
            litemallDicCode.setMainid(dicMain.getId());
            litemallDicCode.setMainname(dicMain.getName());
            litemallDicCode.setAddBy(GetCurrentUser.getCurrentUserName());
            litemallDicCode.setUpdateBy(GetCurrentUser.getCurrentUserName());
            litemallDiccodeService.add(litemallDicCode);
        }
        return ResponseUtil.ok();
    }

    public Object detail(Integer id) {
        BDicMain litemallDicMain = litemallDicmainService.findById(id);
        List<BDicCode> dicCodes = litemallDiccodeService.queryByMainId(id);

        Map<String, Object> data = new HashMap<>();
        data.put("dicmain", litemallDicMain);
        data.put("diccodes", dicCodes);

        return ResponseUtil.ok(data);
    }

}
