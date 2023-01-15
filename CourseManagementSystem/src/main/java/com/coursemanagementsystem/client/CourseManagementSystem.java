package com.coursemanagementsystem.client;

import com.coursemanagementsystem.client.beans.Course;
import com.coursemanagementsystem.client.beans.Prof;
import com.coursemanagementsystem.client.beans.Student;
import com.coursemanagementsystem.client.beans.Timetable;
import com.coursemanagementsystem.client.db.DBConnection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {"com.coursemanagementsystem.client.beans"})
@SpringBootApplication

public class CourseManagementSystem {

    public static List<Prof> p = new ArrayList<>();
    public static List<Course> c = new ArrayList<>();
    public static List<Student> s = new ArrayList<>();

    private static Course course;
    private static Student student;
    private static Prof prof;
    private static Timetable timetable;
    private static DBConnection db = new DBConnection();

    public static void main(String[] args) throws SQLException {
        ApplicationContext context = SpringApplication.run(CourseManagementSystem.class, args);
        course = context.getBean(Course.class);
        student = context.getBean(Student.class);
        prof = context.getBean(Prof.class);
        timetable = context.getBean(Timetable.class);

        readDB();

        start();
        System.out.println("Program Stop!\n");
        System.out.println("Student:");
        displayS();
        System.out.println("Professor:");
        displayP();
        System.out.println("Course:");
        displayC();
    }

    public static void readDB() throws SQLException {
        ResultSet rs;
        //get prof
        rs = db.retrieve("SELECT * FROM prof");
        while (rs.next()) {
            p.add(new Prof(rs.getString("name"), rs.getString("area")));
        }
        //get course
        rs = db.retrieve("SELECT * FROM course");
        while (rs.next()) {
            c.add(new Course(rs.getString("name"), rs.getInt("max"), rs.getString("prof"), rs.getInt("year"), rs.getInt("start_time"), rs.getInt("duration"), rs.getString("day")));
            prof.assignCourse(rs.getString("name"), rs.getString("prof"), "");
        }
        //get student
        rs = db.retrieve("SELECT * FROM student");
        while (rs.next()) {
            s.add(new Student(rs.getString("name"), rs.getInt("rollno"), rs.getInt("year")));
        }
    }

