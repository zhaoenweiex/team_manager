package com.zw.se2.self.model;

/**
 * Created by zhaoenwei on 2017/7/26.
 */
public class User extends BaseEntity{
    private String name;//用户名
    private String password;//密码
    private String type;//人员类型
    private Long orgId;//组织机构id
    private String trueName;//真实姓名

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public String getTrueName() {
        return trueName;
    }

    public void setTrueName(String trueName) {
        this.trueName = trueName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
