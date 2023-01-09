/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.coursemanagementsystem.client.beans;


import com.coursemanagementsystem.client.CourseManagementSystem;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class Course {

    public String name;
    public String profes;
    public int max;
    public int yr;

    public List<String> studenrolled = new ArrayList<String>();
    
    public Course(){}
    
    public Course(String c_name, int max, String prof, int yr) {
        this.name = c_name;
        this.profes = prof;
        this.max = max;
        this.yr = yr;
    }
    
    
    public void editDetail(String name,int max, int year) {
        this.name = name;
        this.max = max;
        this.yr = year;        
    }
    

    public static void modify(String a, String newprof) {
        int cidx = 0;
        for (int i = 0; i < CourseManagementSystem.c.size(); i++) {
            if (CourseManagementSystem.c.get(i).name.equals(a)) {
                cidx = i;
                break;
            }
        }
        CourseManagementSystem.c.get(cidx).profes = newprof;
    }
    
    public static void show(String a) {
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