    public static void start() throws SQLException {
        Scanner sc = new Scanner(System.in);
        userRole:
        for (;;) {
            System.out.print("Choose 1 for Student, Choose 2 for Faculty Admin, Choose 3 to exit: ");
            int role = sc.nextInt();
            sc.nextLine();
            switch (role) {
                case 1 -> {
                    System.out.println("Enter your name: ");
                    String name = sc.nextLine();
                    int temp = scheck(name);
                    if (s.isEmpty() || temp == s.size()) {
                        System.out.println("No such student. Please enter correct name.");
                        continue;
                    }
                    Student t = s.get(temp);
                    for (;;) {
                        System.out.print("Choose 1 for enroll course, Choose 2 for unenroll course, Choose 3 to generate timetable, Choose 4 to exit the program: ");
                        int choice = sc.nextInt();
                        switch (choice) {
                            case 1 -> {
                                //enroll course
                                for (int i = 0; i < c.size(); i++) {
                                    System.out.println(i + 1 + " : " + c.get(i).name + " " + c.get(i).day + " (" + c.get(i).startTime + " - " + (c.get(i).startTime + c.get(i).duration) + ")");
                                }
                                System.out.println();
                                System.out.print("Please choose a course to enroll:");
                                int courseChoice = sc.nextInt();
                                if (courseChoice > c.size()) {
                                    System.out.println("Please choose the correct course number to enroll");
                                } else {
                                    if (t.coursetaken.contains(c.get(courseChoice - 1))) {
                                        System.out.println("Course_Already_Enrolled");
                                    } else {
                                        t.enroll(c.get(courseChoice - 1).name, temp);
                                    }
                                }
                            }
                            case 2 -> {
                                //unenroll course
                                for (int i = 0; i < c.size(); i++) {
                                    System.out.println(i + 1 + " : " + c.get(i).name + " " + c.get(i).day + " (" + c.get(i).startTime + " - " + (c.get(i).startTime + c.get(i).duration) + ")");
                                }
                                System.out.println();
                                System.out.print("Please choose a course to unenroll:");
                                int courseChoice = sc.nextInt();
                                if (courseChoice > c.size()) {
                                    System.out.println("Please choose the correct course number to unenroll");
                                } else {
                                    if (t.coursetaken.contains(c.get(courseChoice - 1))) {
                                        t.unenroll(c.get(courseChoice - 1).name, temp);
                                    } else {
                                        System.out.println("You did not enroll to this course before");
                                    }
                                }
                            }
                            case 3 -> {
                                //generate timetable
                                timetable.setStudent(t);
                                timetable.generateIText();
                            }
                            case 4 -> {
                                //exit program
                                break userRole;
                            }
                            default ->
                                System.out.println("Invalid Choice. Try Again.");
                        }
                    }
                }
                case 2 -> {
                    for (;;) {
                        System.out.print("Choose 1 for assign lecturers, Choose 2 for add course, Choose 3 to edit course, Choose 4 to delete course, Choose 5 to exit the program: ");
                        int choice = sc.nextInt();
                        switch (choice) {
                            case 1 -> {
                                //assign lecturers
                                for (int i = 0; i < c.size(); i++) {
                                    System.out.println(i + 1 + " : " + c.get(i).name);
                                }
                                System.out.println();
                                System.out.print("Please choose a course to assign:");
                                int courseAssign = sc.nextInt();
                                for (int i = 0; i < p.size(); i++) {
                                    System.out.println(i + 1 + " : " + p.get(i).name);
                                }
                                System.out.print("Please choose a professor to assign:");
                                int profChoice = sc.nextInt();
                                if (profChoice > p.size()) {
                                    System.out.println("Please choose the correct professor number to assign");
                                } else {
                                    if (prof.assignCourse(c.get(courseAssign - 1).name, p.get(profChoice - 1).name, c.get(courseAssign - 1).profes)) {
                                        course.modify(c.get(courseAssign - 1).name, p.get(profChoice - 1).name);
                                        System.out.println("Assign Professor Success");
                                    } else {
                                        System.out.println("No_Such_Professor_to_Modify_in_course");
                                    }
                                }

                            }
                            case 2 -> {
                                //add course
                                sc.nextLine();
                                System.out.println("Please fill in the new course information: ");
                                System.out.print("Enter course name: ");
                                String courseName = sc.nextLine();
                                if (ccheck(courseName)) {
                                    System.out.println("Course_Already_Exists");
                                    continue;
                                }
                                System.out.print("Enter professor name: ");
                                String profName = sc.nextLine();
                                if(pcheck2(profName)==-1){                                    
                                    System.out.println("There is no this prof in the system");                                
                                }
                                System.out.print("Enter maximum student can enroll: ");
                                int max = sc.nextInt();
                                System.out.print("Enter which year of student can enroll: ");
                                int year = sc.nextInt();
                                System.out.print("Enter what is the start time: ");
                                int startTime = sc.nextInt();
                                System.out.print("Enter what is the duration: ");
                                int duration = sc.nextInt();
                                sc.nextLine();
                                System.out.print("Enter what day of the class: ");
                                String day = sc.nextLine();
                                c.add(new Course(courseName, max, profName, year, startTime, duration, day));
                                prof.assignCourse(courseName, profName, "");
                                db.insert("INSERT INTO course(name,prof,max,year,start_time,duration,day) VALUES('" + courseName + "','" + profName + "'," + max + "," + year + "," + startTime + "," + duration + ",'" + day + "')");
                                
                                    
                                
                            }
                            case 3 -> {
                                //edit course                               
                                for (int i = 0; i < c.size(); i++) {
                                    System.out.println(i + 1 + " : " + c.get(i).name);
                                }
                                System.out.println();
                                System.out.print("Please choose a course to edit:");
                                int courseEdit = sc.nextInt();
                                if (courseEdit > c.size()) {
                                    System.out.println("Please choose the correct course number to edit");
                                    continue;
                                }
                                sc.nextLine();
                                System.out.print("Enter new course name: ");
                                String courseName = sc.nextLine();
                                if (ccheck(courseName)) {
                                    System.out.println("Course_Already_Exists");
                                    continue;
                                }
                                System.out.print("Enter new maximum student can enroll: ");
                                int max = sc.nextInt();
                                System.out.print("Enter which year of student can enroll: ");
                                int year = sc.nextInt();
                                c.get(courseEdit - 1).editDetail(courseName, max, year);
                                System.out.println("Update Successful");
                                course.show(courseName);
                                System.out.println("");
                            }
                            case 4 -> {
                                //delete course
                                for (int i = 0; i < c.size(); i++) {
                                    System.out.println(i + 1 + " : " + c.get(i).name);
                                }
                                System.out.println();
                                System.out.print("Please choose a course to delete:");
                                int courseDelete = sc.nextInt();
                                sc.nextLine();
                                if (courseDelete > c.size()) {
                                    System.out.println("Please choose the correct course number to delete");
                                    continue;
                                }
                                System.out.println("Are you confirm to delete this course: " + c.get(courseDelete - 1).name);
                                System.out.print("Please enter Y/N: ");
                                String confirm = sc.nextLine();
                                if (confirm.equals("Y")) {
                                    db.remove("DELETE FROM course where name='" + c.get(courseDelete - 1).name + "'");
                                    c.remove(courseDelete - 1);
                                    System.out.println("This course has been deleted.");
                                }

                            }
                            case 5 -> {
                                break userRole;
                            }
                            default ->
                                System.out.println("Invalid Choice. Try Again.");
                        }
                    }
                }
                case 3 -> {
                    System.exit(0);
                }
                default ->
                    System.out.println("Invalid Choice. Try Again.");
            }
        }
    }

    public static void displayS() {
        for (int i = 0; i < s.size(); i++) {
            student.show(i);
            System.out.println();
        }
    }

    public static void displayP() {
        for (int i = 0; i < p.size(); i++) {
            prof.show(i);
            System.out.println();
        }
    }

    public static void displayC() {
        for (int i = 0; i < c.size(); i++) {
            course.show(c.get(i).name);
            System.out.println();
        }
    }

    public static int scheck(String name) {
        for (int i = 0; i < s.size(); i++) {
            if (s.get(i).name.equals(name)) {
                return i;
            }
        }
        return 0;
    }

    public static int pcheck2(String name) {
        for (int i = 0; i < p.size(); i++) {
            if (p.get(i).name.equals(name)) {
                return i;
            }
        }
        return -1;
    }

    public static boolean ccheck(String name) {
        for (int i = 0; i < c.size(); i++) {
            if (c.get(i).name.equals(name)) {
                return true;
            }
        }
        return false;
    }

}
