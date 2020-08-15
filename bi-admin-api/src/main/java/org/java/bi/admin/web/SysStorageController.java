package org.java.bi.admin.web;

import io.swagger.annotations.Api;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.java.bi.admin.util.GetCurrentUser;
import org.java.bi.core.storage.StorageService;
import org.java.bi.core.util.ResponseUtil;
import org.java.bi.db.domain.SysStorage;
import org.java.bi.db.service.SysStorageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/admin/storage")
@Validated
@Api(description = "后台管理/对象存储:/admin/storage")
public class SysStorageController {
    private final Log logger = LogFactory.getLog(SysStorageController.class);

    @Autowired
    private StorageService storageService;
    @Autowired
    private SysStorageService litemallStorageService;

//    @RequiresPermissions("admin:storage:list")
//    @RequiresPermissionsDesc(menu={"系统管理" , "对象存储"}, button="查询")
    @GetMapping("/list")
    public Object list(String key, String name,
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer limit,
                       @RequestParam(defaultValue = "update_time desc") String sort
                       ) {
        List<SysStorage> storageList = litemallStorageService.querySelective(key, name, page, limit, sort);
        return ResponseUtil.okList(storageList);
    }

//    @RequiresPermissions("admin:storage:create")
//    @RequiresPermissionsDesc(menu={"系统管理" , "对象存储"}, button="上传")
    @PostMapping("/create")
    public Object create(@RequestParam("file") MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        SysStorage litemallStorage = storageService.store(file.getInputStream(), file.getSize(), file.getContentType(), originalFilename);
        return ResponseUtil.ok(litemallStorage);
    }

//    @RequiresPermissions("admin:storage:read")
//    @RequiresPermissionsDesc(menu={"系统管理" , "对象存储"}, button="详情")
    @PostMapping("/read")
    public Object read(@NotNull Integer id) {
        SysStorage storageInfo = litemallStorageService.findById(id);
        if (storageInfo == null) {
            return ResponseUtil.badArgumentValue();
        }
        return ResponseUtil.ok(storageInfo);
    }

//    @RequiresPermissions("admin:storage:update")
//    @RequiresPermissionsDesc(menu={"系统管理" , "对象存储"}, button="编辑")
    @PostMapping("/update")
    public Object update(@RequestBody SysStorage litemallStorage) {
        litemallStorage.setUpdateBy(GetCurrentUser.getCurrentUserName());
        if (litemallStorageService.update(litemallStorage) == 0) {
            return ResponseUtil.updatedDataFailed();
        }
        return ResponseUtil.ok(litemallStorage);
    }

//    @RequiresPermissions("admin:storage:delete")
//    @RequiresPermissionsDesc(menu={"系统管理" , "对象存储"}, button="删除")
    @PostMapping("/delete")
    public Object delete(@RequestBody SysStorage litemallStorage) {
        String key = litemallStorage.getKey();
        if (StringUtils.isEmpty(key)) {
            return ResponseUtil.badArgument();
        }
        litemallStorageService.deleteByKey(key);
        storageService.delete(key);
        return ResponseUtil.ok();
    }

    @PostMapping("/upload")
    public Object upload(@RequestParam("file") MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        SysStorage litemallStorage = storageService.store(file.getInputStream(), file.getSize(), file.getContentType(), originalFilename);
        return ResponseUtil.ok(litemallStorage);
    }

    /**
     * 访问存储对象
     *
     * @param key 存储对象key
     * @return
     */
    @GetMapping("/fetch/{key:.+}")
    public ResponseEntity<Resource> fetch(@PathVariable String key) {
        SysStorage litemallStorage = litemallStorageService.findByKey(key);
        if (key == null) {
            return ResponseEntity.notFound().build();
        }
        if (key.contains("../")) {
            return ResponseEntity.badRequest().build();
        }
        if(litemallStorage==null){
            return null;
        }
        String type = litemallStorage.getType();
        MediaType mediaType = MediaType.parseMediaType(type);

        Resource file = storageService.loadAsResource(key);
        if (file == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().contentType(mediaType).body(file);
    }

    /**
     * 访问存储对象
     *
     * @param key 存储对象key
     * @return
     */
    @GetMapping("/download/{key:.+}")
    public ResponseEntity<Resource> download(@PathVariable String key) {
        SysStorage litemallStorage = litemallStorageService.findByKey(key);
        if (key == null) {
            return ResponseEntity.notFound().build();
        }
        if (key.contains("../")) {
            return ResponseEntity.badRequest().build();
        }

        String type = litemallStorage.getType();
        MediaType mediaType = MediaType.parseMediaType(type);

        Resource file = storageService.loadAsResource(key);
        if (file == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().contentType(mediaType).header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

}
