/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.coursemanagementsystem.client.beans;


import com.coursemanagementsystem.client.CourseManagementSystem;
import com.coursemanagementsystem.client.db.DBConnection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class Course {

    public String name;
    public String profes;
    public int max;
    public int yr;
    public int startTime;
    public int duration;
    public String day;

    public List<String> studenrolled = new ArrayList<>();
    private static DBConnection db = new DBConnection();
    
    public Course(){}
    
    public Course(String c_name, int max, String prof, int yr, int startTime, int duration,String day) throws SQLException {
        this.name = c_name;
        this.profes = prof;
        this.max = max;
        this.yr = yr;        
        this.startTime = startTime;
        this.duration = duration;
        this.day = day;
    }
    
    
    public void editDetail(String name,int max, int year) throws SQLException {
        String oldname = this.name;
        this.name = name;
        this.max = max;
        this.yr = year;        
        db.update("UPDATE course SET name='"+name+"',max="+max+",year="+year+" WHERE name='"+oldname+"';");
    }
    

    public void modify(String a, String newprof) throws SQLException {
        int cidx = 0;
        for (int i = 0; i < CourseManagementSystem.c.size(); i++) {
            if (CourseManagementSystem.c.get(i).name.equals(a)) {
                cidx = i;
                break;
            }
        }
        CourseManagementSystem.c.get(cidx).profes = newprof;
        db.update("UPDATE course SET prof='"+newprof+"' WHERE name='"+a+"';");
    }
    
    public void show(String a) {
        int cidx = 0;
        for (int i = 0; i < CourseManagementSystem.c.size(); i++) {
            if (CourseManagementSystem.c.get(i).name.equals(a)) {
                cidx = i;
                break;
            }
        }
        System.out.println("Course_Name:" + CourseManagementSystem.c.get(cidx).name + " ");
        System.out.println("Course_prof:" + CourseManagementSystem.c.get(cidx).profes + " ");
        System.out.println("Course_max:" + CourseManagementSystem.c.get(cidx).max + " ");
        System.out.println("Course_pre_year:" + CourseManagementSystem.c.get(cidx).yr + " ");
        System.out.println("Course_time:"+ CourseManagementSystem.c.get(cidx).day+" ("+ CourseManagementSystem.c.get(cidx).startTime+"00 - "+ (CourseManagementSystem.c.get(cidx).startTime+CourseManagementSystem.c.get(cidx).duration)+"00)");
        if (CourseManagementSystem.c.get(cidx).studenrolled.isEmpty()) {
            System.out.println("Course_students: No_Students_in_this_course");
        } else {
            System.out.print("Course_students:");
            for (int i = 0; i < CourseManagementSystem.c.get(cidx).studenrolled.size(); i++) {
                System.out.print(CourseManagementSystem.c.get(cidx).studenrolled.get(i) + " ");
            }
            System.out.println();
        }
    }
}
