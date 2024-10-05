package controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import model.Course;
import model.Register;
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
        try (BufferedReader br = new BufferedReader(new FileReader("courses.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.trim().isEmpty()) { // Check for non-empty lines
                    String[] word = line.split("\\\\"); // Ensure this delimiter is correct
                    if (word.length < 8) {
                        System.err.println("Invalid data format: " + line);
                        continue; // Skip invalid lines
                    }
                    String ccode = word[0].trim();
                    String scode = word[1].trim();
                    String sname = word[2].trim();
                    String semester = word[3].trim();
                    String year = word[4].trim();
                    int seats;
                    int registered;
                    double price;
                    try {
                        seats = Integer.parseInt(word[5].trim());
                        registered = Integer.parseInt(word[6].trim());
                        price = Double.parseDouble(word[7].trim());
                    } catch (NumberFormatException e) {
                        System.err.println("Number format error in line: " + line);
                        continue; // Skip lines with invalid number formats
                    }

                    if (searchByCcode(ccode) == null) {
                        Course newCourse = new Course(ccode, scode, sname, semester, year, seats, registered, price);
                        courseList.addLast(newCourse);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading courses.txt: " + e.getMessage());
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
        // First, collect all existing course codes to avoid duplicates
        Set<String> existingCcodes = new HashSet<>();
        try (BufferedReader br = new BufferedReader(new FileReader("courses.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    String[] word = line.split("\\\\");
                    if (word.length > 0) {
                        existingCcodes.add(word[0].trim().toUpperCase());
                    }
                }
            }
        } catch (IOException e) {
            // If file doesn't exist, we'll create it later
            System.out.println("courses.txt not found. A new file will be created.");
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter("courses.txt", true))) {
            Node<Course> cur = courseList.head;
            while (cur != null) {
                String code = cur.data.getCcode().toUpperCase();
                if (!existingCcodes.contains(code)) {
                    bw.write(cur.data.toString());
                    bw.newLine();
                    existingCcodes.add(code); // Add to set to prevent future duplicates
                }
                cur = cur.next;
            }
            System.out.println("Courses saved successfully.");
        } catch (IOException e) {
            System.err.println("Error writing to courses.txt: " + e.getMessage());
        }
    }

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

    //1.5
    public void searchCourseByCode() {
        String ccode = Validation.getValidString("Enter Course Code (Format CCxxxx): ",
                "The format code is CCxxxx with x being numbers.", "^CC\\d{4}$");
        Node<Course> course = searchByCcode(ccode);
        if (course != null) {
            System.out.println("========================");
            course.data.displayCourseInfo();
            System.out.println("========================");
        } else {
            System.err.println("Course with code " + ccode + " NOT FOUND!");
        }
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

    //1.12
    public void searchCourseByCcode(RegisterList registerList, StudentList studentList) {
        String ccode = Validation.getValidString("Enter Course Code (Format CCxxxx): ",
                "The format code is CCxxxx with x being numbers.", "^CC\\d{4}$");
        Node<Course> course = searchByCcode(ccode);
        if (course != null) {
            System.out.println("========================");
            course.data.displayCourseInfo();
            System.out.println("========================");
            String scode = registerList.findScodeByCcode(ccode);
            studentList.searchByScode(scode).data.displayStudentInfo();
            System.out.println("========================");
        } else {
            System.err.println("Course with code " + ccode + " NOT FOUND!");
        }

    }

}
