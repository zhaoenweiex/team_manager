package com.zw.se2.self.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zw.se2.self.mapper.WeekReportMapper;
import com.zw.se2.self.model.WeekReport;
import com.zw.se2.self.service.WeekReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhaoenwei on 2017/7/27.
 */
@Service
public class WeekReportServiceImpl implements WeekReportService {
    private static final Logger logger= LoggerFactory.getLogger(WeekReportServiceImpl.class);
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
    public List<WeekReport> searchByOrgId(WeekReport weekReport) {
        return weekReportMapper.findByOrgId(weekReport);
    }

    @Override
    public List<WeekReport> findReportsByIds(String[] ids) {
        return weekReportMapper.findInIds(ids);
    }

    @Override
    public String generateReport(List<WeekReport> reports) {
        //生成报告
        String filePath = reportsPath + System.currentTimeMillis() + ".doc";
        Map<String,List<String>> attenceInfoMap=new HashMap<>();//人员名称-请假，出差，加班
        Map<String,Map<String,List<String>>> projectInfoMap=new HashMap<>();//项目名称-角色-人员
        Map<String,List<String>> workInfoMap=new HashMap<>();//人员名称-工作完成情况(包含上一周计划的对比)，计划情况
        for (WeekReport report : reports) {
            List<String> employeeAttenceInfo=new ArrayList<>();
            employeeAttenceInfo.add(report.getOffWorkInfo());
            employeeAttenceInfo.add(report.getBusinessOutInfo());
            employeeAttenceInfo.add(report.getOvertimeInfo());
            attenceInfoMap.put(report.getUserName(),employeeAttenceInfo);

            String[] projectInfos=report.getProjectInfo().split(";");
            for(String projectInfoStr:projectInfos){
                String[] projectInfo=projectInfoStr.split(",");
                String projectName=projectInfo[0];
                String role=projectInfo[1];
                String ratio=projectInfo[2];
                Map<String,List<String>> tmpProjectInfoMap=projectInfoMap.get(projectName);
                List<String> employeeList=null;
                if(tmpProjectInfoMap==null){
                    tmpProjectInfoMap=new HashMap<>();
                }else{
                    employeeList=tmpProjectInfoMap.get(role);
                    if(employeeList==null){
                        employeeList=new ArrayList<>();
                    }
                    if(!employeeList.contains(report.getUserName()))
                        employeeList.add(report.getUserName());
                }
                tmpProjectInfoMap.put(role,employeeList);
                projectInfoMap.put(projectName,tmpProjectInfoMap);
            }

            List<String> employeeWorkInfo=new ArrayList<>();
            employeeWorkInfo.add(report.getDoneInfo());
            employeeWorkInfo.add(report.getPlanInfo());
            workInfoMap.put(report.getUserName(),employeeWorkInfo);
            JSONObject jsonAttence=new JSONObject();
            jsonAttence.putAll(attenceInfoMap);
            logger.info(jsonAttence.toString());
            JSONObject jsonProject=new JSONObject();
            jsonProject.putAll(projectInfoMap);
            logger.info(jsonProject.toString());
            JSONObject jsonWork=new JSONObject();
            jsonWork.putAll(workInfoMap);
            logger.info(jsonWork.toJSONString());

        }
        return filePath;
    }

    @Override
    public List<WeekReport> searchByUserId(WeekReport weekReport) {
        return weekReportMapper.findByUserId(weekReport);
    }
}
