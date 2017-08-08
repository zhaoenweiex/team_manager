package com.zw.se2.self.service.impl;

import com.zw.se2.self.mapper.WeekReportMapper;
import com.zw.se2.self.model.WeekReport;
import com.zw.se2.self.service.WeekReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.List;

/**
 * Created by zhaoenwei on 2017/7/27.
 */
@Service
public class WeekReportServiceImpl implements WeekReportService {
    @Value("${reportPath}}")
    private String reportsPath;
    @Autowired
    private WeekReportMapper weekReportMapper;

    @PostConstruct
    public void ini() {
        File file = new File(reportsPath);
        if (!file.exists() || !file.isDirectory()) {
            file.mkdirs();
        }
    }

    @Override
    public void create(WeekReport weekReport) {
        weekReportMapper.create(weekReport);
    }

    @Override
    public List<WeekReport> search(WeekReport weekReport) {
        return weekReportMapper.findAll();
    }

    @Override
    public List<WeekReport> findReportsByIds(String[] ids) {
        return weekReportMapper.findInIds(ids);
    }

    @Override
    public String generateReport(List<WeekReport> reports) {

        //生成报告
        String filePath = reportsPath + System.currentTimeMillis() + ".doc";

        return filePath;
    }
}
