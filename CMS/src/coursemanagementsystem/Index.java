package coursemanagementsystem;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Index {

    static List<Prof> p = new ArrayList<>();
    static List<Course> c = new ArrayList<>();
    static List<Student> s = new ArrayList<>();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try {
            readFile();
        } catch (FileNotFoundException ex) {
            System.out.println("File not found");
        }
        System.out.println("Student:");
        displayS();
        System.out.println("Professor:");
        displayP();
        System.out.println("Course:");
        displayC();
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
                                    System.out.println(i + 1 + " : " + c.get(i).name);
                                }
                                System.out.println();
                                System.out.print("Please choose a course to enroll:");
                                int courseChoice = sc.nextInt();
                                if (courseChoice > c.size()) {
                                    System.out.println("Please choose the correct course number to enroll");
                                } else {                                    
                                    if (t.coursetaken.contains(c.get(courseChoice - 1).name)) {
                                        System.out.println("Course_Already_Enrolled");
                                    } else {
                                        t.enroll(c.get(courseChoice - 1).name, temp);
                                    }
                                }
                            }
                            case 2 -> {
                                //unenroll course
                                for (int i = 0; i < c.size(); i++) {
                                    System.out.println(i + 1 + " : " + c.get(i).name);
                                }
                                System.out.println();
                                System.out.print("Please choose a course to enroll:");
                                int courseChoice = sc.nextInt();
                                if (courseChoice > c.size()) {
                                    System.out.println("Please choose the correct course number to enroll");
                                } else {
                                    if (t.coursetaken.contains(c.get(courseChoice - 1).name)) {
                                        t.unenroll(c.get(courseChoice - 1).name, temp);
                                    } else {
                                        System.out.println("You did not enroll to this course before");
                                    }
                                }
                            }
                            case 3 -> {
                                //generate timetable
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
                                sc.nextLine();
                                System.out.print("Enter professor name: ");
                                String prof = sc.nextLine();
                                if (ccheck(c.get(courseAssign-1).name)) {
                                    if (pcheck(prof, c.get(courseAssign-1).name)) {
                                        Course.modify(c.get(courseAssign-1).name, prof);
                                        System.out.println("Modify_Success");
                                    } else {
                                        System.out.println("No_Such_Professor_to_Modify_in_course");
                                    }
                                } else {
                                System.out.println("No_Such_Course_to_Modify");
                                }
                                
                            }
                            case 2 -> {
                                //add course
                                sc.nextLine();
                                System.out.println("Please fill in the new course information: ");
                                System.out.print("Enter course name: ");
                                String course = sc.nextLine();
                                if (ccheck(course)) {
                                    System.out.println("Course_Already_Exists");
                                    continue;
                                }
                                System.out.print("Enter professor name: ");
                                String prof = sc.nextLine();
                                System.out.print("Enter maximum student can enroll: ");
                                int max = sc.nextInt();
                                System.out.print("Enter which year of student can enroll: ");
                                int year = sc.nextInt();
                                c.add(new Course(course, max, prof, year));                                
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
                                    System.out.println("Please choose the correct course number to enroll");
                                    continue;
                                } 
                                sc.nextLine();
                                System.out.print("Enter new course name: ");
                                String course = sc.nextLine();
                                if (ccheck(course)) {
                                    System.out.println("Course_Already_Exists");
                                    continue;
                                }
                                System.out.print("Enter new maximum student can enroll: ");
                                int max = sc.nextInt();
                                System.out.print("Enter which year of student can enroll: ");
                                int year = sc.nextInt();
                                c.get(courseEdit-1).editDetail(course,max,year);
                                System.out.println("Update Successful");
                                Course.show(course);
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
                                    System.out.println("Please choose the correct course number to enroll");
                                    continue;
                                } 
                                System.out.println("Are you confirm to delete this course: "+c.get(courseDelete-1).name);
                                System.out.print("Please enter Y/N: ");
                                String confirm = sc.nextLine();
                                if(confirm.equals("Y")){
                                    c.remove(courseDelete-1);
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
        System.out.println("Program Stop!\n");
        System.out.println("Student:");
        displayS();
        System.out.println("Professor:");
        displayP();
        System.out.println("Course:");
        displayC();
    }

    public static void readFile() throws FileNotFoundException {
        Scanner ip = new Scanner(new File("input.txt"));
        int x, y, yr;
        String a, b, n, d, input;
        while (ip.hasNext()) {
            input = ip.next();
            if (input.equalsIgnoreCase("ADDS")) {
                a = ip.next();
                x = ip.nextInt();
                y = ip.nextInt();
                s.add(new Student(a, x, y));
            } else if (input.equalsIgnoreCase("ADDP")) {
                a = ip.next();
                b = ip.next();
                p.add(new Prof(a, b));
            } else if (input.equalsIgnoreCase("ADDC")) {
                a = ip.next();
                x = ip.nextInt();
                b = ip.next();
                yr = ip.nextInt();
                if (ccheck(a)) {
                    System.out.println("Course_Already_Exists");
                } else {
                    if (pcheck(b, a)) {
                        c.add(new Course(a, x, b, yr));
                    } else {
                        System.out.println("No_such_Professor");
                    }
                }
            }
        }
        ip.close();
    }

    public static void displayS() {
        for (int i = 0; i < s.size(); i++) {
            Student.show(i);
            System.out.println();
        }
    }

    public static void displayP() {
        for (int i = 0; i < p.size(); i++) {
            Prof.show(i);
            System.out.println();
        }
    }

    public static void displayC() {
        for (int i = 0; i < c.size(); i++) {
            Course.show(c.get(i).name);
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

    public static boolean pcheck(String name, String course) {
        for (int i = 0; i < p.size(); i++) {
            if (p.get(i).name.equals(name)) {
                p.get(i).coursetaking.add(course);
                return true;
            }
        }
        return false;
    }

    public static int pcheck2(String name) {
        for (int i = 0; i < p.size(); i++) {
            if (p.get(i).name.equals(name)) {
                return i;
            }
        }
        return 0;
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
