package com.zw.se2.self.model;

import java.util.List;

//项目
public class Project extends BaseEntity{
    private String trueName;
    private String description;
    private List<User> members;
    private List<ProjectTarget> targets;

    public List<ProjectTarget> getTargets() {
        return targets;
    }

    public void setTargets(List<ProjectTarget> targets) {
        this.targets = targets;
    }

    public String getTrueName() {
        return trueName;
    }

    public void setTrueName(String trueName) {
        this.trueName = trueName;
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
                ", trueName='" + trueName + '\'' +
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
