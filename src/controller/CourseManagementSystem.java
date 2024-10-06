package controller;

import ui.Menu;
import ui.MenuManagement;

public class CourseManagementSystem {

    MenuManagement mm = new MenuManagement();

    RegisterList registerList = new RegisterList();
    CourseList courseList = new CourseList();
    StudentList studentList = new StudentList();

    boolean isLoadCourseList = false;
    boolean isLoadStudentList = false;
    boolean isLoadRegisterList = false;

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
                    isLoadCourseList = true;
                    break;
                case 2:
                    courseList.addLast();
                    break;
                case 3:
                    courseList.display();
                    break;
                case 4:
                    courseList.saveToFile(isLoadCourseList);
                    break;
                case 5:
                    courseList.searchCourseByCode();
                    break;
                case 6:
                    courseList.deleteByCcode(registerList);
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
                    courseList.deleteByPosition(registerList);
                    break;
                case 11:
                    courseList.searchByName();
                    break;
                case 12:
                    courseList.searchCourseByCcode(registerList, studentList);
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
                    isLoadStudentList = true;
                    break;
                case 2:
                    studentList.addLast();
                    break;
                case 3:
                    studentList.display();
                    break;
                case 4:
                    studentList.saveToFile(isLoadStudentList);
                    break;
                case 5:
                    studentList.searchStudentByCode();
                    break;
                case 6:
                    studentList.deleteByScode(registerList);
                    break;
                case 7:
                    studentList.searchByName();
                    break;
                case 8:
                    studentList.searchCourseByCcode(registerList, courseList);
                    break;
                case 9:
                    return;
            }
        }
    }

    public void executeRegisteringList() {
        Menu menu = mm.menuRegisterList();
        while (true) {
            menu.printMenu();
            int choice = menu.getChoice();
            switch (choice) {
                case 1:
                    registerList.loadData();
                    isLoadRegisterList = true;
                    break;
                case 2:
                    registerList.registerCourse(courseList, studentList);
                    break;
                case 3:
                    registerList.display();
                    break;
                case 4:
                    registerList.saveToFile(isLoadRegisterList);
                    break;
                case 5:
                    registerList.sort();
                    break;
                case 6:
                    registerList.updateMark();
                    break;
                case 7:
                    return;
            }
        }
    }

}
