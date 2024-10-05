package controller;

import model.Course;
import model.Student;
import ui.Menu;
import ui.MenuManagement;
import util.Validation;

public class CourseManagementSystem {

    MenuManagement mm = new MenuManagement();

    RegisterList registerList = new RegisterList();
    CourseList courseList = new CourseList();
    StudentList studentList = new StudentList();

    public void executeSystem() {
        Menu menu = mm.menuSystem();
        while (true) {
            menu.printMenu();
            int choice = menu.getChoice();
            switch (choice) {
                case 1:
                    executeCourseList();
                    break;
                case 2:
                    executeStudentList();
                    break;
                case 3:
                    executeRegisteringList();
                    break;
                case 4:
                    return;
            }
        }
    }

    public void executeCourseList() {
        Menu menu = mm.menuCourseList();
        while (true) {
            menu.printMenu();
            int choice = menu.getChoice();
            switch (choice) {
                case 1:
                    courseList.loadData();
                    break;
                case 2:
                    courseList.addLast();
                    break;
                case 3:
                    courseList.display();
                    break;
                case 4:
                    courseList.saveToFile();
                    break;
                case 5:
                    String ccode = Validation.getValidString("Enter Course Code: ",
                            "The Format code is CCxxxx with x is number", "^CC\\d{4}$");
                    Node<Course> courseSearch = courseList.searchByCcode(ccode);
                    if (courseSearch != null) {
                        courseSearch.data.displayCourseInfo();
                    } else {
                        System.err.println("NOT FOUND!");
                    }
                    break;
                case 6:
                    String ccodeDelete = Validation.getValidString("Enter Course Code: ",
                            "The Format code is CCxxxx with x is number", "^CC\\d{4}$");
                    registerList.deleteRegisterByCcode(ccodeDelete);
                    courseList.deleteByCcode(ccodeDelete);
                    break;
                case 7:
                    courseList.sortByCcode();
                    break;
                case 8:
                    courseList.addFirst();
                    break;
                case 9:
                    courseList.insertAfter();
                    break;
                case 10:
                    String coureDelete = courseList.deleteByPosition().data.getCcode();
                    registerList.deleteRegisterByCcode(coureDelete);
                    break;
                case 11:               
                    courseList.searchByName();
                    break;
                case 12:
                    break;
                case 13:
                    return;
            }
        }
    }

    public void executeStudentList() {
        Menu menu = mm.menuStudentList();
        while (true) {
            menu.printMenu();
            int choice = menu.getChoice();
            switch (choice) {
                case 1:
                    studentList.loadData();
                    break;
                case 2:
                    studentList.addLast();
                    break;
                case 3:
                    studentList.display();
                    break;
                case 4:
                    studentList.saveToFile();
                    break;
                case 5:
                    String scode = Validation.getValidString("Input student ID(HAxxxxxx, HExxxxxx, HSxxxxxx): ",
                            "The format of id is HAXXXXXX, HEXXXXXX, HSXXXXXX", "H[ASE]\\d{6}");
                    Node<Student> studentSearch = studentList.searchByScode(scode);
                    
                    if (studentSearch != null) {
                        studentSearch.data.displayStudentInfo();
                    } else {
                        System.err.println("NOT FOUND!");
                    }
                    break;
                case 6:
                    String scodeDelete = Validation.getValidString("Input student ID(HAxxxxxx, HExxxxxx, HSxxxxxx): ",
                    "The format of id is HAXXXXXX, HEXXXXXX, HSXXXXXX", "H[ASE]\\d{6}");
                    registerList.deleteRegisterByScode(scodeDelete);
                    studentList.deleteByScode(scodeDelete);
                    break;
                case 7:
                    studentList.searchByName();
                    break;
                case 8:
                    return;
            }
        }
    }

    public void executeRegisteringList() {
        Menu menu = mm.menuCourseList();
        while (true) {
            menu.printMenu();
            int choice = menu.getChoice();
            switch (choice) {
                case 1:
                    registerList.loadData();
                    break;
                case 2:
                    registerList.registerCourse(courseList, studentList);
                    break;
                case 3:
                    registerList.display();
                    break;
                case 4:
                    registerList.saveToFile();
                    break;
                case 5:
                    registerList.sort();
                    break;
                case 6:
                    registerList.updateMark(courseList, studentList);
                    break;
                case 7:
                    return;
            }
        }
    }

}
