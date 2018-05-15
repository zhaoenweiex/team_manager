package com.zw.se2.self.ctrl.dto;

import com.zw.se2.self.model.WeekReport;

/**
 * @author zhaoe
 */
public class WeekReportDto extends WeekReport {
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    private String userId;

}
