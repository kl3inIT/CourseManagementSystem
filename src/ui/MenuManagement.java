package ui;

/**
 *
 * @author nhatnhatnhat
 */
public class MenuManagement {

    public Menu menuSystem() {
        Menu menu = new Menu("============= Course Management System ==========");
        menu.addNewOption("1. Course List");
        menu.addNewOption("2. Student List");
        menu.addNewOption("3. Registering List");
        menu.addNewOption("4. Exit");
        return menu;
    }

    public Menu menuCourseList() {
        Menu menu = new Menu("============= Course List Menu ==========");
        menu.addNewOption("1. Load data from file");
        menu.addNewOption("2. Input & add to the end");
        menu.addNewOption("3. Display data");
        menu.addNewOption("4. Save course list to file");
        menu.addNewOption("5. Search by ccode");
        menu.addNewOption("6. Delete by ccode");
        menu.addNewOption("7. Sort by ccode");
        menu.addNewOption("8. Input & add to beginning");
        menu.addNewOption("9. Add after position k");
        menu.addNewOption("10. Delete position k");
        menu.addNewOption("11. Search by name");
        menu.addNewOption("12. Search booked by ccode");
        menu.addNewOption("13. Exit");
        return menu;
    }

    public Menu menuStudentList() {
        Menu menu = new Menu("============= Student List Menu ==========");
        menu.addNewOption("1. Load data from file");
        menu.addNewOption("2. Input & add to the end");
        menu.addNewOption("3. Display data");
        menu.addNewOption("4. Save student list to file");
        menu.addNewOption("5. Search by scode");
        menu.addNewOption("6. Delete by scode");
        menu.addNewOption("7. Search by student name");
        menu.addNewOption("8. Search registered courses by scode");
        menu.addNewOption("9. Exit");
        return menu;
    }

    public Menu menuRegisterList() {
        Menu menu = new Menu("============= Register List Menu ==========");
        menu.addNewOption("1. Load data from file");
        menu.addNewOption("2. Register the course");
        menu.addNewOption("3. Display data");
        menu.addNewOption("4. Save registering list to file");
        menu.addNewOption("5. Sort by scode and ccode");
        menu.addNewOption("6. Update mark by scode and ccode");
        menu.addNewOption("7. Exit");
        return menu;
    }

}
