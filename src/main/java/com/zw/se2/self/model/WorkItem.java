package com.zw.se2.self.model;

/**
 * 工作项数据结构
 */
public class WorkItem {
    private Integer id;//主键
    private Long createTime;//创建时间
    private Integer userId;//所属用户id
    private String content;//工作内容
    //工作项状态(不是success的状态必填remark)
    // success，成功
    // delay,延期
    // delete,删除
    // adjust,调整
    private String status;
    private boolean planFlag;//是否计划中
    private String remark;//备注不是success的状态必填

    @Override
    public String toString() {
        return "WorkItem{" +
                "id=" + id +
                ", createTime=" + createTime +
                ", userId=" + userId +
                ", content='" + content + '\'' +
                ", status='" + status + '\'' +
                ", planFlag=" + planFlag +
                ", remark='" + remark + '\'' +
                '}';
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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isPlanFlag() {
        return planFlag;
    }

    public void setPlanFlag(boolean planFlag) {
        this.planFlag = planFlag;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
