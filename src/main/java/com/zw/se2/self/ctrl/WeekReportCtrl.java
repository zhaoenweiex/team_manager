package com.zw.se2.self.ctrl;

import com.zw.se2.self.model.WeekReport;
import com.zw.se2.self.service.WeekReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by zhaoenwei on 2017/7/26.
 */
@RestController
@RequestMapping("/week_report")
public class WeekReportCtrl {
    @Autowired
    private WeekReportService service;
    private static final Logger logger = LoggerFactory.getLogger(WeekReportCtrl.class);

    //提交周报
    @PostMapping
    public String createReport(@RequestBody WeekReport weekReport) {
        weekReport.setCreateTime(System.currentTimeMillis());
        service.create(weekReport);
        return "success";
    }

    //查询
    @GetMapping
    public List<WeekReport> getReport(WeekReport weekReport) {
        if (weekReport.getOrgId() != null)
            return service.searchByOrgId(weekReport);
        else
            return service.searchByUserId(weekReport);
    }

    //导出
    @GetMapping("export/word")
    public void exportWord(String[] ids, HttpServletResponse response) {
        ids= new String[]{"5", "6", "8"};
        //生成查询的结果
        List<WeekReport> reports = service.findReportsByIds(ids);
        //生成汇总报告
        String reportPath = service.generateReport(reports);
        File targetFile = new File(reportPath);
        //文件流导出
        FileInputStream inputStream = null;
        try {
            //将压缩文件流写入到http的返回数据流
            response.setContentType("multipart/form-data");
            response.setHeader("Content-Disposition", "attachment;fileName=" + "tmp.pdf");
            ServletOutputStream outputHttpStream = response.getOutputStream();
            inputStream = new FileInputStream(targetFile);
            int lenZipFile;
            byte[] bufZip = new byte[1024];
            while ((lenZipFile = inputStream.read(bufZip)) > 0) {
                outputHttpStream.write(bufZip, 0, lenZipFile);
            }
            outputHttpStream.flush();

        } catch (IOException e) {
            logger.error("文件压缩失败", e);
        } finally {
            try {
                inputStream.close();
            } catch (Exception e) {
                logger.error("文件流关闭失败", e);
            }
        }
    }

}
