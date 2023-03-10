/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.coursemanagementsystem.client.beans;


import com.coursemanagementsystem.client.CourseManagementSystem;
import com.coursemanagementsystem.client.db.DBConnection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;


@Component
public class Student {
	public String name;
	public int rollno;
	public int year;
	public List<Course> coursetaken = new ArrayList<>();
        private static DBConnection db = new DBConnection();
        public Student(){}
        
	public Student(String name,int roll,int yr) throws SQLException{
		this.name = name;
		this.rollno = roll;
		this.year = yr;
	}
	public void enroll(String a,int myidx) throws SQLException{		
		int cidx=0;
		for(int i=0;i<CourseManagementSystem.c.size();i++){
			if(CourseManagementSystem.c.get(i).name.equals(a)){
				cidx=i;
				break;
			}
		}
                boolean crash =false;
                if(!CourseManagementSystem.s.get(myidx).coursetaken.isEmpty()){
                    for (int i = 0; i < CourseManagementSystem.s.get(myidx).coursetaken.size(); i++) {
                        Course tempC = CourseManagementSystem.s.get(myidx).coursetaken.get(i);
                        Course current = CourseManagementSystem.c.get(cidx);
                        int tempEndTime = tempC.startTime+tempC.duration;
                        int currEndTime = current.startTime+current.duration;
                        if(current.day.equals(tempC.day) && ((current.startTime>tempC.startTime && current.startTime<tempEndTime) || (currEndTime>tempC.startTime && currEndTime<tempEndTime) )){                            
                            crash = true;
                            System.out.println("Clash time with course "+tempC.name);
                            break;
                        }
                        
                    }
                }                                
                if((CourseManagementSystem.c.get(cidx).yr<=CourseManagementSystem.s.get(myidx).year)&&CourseManagementSystem.c.get(cidx).max>0 && !crash){			
			CourseManagementSystem.s.get(myidx).coursetaken.add(CourseManagementSystem.c.get(cidx));
			CourseManagementSystem.c.get(cidx).studenrolled.add(CourseManagementSystem.s.get(myidx).name);
			CourseManagementSystem.c.get(cidx).max--;
                        String studentName = CourseManagementSystem.s.get(myidx).name;                        
                        db.insert("insert into student_course(student_id,course_id) SELECT student.id,course.id from student,course WHERE student.name='"+studentName+"' AND course.name='"+a+"';");
			System.out.println("Enrollment_Success");
		}
		else{
			System.out.println("Not_eligble_to_enroll_course");
		}
	}
	
	public void unenroll(String a,int myidx) throws SQLException{
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
			CourseManagementSystem.s.get(myidx).coursetaken.remove(CourseManagementSystem.c.get(cidx));
			CourseManagementSystem.c.get(cidx).studenrolled.remove(CourseManagementSystem.s.get(myidx).name);
			CourseManagementSystem.c.get(cidx).max++;
                        String studentName = CourseManagementSystem.s.get(myidx).name;
                        ResultSet rs = db.retrieve("SELECT student.id as S,course.id as C from student,course where student.name='"+studentName+"' AND course.name='"+a+"';");
                        while (rs.next()) {
                            db.remove("delete from student_course where student_id="+rs.getInt("S")+" and course_id="+rs.getInt("C")+"; ");
                            
                        }
			System.out.println("Unenroll_Success");
		}
	}
	
	public void show(int myidx){
		System.out.println("Student_Name:" + CourseManagementSystem.s.get(myidx).name + " ");
//		System.out.println("Student_Rollno:" + CourseManagementSystem.s.get(myidx).rollno + " ");
		System.out.println("Student_Year:" + CourseManagementSystem.s.get(myidx).year + " ");
		if(CourseManagementSystem.s.get(myidx).coursetaken.isEmpty()){
			System.out.println("Student_courses: No_courses_enrolled");
		}
		else{
			System.out.print("Student_courses:");
			for(int i=0;i<CourseManagementSystem.s.get(myidx).coursetaken.size();i++){
				System.out.print(CourseManagementSystem.s.get(myidx).coursetaken.get(i).name + " ");
			}
			System.out.println();
		}
	}
}
