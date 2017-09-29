package com.zw.se2.self.model;

import java.util.List;

/**
 * Created by zhaoenwei on 2017/7/26.
 */
public class WeekReport {
    private Integer id;
    private Long createTime;
    private String doneInfo;
    private String planInfo;
    private String problemInfo;
    private String projectInfo;
    private String offWorkInfo;//请假
    private String overtimeInfo;//加班
    private String businessOutInfo;//出差信息
    private Integer userId;
    private String userName;//用户名称
    private Integer orgId;
    private List<WorkItem> planWorkItem;//计划的工作条目
    private List<WorkItem> doneWorkItem;//完成的工作条目

    public List<WorkItem> getPlanWorkItem() {
        return planWorkItem;
    }

    public void setPlanWorkItem(List<WorkItem> planWorkItem) {
        this.planWorkItem = planWorkItem;
    }

    public List<WorkItem> getDoneWorkItem() {
        return doneWorkItem;
    }

    public void setDoneWorkItem(List<WorkItem> doneWorkItem) {
        this.doneWorkItem = doneWorkItem;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    @Override
    public String toString() {
        return "WeekReport{" +
                "id=" + id +
                ", createTime=" + createTime +
                ", doneInfo='" + doneInfo + '\'' +
                ", planInfo='" + planInfo + '\'' +
                ", problemInfo='" + problemInfo + '\'' +
                ", projectInfo='" + projectInfo + '\'' +
                ", offWorkInfo='" + offWorkInfo + '\'' +
                ", overtimeInfo='" + overtimeInfo + '\'' +
                ", businessOutInfo='" + businessOutInfo + '\'' +
                ", userId=" + userId +
                ", userName='" + userName + '\'' +
                ", orgId=" + orgId +
                ", planWorkItem=" + planWorkItem +
                ", doneWorkItem=" + doneWorkItem +
                '}';
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public String getDoneInfo() {
        return doneInfo;
    }

    public void setDoneInfo(String doneInfo) {
        this.doneInfo = doneInfo;
    }

    public String getPlanInfo() {
        return planInfo;
    }

    public void setPlanInfo(String planInfo) {
        this.planInfo = planInfo;
    }

    public String getProblemInfo() {
        return problemInfo;
    }

    public void setProblemInfo(String problemInfo) {
        this.problemInfo = problemInfo;
    }


    public String getProjectInfo() {
        return projectInfo;
    }

    public void setProjectInfo(String projectInfo) {
        this.projectInfo = projectInfo;
    }

    public String getOffWorkInfo() {
        return offWorkInfo;
    }

    public void setOffWorkInfo(String offWorkInfo) {
        this.offWorkInfo = offWorkInfo;
    }

    public String getOvertimeInfo() {
        return overtimeInfo;
    }

    public void setOvertimeInfo(String overtimeInfo) {
        this.overtimeInfo = overtimeInfo;
    }

    public String getBusinessOutInfo() {
        return businessOutInfo;
    }

    public void setBusinessOutInfo(String businessOutInfo) {
        this.businessOutInfo = businessOutInfo;
    }
}
