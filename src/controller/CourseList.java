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

    //1.1
    public void loadData() {
        try {
            FileReader fr = new FileReader("courses.txt");
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
            System.out.println("Load data successfully.");
        } catch (IOException e) {
            System.err.println("File is empty");
        }
    }

    private Course inputCourse() {
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
        System.out.println("Added course successfully!");
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
            return;
        }
        Node<Course> cur = courseList.head;
        System.out.printf("%-15s %-15s %-15s %-10s %-10s %-10s %-12s %-10s\n",
                "Course Code", "Subject Code", "Subject Name", "Semester", "Year", "Seats", "Registered", "Price");
        System.out.println("----------------------------------------------------------------------------------------------------------------------");
        while (cur != null) {
            cur.data.displayCourseInfo();
            cur = cur.next;

        }
    }

    //1.4
    public void saveToFile(boolean isLoadData) {
        if (courseList.isEmpty()) {
            // vi list trong ma save vao file thi la clear file =))
            System.err.println("List is empty, can not save file");
            return;
        }
        if (!isLoadData) {
            // chua load ma save thi no xoa cai data cu di va thay vao cai data moi dang co trong list
            System.err.println("Please load data before save to file");
            return;
        }
        try {
            FileWriter fw = new FileWriter("courses.txt");
            BufferedWriter bw = new BufferedWriter(fw);
            Node<Course> cur = courseList.head;
            while (cur != null) {
                String data = cur.data.toString();
                bw.write(data);
                bw.newLine();
                cur = cur.next;
            }
            System.out.println("Save data to file Successfully.");
            bw.close();
            fw.close();
        } catch (Exception e) {
            System.err.println("ERROR to save file");
        }
    }

    public Node<Course> searchByCcode(String ccode) {
        if (courseList.isEmpty()) {
            return null;
        }
        Node<Course> current = courseList.head;
        while (current != null) {
            if (current.data.getCcode().equalsIgnoreCase(ccode)) {
                return current;
            }
            current = current.next;
        }
        return null; // Not found
    }

    //1.5
    public void searchCourseByCode() {
        String ccode = Validation.getValidString("Enter Course Code (Format CCxxxx): ",
                "The format code is CCxxxx with x being numbers.", "^CC\\d{4}$");
        Node<Course> course = searchByCcode(ccode);

        if (course != null) {
            System.out.println("\nHere is the Course that you want to search: ");
            System.out.printf("%-15s %-15s %-15s %-10s %-10s %-10s %-12s %-10s\n",
                    "Course Code", "Subject Code", "Subject Name", "Semester", "Year", "Seats", "Registered", "Price");
            System.out.println("----------------------------------------------------------------------------------------------------------------------");
            course.data.displayCourseInfo();
        } else {
            System.err.println("Course with code " + ccode + " NOT FOUND!");
        }
    }

    //1.6
    public void deleteByCcode(RegisterList registerList) {
        if (courseList.isEmpty()) {
            System.err.println("Course List is empty");
            return;
        }
        String ccodeDelete = Validation.getValidString("Enter Course Code: ",
                "The Format code is CCxxxx with x is number", "^CC\\d{4}$");
        Node<Course> result = searchByCcode(ccodeDelete);
        if (result != null) {
            registerList.deleteRegisterByCcode(ccodeDelete);
            courseList.delete(result);
            System.out.println("Deletion successfully!");
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
        System.out.println("\nHere is the Course List after sorting ascending: ");
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
    public void deleteByPosition(RegisterList registerList) {
        if (courseList.isEmpty()) {
            System.err.println("Course List is empty");
            return;
        }
        int k = Validation.getAnInteger("Enter position to delete: ",
                "Invalid!", 0, courseList.size() - 1);
        Node<Course> result = courseList.getByIndex(k);
        String ccode = result.data.getCcode();
        registerList.deleteRegisterByCcode(ccode);
        courseList.delete(k);
        System.out.println("Deletion successfully!");
    }

    //1.11
    public void searchByName() {
        String nameSearch = Validation.getString("Enter name searching: ", "Wrong input!");
        Node<Course> temp = courseList.head;
        boolean foundCourse = false;  // Track if a course is found

        while (temp != null) {
            if (temp.data.getSname().toUpperCase().contains(nameSearch.toUpperCase())) {
                if (!foundCourse) {
                    System.out.println("Here is the course that you want to search:");
                    System.out.printf("%-15s %-15s %-15s %-10s %-10s %-10s %-12s %-10s\n",
                            "Course Code", "Subject Code", "Subject Name", "Semester", "Year", "Seats", "Registered", "Price");
                    System.out.println("----------------------------------------------------------------------------------------------------------------------");
                    foundCourse = true;
                }
                temp.data.displayCourseInfo();  // Display the course info
            }
            temp = temp.next;
        }

        if (!foundCourse) {
            System.err.println("Not found!");
        }
    }

    //1.12
    public void searchCourseByCcode(RegisterList registerList, StudentList studentList) {
        String ccode = Validation.getValidString("Enter Course Code (Format CCxxxx): ",
                "The format code is CCxxxx with x being numbers.", "^CC\\d{4}$");
        Node<Course> course = searchByCcode(ccode);
        if (course != null) {
            System.out.println("\nHere is the course you want to search: ");
            System.out.printf("%-15s %-15s %-15s %-10s %-10s %-10s %-12s %-10s\n",
                    "Course Code", "Subject Code", "Subject Name", "Semester", "Year", "Seats", "Registered", "Price");
            System.out.println("----------------------------------------------------------------------------------------------------------------------");
            course.data.displayCourseInfo();
            System.out.println("\nHere are the students in this course: ");
            System.out.printf("%-15s %-25s %-10s\n",
                    "Student Code", "Student Name", "Birth Year");
            System.out.println("----------------------------------------------------");
            registerList.printOutStudentByCcode(ccode, studentList);
        } else {
            System.err.println("Course with code " + ccode + " NOT FOUND!");
        }
    }

}
