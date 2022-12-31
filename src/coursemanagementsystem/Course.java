package coursemanagementsystem;

import java.util.ArrayList;
import java.util.List;

public class Course {

    String name;
    String profes;
    int max;
    int yr;

    List<String> studenrolled = new ArrayList<String>();

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
        for (int i = 0; i < Index.c.size(); i++) {
            if (Index.c.get(i).name.equals(a)) {
                cidx = i;
                break;
            }
        }
        Index.c.get(cidx).profes = newprof;
    }

    public static void show(String a) {
        int cidx = 0;
        for (int i = 0; i < Index.c.size(); i++) {
            if (Index.c.get(i).name.equals(a)) {
                cidx = i;
                break;
            }
        }
        System.out.println("Course_Name:" + Index.c.get(cidx).name + " ");
        System.out.println("Course_prof:" + Index.c.get(cidx).profes + " ");
        System.out.println("Course_max:" + Index.c.get(cidx).max + " ");
        System.out.println("Course_pre_year:" + Index.c.get(cidx).yr + " ");
        if (Index.c.get(cidx).studenrolled.isEmpty()) {
            System.out.println("Course_students: No_Students_in_this_course");
        } else {
            System.out.print("Course_students:");
            for (int i = 0; i < Index.c.get(cidx).studenrolled.size(); i++) {
                System.out.print(Index.c.get(cidx).studenrolled.get(i) + " ");
            }
            System.out.println();
        }
    }
}
