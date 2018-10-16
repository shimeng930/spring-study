package com.shim.collection.tree;

import java.util.List;

/**
 * @类描述：
 * @创建人：xn064961
 * @创建时间：2017/4/27 17:04
 * @版权：Copyright (c) 深圳市牛鼎丰科技有限公司-版权所有.
 */
public class Role {
    private String roleId;
    private String roleName;
    private List<Role> childs;

    public Role(){}

    public Role(String roleId, String roleName){
        this.roleId = roleId;
        this.roleName = roleName;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public List<Role> getChilds() {
        return childs;
    }

    public void setChilds(List<Role> childs) {
        this.childs = childs;
    }
}
