package controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import model.Course;
import util.Validation;

public class CourseList {

    private final MyLinkedList<Course> courseList = new MyLinkedList<>();

    public CourseList() {
    }

    public MyLinkedList<Course> getCourseList() {
        return courseList;
    }

    //1.1
    public void loadData() {
        try {
            FileReader fr = new FileReader("course.txt");
            BufferedReader br = new BufferedReader(fr);
            String line;
            while ((line = br.readLine()) != null) {
                if (line != null) {
                    String[] word = line.split("\\\\");
                    String ccode = word[0];
                    String scode = word[1];
                    String sname = word[2];
                    String semester = word[3];
                    String year = word[4];
                    int seats = Integer.parseInt(word[5]);
                    int registered = Integer.parseInt(word[6]);
                    double price = Double.parseDouble(word[7]);
                    if (searchByCcode(ccode) == null) {
                        Course newCourse = new Course(ccode, scode, sname, semester, year, seats, registered, price);
                        courseList.addLast(newCourse);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("File is empty");
        }
    }

    //1.2
    public Course inputCourse() {
        String ccode;
        while (true) {
            ccode = Validation.getValidString("Enter Course Code: ",
                    "The Format code is CCxxxx with x is number", "^CC\\d{4}$");
            if (searchByCcode(ccode) != null) {
                System.err.println("Course Code is already exist. Please input another one.");
            } else {
                break;
            }
        }
        String scode = Validation.getString("Enter subject code: ", "Wrong input!");
        String sname = Validation.getString("Enter subject name: ", "Wrong input!");
        String semester = Validation.getString("Enter semester: ", "Wrong input!");
        String year = Validation.getString("Enter year of course: ", "Wrong input!");
        int seats = Validation.getAnInteger("Enter Seats: ", "Wrong input!", 0, 30);
        double price = Validation.getPositiveDouble("Enter price of course: ", "Wrong input");
        return new Course(ccode, scode, sname, semester, year, seats, price);
    }

    //1.2   
    public void addLast() {
        courseList.addLast(inputCourse());
    }

    //1.3
    public void display() {
        if (courseList.isEmpty()) {
            System.err.println("Course list is empty");
        }
        Node<Course> cur = courseList.head;
        while (cur != null) {
            System.out.println("========================");
            cur.data.displayCourseInfo();
            System.out.println("========================");
            cur = cur.next;

        }
    }

    //1.4
    public void saveToFile() {
        try {
            FileReader fr = new FileReader("course.txt");
            BufferedReader br = new BufferedReader(fr);
            Node<Course> cur = courseList.head;
            while (cur != null) {
                String code = cur.data.getCcode();
                boolean isExist = false;
                String line;
                while ((line = br.readLine()) != null) {
                    if (line != null) {
                        String[] word = line.split("\\\\");
                        if (word[0].equalsIgnoreCase(code)) {
                            isExist = true;
                            break;
                        }
                    }
                }
                if (!isExist) {
                    FileWriter fw = new FileWriter("course.txt", true);
                    BufferedWriter bw = new BufferedWriter(fw);
                    String data = cur.data.toString();
                    bw.write(data);
                    bw.newLine();
                    bw.close();
                    fw.close();
                }
                cur = cur.next;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //1.5
    public Node<Course> searchByCcode(String ccode) {
        Node<Course> current = courseList.head;
        while (current != null) {
            if (current.data.getCcode().equalsIgnoreCase(ccode)) {
                return current;
            }
            current = current.next;
        }
        return null; // Not found
    }

    //1.6    
    public void deleteByCcode(String ccode) {
        Node<Course> result = searchByCcode(ccode);
        if (result != null) {
            courseList.delete(result);
        } else {
            System.err.println("Ccode is not Exist");
        }
    }

    //1.7
    public void sortByCcode() {
        for (Node<Course> i = courseList.head; i != courseList.tail; i = i.next) {
            Node<Course> pos = i;
            for (Node<Course> j = i.next; j != courseList.tail.next; j = j.next) {
                if (pos.data.getCcode().compareTo(j.data.getCcode()) > 0) {
                    pos = j;
                }
            }
            courseList.swap(i, pos);
        }
        display();
    }

    //1.8
    public void addFirst() {
        courseList.addFirst(inputCourse());
    }

    //1.9
    public void insertAfter() {
        int k = Validation.getAnInteger("Enter position to delete: ",
                "Invalid!", 0, courseList.size() - 1);
        courseList.insert(k + 1, inputCourse());
    }

    //1.10
    public Node<Course> deleteByPosition() {
        int k = Validation.getAnInteger("Enter position to delete: ",
                "Invalid!", 0, courseList.size() - 1);
        courseList.delete(k);
        return courseList.getByIndex(k);
    }

    //1.11
    public void searchByName() {
        String nameSearch = Validation.getString("Enter name searching: ", "Wrong input!");
        Node<Course> temp = courseList.head;
        boolean ok = true;
        while (temp != null) {
            if (temp.data.getSname().toUpperCase().contains(nameSearch.toUpperCase())) {
                temp.data.displayCourseInfo();
                ok = false;
            }
            temp = temp.next;
        }
        if (ok) {
            System.err.println("Not found!");
        }
    }
}
