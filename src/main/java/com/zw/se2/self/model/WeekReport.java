package com.zw.se2.self.model;

/**
 * Created by zhaoenwei on 2017/7/26.
 */
public class WeekReport {
    private Integer id;
    private Long createTime;
    private String doneInfo;
    private String planInfo;
    private String problemInfo;
    private String projectName;
    private String roleName;
    private String offWorkInfo;//请假
    private String overtimeInfo;//加班
    private String businessOutInfo;//出差信息
    private Integer userId;

    @Override
    public String toString() {
        return "WeekReport{" +
                "id=" + id +
                ", createTime=" + createTime +
                ", doneInfo='" + doneInfo + '\'' +
                ", planInfo='" + planInfo + '\'' +
                ", problemInfo='" + problemInfo + '\'' +
                ", projectName='" + projectName + '\'' +
                ", roleName='" + roleName + '\'' +
                ", offWorkInfo='" + offWorkInfo + '\'' +
                ", overtimeInfo='" + overtimeInfo + '\'' +
                ", businessOutInfo='" + businessOutInfo + '\'' +
                ", userId='" + userId + '\'' +
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


    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
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
