package com.zw.se2.self.model;

public class ProjectTarget extends BaseEntity{
    private String content;
    private Long time;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }
}
