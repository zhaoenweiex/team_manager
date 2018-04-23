package com.zw.se2.self.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zw.se2.self.mapper.WeekReportMapper;
import com.zw.se2.self.model.WeekReport;
import com.zw.se2.self.service.WeekReportService;
import com.zw.se2.self.utils.MSWordManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Created by zhaoenwei on 2017/7/27.
 */
@Service
public class WeekReportServiceImpl implements WeekReportService {
    private static final Logger logger = LoggerFactory.getLogger(WeekReportServiceImpl.class);
    @Value("${reportPath}")
    private String reportsPath;
    @Value("${templatePath}")
    private String templatePath;
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
        StringBuilder sb = new StringBuilder();
        for (String id : ids) {
            sb.append(id).append(",");
        }
        return weekReportMapper.findInId(sb.substring(0, sb.length() - 1));
    }

    @Override
    public String generateReport(List<WeekReport> reports, Map<String, String> basicInfo) {
        //生成报告
        String filePath = reportsPath + "/" + System.currentTimeMillis() + ".doc";
        Map<String, List<String>> attenceInfoMap = generateAttenceInfo(reports);//人员名称-请假，出差，加班
        Map<String, Map<String, List<String>>> projectInfoMap = new HashMap<>();//项目名称-角色-人员
        Map<String, List<String>> workInfoMap = new HashMap<>();//人员名称-工作完成情况(包含上一周计划的对比)，计划情况
        generateReportInfo(reports, attenceInfoMap, projectInfoMap, workInfoMap);
        //请假人数
        attenceInfoMap.forEach((name, infoList) -> {
            if (infoList != null && infoList.size() > 0 && !(infoList.get(0).equalsIgnoreCase("无"))) {
                int vacationMum = 0;
                if (basicInfo.containsKey("vacationNum"))

                    vacationMum = Integer.parseInt(basicInfo.get("vacationNum"));
                basicInfo.put("vacationNum", String.valueOf(vacationMum + 1));
            }
        });


        //出差人数
        attenceInfoMap.forEach((name, infoList) -> {
            if (infoList != null && infoList.size() > 1 && !(infoList.get(1).equalsIgnoreCase("无"))) {
                int bussinessNum = 0;
                if (basicInfo.containsKey("bussinessNum"))

                    bussinessNum = Integer.parseInt(basicInfo.get("bussinessNum"));
                basicInfo.put("bussinessNum", String.valueOf(bussinessNum + 1));
            }
        });
        writeInfo2Report(filePath, attenceInfoMap, projectInfoMap, workInfoMap, basicInfo);
        return filePath;
    }

    private Map<String, List<String>> generateAttenceInfo(List<WeekReport> reports) {
        Map<String, List<String>> attenceInfoMap = new HashMap<>();//人员名称-请假，出差，加班
        for (WeekReport report : reports) {
            List<String> employeeAttenceInfo = new ArrayList<>();
            employeeAttenceInfo.add(report.getOffWorkInfo());
            employeeAttenceInfo.add(report.getBusinessOutInfo());
            employeeAttenceInfo.add(report.getOvertimeInfo());
            String userName = StringUtils.isEmpty(report.getUserName()) ? String.valueOf(report.getUserId()) : report.getUserName();
            attenceInfoMap.put(userName, employeeAttenceInfo);
            //出勤信息
            JSONObject jsonAttence = new JSONObject();
            jsonAttence.putAll(attenceInfoMap);
            logger.info(jsonAttence.toString());

        }
        return attenceInfoMap;
    }

    private void generateReportInfo(List<WeekReport> reports, Map<String, List<String>> attenceInfoMap, Map<String, Map<String, List<String>>> projectInfoMap, Map<String, List<String>> workInfoMap) {
        for (WeekReport report : reports) {
            List<String> employeeAttenceInfo = new ArrayList<>();
            employeeAttenceInfo.add(report.getOffWorkInfo());
            employeeAttenceInfo.add(report.getBusinessOutInfo());
            employeeAttenceInfo.add(report.getOvertimeInfo());
            String userName = StringUtils.isEmpty(report.getUserName()) ? String.valueOf(report.getUserId()) : report.getUserName();
            attenceInfoMap.put(userName, employeeAttenceInfo);
            if (!StringUtils.isEmpty(report.getProjectInfo())) {
                String[] projectInfos = report.getProjectInfo().split(";");
                for (String projectInfoStr : projectInfos) {
                    String[] projectInfo = projectInfoStr.split(",");
                    if (projectInfo.length < 3)
                        continue;
                    String projectName = projectInfo[0];
                    String role = projectInfo[1];
                    String ratio = projectInfo[2];
                    Map<String, List<String>> tmpProjectInfoMap = projectInfoMap.get(projectName);
                    List<String> employeeList = new ArrayList<>();
                    if (tmpProjectInfoMap == null) {
                        tmpProjectInfoMap = new HashMap<>();
                        employeeList.add(report.getUserName());
                    } else {
                        employeeList = tmpProjectInfoMap.get(role);
                        if (!employeeList.contains(report.getUserName()))
                            employeeList.add(report.getUserName());
                    }
                    tmpProjectInfoMap.put(role, employeeList);
                    projectInfoMap.put(projectName, tmpProjectInfoMap);
                }
            }


            List<String> employeeWorkInfo = new ArrayList<>();
            employeeWorkInfo.add(report.getDoneInfo());
            employeeWorkInfo.add(report.getPlanInfo());
            workInfoMap.put(userName, employeeWorkInfo);
            //出勤信息
            JSONObject jsonAttence = new JSONObject();
            jsonAttence.putAll(attenceInfoMap);
            logger.info(jsonAttence.toString());
            //项目信息
            JSONObject jsonProject = new JSONObject();
            jsonProject.putAll(projectInfoMap);
            logger.info(jsonProject.toString());
            //工作信息
            JSONObject jsonWork = new JSONObject();
            jsonWork.putAll(workInfoMap);
            logger.info(jsonWork.toJSONString());

        }
    }
    //TODO:有待进一步完善

    private void writeInfo2Report(String filePath, Map<String, List<String>> attenceInfoMap, Map<String, Map<String, List<String>>> projectInfoMap, Map<String, List<String>> workInfoMap, Map<String, String> basicInfo) {
        MSWordManager msWordManager = new MSWordManager(true);
        int pos = templatePath.lastIndexOf("//");
        Long now = System.currentTimeMillis();
        String templateCopyPath = templatePath.substring(0, pos - 1) + now + templatePath.substring(pos, templatePath.length());
        File orgTemplateFile = new File(templatePath);
        File copyTemplateFile = new File(templateCopyPath);
        try {
            FileCopyUtils.copy(orgTemplateFile, copyTemplateFile);
        } catch (IOException e) {
            logger.error("模板文件复制失败", e);
        }
        try {
            //读取模板数据
            msWordManager.openDocument(templateCopyPath);

            //总体内容替换
            basicInfo.forEach((key, value) -> {
                msWordManager.replaceAllText(markReplaceStr(key), value);
            });

            //出勤信息
            createAttenceTable(attenceInfoMap, msWordManager);
            //项目信息
            createProjectTable(projectInfoMap, msWordManager);
            //工作信息
            createWorkTable(workInfoMap, msWordManager);
            //保存数据
            msWordManager.save(filePath);
        } catch (Exception e) {
            logger.error("报告生成失败", e);
        } finally {
            msWordManager.close();
            msWordManager.closeDocument();
            if (copyTemplateFile.exists())
                copyTemplateFile.deleteOnExit();
        }

    }

    private void createWorkTable(Map<String, List<String>> workInfoMap, MSWordManager msWordManager) {

        //TODO
    }

    private void createProjectTable(Map<String, Map<String, List<String>>> projectInfoMap, MSWordManager msWordManager) {
//TODO
    }

    private void createAttenceTable(Map<String, List<String>> attenceInfoMap, MSWordManager msWordManager) {
//TODO
    }

    private void createTable(JSONObject resultJson, MSWordManager msWordManager) {
        msWordManager.replaceAllText("${table}$", "");
        msWordManager.moveDown(1);
        JSONArray tableDataArray = resultJson.getJSONArray("tableData");
        for (int iTable = 0; iTable < tableDataArray.size(); iTable++) {
            int tableIndex = iTable + 1;
            JSONObject singleTableData = tableDataArray.getJSONObject(iTable);
            JSONArray headerArray = singleTableData.getJSONArray("header");
            String[] array = new String[headerArray.size()];
            headerArray.toArray(array);
            List<String> headerList = Arrays.asList(array);
            List<List> tableData = new ArrayList<>();
            tableData.add(headerList);
            JSONArray rowsArray = singleTableData.getJSONArray("rowsData");
            for (int i = 0; i < rowsArray.size(); i++) {
                JSONArray rowData = rowsArray.getJSONArray(i);
                String[] tableRowArray = new String[rowData.size()];
                rowData.toArray(tableRowArray);
                tableData.add(Arrays.asList(tableRowArray));
            }
            //增加表格标题
            msWordManager.insertText("表格" + tableIndex + " " + singleTableData.getString("title"));
            msWordManager.moveDown(1);
            msWordManager.createTable(headerList.size(), 1);
            msWordManager.putTxtToRows(tableIndex, 1, 1, tableData);
            msWordManager.moveEnd();
        }

    }

    private String markReplaceStr(String s) {
        return "${" + s + "}$";
    }

    @Override
    public List<WeekReport> searchByUserId(String userId) {
        return weekReportMapper.findByUserId(userId);
    }

    @Override
    public WeekReport getLastReportByUserId(String userId){
       List<WeekReport> reportList= weekReportMapper.findByUserId(userId);
       if (reportList!=null&&reportList.size()>0)
       {
           return reportList.get(0);
       }else
           return null;
    }
}
