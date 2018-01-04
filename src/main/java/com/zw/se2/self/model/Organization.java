package com.zw.se2.self.model;

/**
 * Created by zhaoenwei on 2017/7/26.
 */
public class Organization {
    private int id;
    private String name;
    private Organization parentOrg;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Organization getParentOrg() {
        return parentOrg;
    }

    public void setParentOrg(Organization parentOrg) {
        this.parentOrg = parentOrg;
    }
}
