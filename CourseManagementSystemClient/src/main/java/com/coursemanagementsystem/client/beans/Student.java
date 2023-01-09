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
public class Student {
	public String name;
	public int rollno;
	public int year;
	public List<String> coursetaken = new ArrayList<>();
	
        public Student(){}
        
	public Student(String name,int roll,int yr){
		this.name = name;
		this.rollno = roll;
		this.year = yr;
	}
	public void enroll(String a,int myidx){		
		int cidx=0;
		for(int i=0;i<CourseManagementSystem.c.size();i++){
			if(CourseManagementSystem.c.get(i).name.equals(a)){
				cidx=i;
				break;
			}
		}
		if((CourseManagementSystem.c.get(cidx).yr<=CourseManagementSystem.s.get(myidx).year)&&CourseManagementSystem.c.get(cidx).max>0){			
			CourseManagementSystem.s.get(myidx).coursetaken.add(a);
			CourseManagementSystem.c.get(cidx).studenrolled.add(CourseManagementSystem.s.get(myidx).name);
			CourseManagementSystem.c.get(cidx).max--;			
			System.out.println("Enrollment_Success");
		}
		else{
			System.out.println("Not_eligble_to_enroll_course");
		}
	}
	
	public void unenroll(String a,int myidx){
		int cidx=0;
		for(int i=0;i<CourseManagementSystem.c.size();i++){
			if(CourseManagementSystem.c.get(i).name.equals(a)){
				cidx=i;
				break;
			}
		}
		
		if(CourseManagementSystem.s.get(myidx).coursetaken.isEmpty()){
			System.out.println("Nothing_to_unenroll");
		}
		else{
			CourseManagementSystem.s.get(myidx).coursetaken.remove(a);
			CourseManagementSystem.c.get(cidx).studenrolled.remove(CourseManagementSystem.s.get(myidx).name);
			CourseManagementSystem.c.get(cidx).max++;
			System.out.println("Unenroll_Success");
		}
	}
	
	public static void show(int myidx){
		System.out.println("Student_Name:" + CourseManagementSystem.s.get(myidx).name + " ");
		System.out.println("Student_Rollno:" + CourseManagementSystem.s.get(myidx).rollno + " ");
		System.out.println("Student_Year:" + CourseManagementSystem.s.get(myidx).year + " ");
		if(CourseManagementSystem.s.get(myidx).coursetaken.isEmpty()){
			System.out.println("Student_courses: No_courses_enrolled");
		}
		else{
			System.out.print("Student_courses:");
			for(int i=0;i<CourseManagementSystem.s.get(myidx).coursetaken.size();i++){
				System.out.print(CourseManagementSystem.s.get(myidx).coursetaken.get(i) + " ");
			}
			System.out.println();
		}
	}
}
