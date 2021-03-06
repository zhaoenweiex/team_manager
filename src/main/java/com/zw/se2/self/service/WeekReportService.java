package com.zw.se2.self.service;

import com.zw.se2.self.model.WeekReport;

import java.util.List;
import java.util.Map;

/**
 * Created by zhaoenwei on 2017/7/27.
 */
public interface WeekReportService {
    void create(WeekReport weekReport);

    List<WeekReport> searchByOrgId(WeekReport weekReport);

    List<WeekReport> findReportsByIds(String[] ids);

    String generateReport(List<WeekReport> reports,Map<String,String> basicInfo);


    List<WeekReport> searchByUserId(String userId);

    WeekReport getLastReportByUserId(String userId);
}
