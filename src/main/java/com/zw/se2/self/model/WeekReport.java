package com.zw.se2.self.model;

import java.util.List;

/**
 * Created by zhaoenwei on 2017/7/26.
 */
public class WeekReport extends BaseEntity{
    private Long createTime;
    private String problemInfo;
    private String offWorkInfo;//请假
    private String overtimeInfo;//加班
    private String businessOutInfo;//出差信息
    private User user;
    private Organization organization;
    private List<WorkItem> planWorkItems;//计划的工作条目
    private List<WorkItem> doneWorkItems;//完成的工作条目

    public List<WorkItem> getPlanWorkItems() {
        return planWorkItems;
    }

    public void setPlanWorkItems(List<WorkItem> planWorkItems) {
        this.planWorkItems = planWorkItems;
    }

    public List<WorkItem> getDoneWorkItems() {
        return doneWorkItems;
    }

    public void setDoneWorkItems(List<WorkItem> doneWorkItems) {
        this.doneWorkItems = doneWorkItems;
    }





    @Override
    public String toString() {
        return "WeekReport{" +
                ", createTime=" + createTime +
                ", problemInfo='" + problemInfo + '\'' +
                ", offWorkInfo='" + offWorkInfo + '\'' +
                ", overtimeInfo='" + overtimeInfo + '\'' +
                ", businessOutInfo='" + businessOutInfo + '\'' +
                ", planWorkItems=" + planWorkItems +
                ", doneWorkItems=" + doneWorkItems +
                '}';
    }


    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public String getProblemInfo() {
        return problemInfo;
    }

    public void setProblemInfo(String problemInfo) {
        this.problemInfo = problemInfo;
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

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }
}
