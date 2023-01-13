/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.coursemanagementsystem.client.beans;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import org.springframework.stereotype.Component;

/**
 *
 * @author hng
 */
@Component
public class Timetable {
    
    
    public void generate() throws JRException{
        JasperReport jasperReport = JasperCompileManager.compileReport("Timetable.jrxml");
        
        JRDataSource jrDataSource = new JREmptyDataSource();
        
        JasperPrint print = JasperFillManager.fillReport(jasperReport, null,jrDataSource);
        JasperExportManager.exportReportToPdfFile(print,"Timetable.pdf");
        
    }
}
