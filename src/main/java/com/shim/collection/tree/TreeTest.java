package com.shim.collection.tree;

import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * @类描述：
 * @创建人：xn064961
 * @创建时间：2017/4/27 17:06
 * @版权：Copyright (c) 深圳市牛鼎丰科技有限公司-版权所有.
 */
public class TreeTest {
    public static void main(String[] s){
        TreeTest test = new TreeTest();
        Role a = new Role("Aid", "A");
        List<Role> data = test.convertTree(a);
        Set<String> roleIds = new HashSet<>();
        for (Role item : data) {
            roleIds.add(item.getRoleId());
        }
        Set<String> sets = test.getAllLeafRole(data, roleIds);
    }

    private List<Role> convertTree(Role a){
        List<Role> data = new ArrayList<>();
        Role b = new Role("Bid", "B");
        Role c = new Role("Cid", "C");
        Role d = new Role("Did", "D");
        Role e = new Role("Eid", "E");
        Role f = new Role("Fid", "F");
        Role g = new Role("Gid", "G");

        a.setChilds(Arrays.asList(b, c));
        b.setChilds(Arrays.asList(d, e));
        c.setChilds(Arrays.asList(f, g));

        data.add(b);
        data.add(d);
        data.add(c);
        return data;
    }

    private Set<String> getAllLeafRole(List<Role> roles, Set<String> roleIds) {
        Set<String> childIds = new HashSet<>();
        for (Role item : roles) {
            childIds.addAll(getAllChild(item, roleIds));
        }
        return childIds;
    }

    public Set<String> getAllChild(Role role, Set<String> roleIds){
        Set<String> childIds = new HashSet<>();

        List<Role> roles = role.getChilds();
        if (!CollectionUtils.isEmpty(roles)) {
            for(Role item : roles){
                if (roleIds.contains(item.getRoleId())){
                    return childIds;
                }
            }
        }

        childIds.add(role.getRoleId());
        return childIds;
    }
}
