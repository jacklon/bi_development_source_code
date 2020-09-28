//package org.java.bi.admin.web;
//
//import io.swagger.annotations.Api;
//import org.java.bi.ck.service.CkService;
//import org.java.bi.core.util.ResponseUtil;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.*;
//
//import java.sql.Connection;
//import java.util.List;
//import java.util.Map;
//
//@RestController
//@RequestMapping("/admin/ckdbop")
//@Validated
//@Api(description = "后台管理/登陆授权:/admin/ckdbop")
//public class CKDBOpController {
//
//    @Autowired
//    private CkService ckService;
//
//    @GetMapping("/test")
//    public Object list(Integer id, String name) {
//        String sql="select id,col1,col2,create_date from test02";
//
//        List<Map<String,String>> result= ckService.exeSql(sql);
//
//        return ResponseUtil.okList(result);
//    }
//
//
//
//
//
//}
