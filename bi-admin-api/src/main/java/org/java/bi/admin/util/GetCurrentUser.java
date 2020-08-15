package org.java.bi.admin.util;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.java.bi.db.domain.SysBiUser;

public class GetCurrentUser {
    /**
     * 获取当前后台操作用户的ID
     * @return
     */
    public static Integer getCurrentUserId() {
        Subject currentUser = SecurityUtils.getSubject();
        SysBiUser admin = (SysBiUser) currentUser.getPrincipal();
        return admin.getId();
    }
    public static String getCurrentUserName() {
        Subject currentUser = SecurityUtils.getSubject();
        SysBiUser admin = (SysBiUser) currentUser.getPrincipal();
        return admin.getUsername();
    }
    public static String getCurrentUserIdStr() {
        Subject currentUser = SecurityUtils.getSubject();
        SysBiUser admin = (SysBiUser) currentUser.getPrincipal();
        return admin.getId().toString();
    }
    public static Integer getCurrentUserIdInteger() {
        Subject currentUser = SecurityUtils.getSubject();
        SysBiUser admin = (SysBiUser) currentUser.getPrincipal();
        return admin.getId();
    }
    public static Integer getCurrentUserDeptId() {
        Subject currentUser = SecurityUtils.getSubject();
        SysBiUser admin = (SysBiUser) currentUser.getPrincipal();
        return admin.getDeptId();
    }

    public static String getCurrentUserDeptIdStr() {
        Subject currentUser = SecurityUtils.getSubject();
        SysBiUser admin = (SysBiUser) currentUser.getPrincipal();
        return admin.getDeptId().toString();
    }
    public static String getCurrentUserDeptName() {
        Subject currentUser = SecurityUtils.getSubject();
        SysBiUser admin = (SysBiUser) currentUser.getPrincipal();
        return admin.getDeptName().toString();
    }
    /**
     * 获取当前后台操作用户的结构
     * @return
     */
    public static SysBiUser getCurrentUser() {
        Subject currentUser = SecurityUtils.getSubject();
        SysBiUser admin = (SysBiUser) currentUser.getPrincipal();
        return admin;
    }

}
