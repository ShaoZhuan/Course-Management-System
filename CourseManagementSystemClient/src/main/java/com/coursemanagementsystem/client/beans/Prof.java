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
public class Prof {
	public String name;
	public String area;
	
	public List<String> coursetaking = new ArrayList<>();
	
        public Prof(){}
        
	public Prof(String name,String area){
		this.name = name;
		this.area = area;
	}
	
	public static void show(int pidx){
		System.out.println("Prof_Name:" + CourseManagementSystem.p.get(pidx).name + " ");
		System.out.println("Prof_Area:" + CourseManagementSystem.p.get(pidx).area + " ");
		if(CourseManagementSystem.p.get(pidx).coursetaking.isEmpty()){
			System.out.print("Prof_Courses: No_courses_taken");
		}
		else{
			System.out.print("Prof_Courses:");
		for(int i=0;i<CourseManagementSystem.p.get(pidx).coursetaking.size();i++)
			System.out.print(CourseManagementSystem.p.get(pidx).coursetaking.get(i) + " ");	
		}
		System.out.println();
	}
}
