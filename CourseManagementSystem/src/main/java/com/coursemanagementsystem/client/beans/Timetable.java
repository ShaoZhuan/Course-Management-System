/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.coursemanagementsystem.client.beans;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Table;
import java.io.FileNotFoundException;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class Timetable {
    private Student student;

    public void setStudent(Student student) {
        this.student = student;
    }
    
    

    public void generateIText() {
        String fileName = "Timetable.pdf";
        try {
            PdfWriter writer = new PdfWriter(fileName);
            PdfDocument pdfdoc = new PdfDocument(writer);
            pdfdoc.addNewPage();            
            Document document = new Document(pdfdoc);
            float columnWidth[] = {100f, 200f, 200f, 200f, 200f, 200f};
            Table table = new Table(columnWidth);

            List<String> day = List.of("Monday", "Tuesday", "Wednesday", "Thursday", "Friday");

            table.addCell(new Cell().add("Time\\Day"));
            for (int i = 0; i < day.size(); i++) {
                table.addCell(new Cell().add(day.get(i)));
            }
            for (int i = 8; i <= 17; i++) {
                table.addCell(new Cell().add(i + ":00"));
                for (int j = 0; j < day.size(); j++) {
                    String currentday = day.get(j);
                    String courseName="";
                    for (int k = 0; k < student.coursetaken.size(); k++) {
                        Course course = student.coursetaken.get(k);
                        int courseET = course.startTime+course.duration;
                        if(course.day.equalsIgnoreCase(currentday) && ( i>=course.startTime && i<courseET )){
                            courseName = course.name;
                        }
                    }
                    table.addCell(new Cell().add(courseName));
                }

            }

            document.add(table);

            document.close();
            System.out.println("Timetable is generated. Please check your file");
        } catch (FileNotFoundException e) {
            System.out.println("File is not found");
        }
    }
}
