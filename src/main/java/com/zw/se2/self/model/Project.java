package com.zw.se2.self.model;

import java.util.List;

//项目
public class Project extends BaseEntity{
    private String name;
    private String description;
    private List<User> members;
    private List<ProjectTarget> targets;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<User> getMembers() {
        return members;
    }

    public void setMembers(List<User> members) {
        this.members = members;
    }

    @Override
    public String toString() {
        return "Project{" +
                ", name='" + name + '\'' +
                ", members=" + members +
                '}';
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
