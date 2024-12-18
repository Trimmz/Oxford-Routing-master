/*
 * StudentInterface Class
 * Represents the interface for students in the campus navigation system.
 * Provides options to view personal timetable, view the shortest routes, join events, leave events, or exit.
 * Uses the DatabaseConnect class to interact with the database for relevant operations.
 */

import DatabaseConnect.DatabaseConnect;

import java.util.Scanner;

public class StudentInterface {
    int studentID;

    public StudentInterface(String name)
    {
        Scanner input = new Scanner(System.in);
        System.out.println("[1] View Personal timetable");
        System.out.println("[2] View Output of Shortest Routes");
        System.out.println("[3] Join Event");
        System.out.println("[4] Leave Event");
        System.out.println("[5] Exit");

        DatabaseConnect conn = new DatabaseConnect();
        studentID = conn.getStudentID(name);
        conn.close();

        int choice = input.nextInt();

        switch(choice) {
            case 1:
                viewTimetable();
                break;
            case 2:
                viewShortestRoutes();
                break;
            case 3:
                joinEvent();
                break;
            case 4:
                leaveEvent();
                break;
            case 5:
                break;
        }
    }

    private void joinEvent() {
        DatabaseConnect conn = new DatabaseConnect();
        Scanner input = new Scanner(System.in);
        System.out.println("Here is a list of the existing events, press the corresponding key to add it to your timetable for the next day: ");
        conn.getListOfEvents();
        System.out.print("Event: ");
        try {
            int event = input.nextInt();
            conn.addStudentToEvent(studentID, event);
        }catch(Exception e)
        {
            System.out.println("Please enter a valid integer");
        }
    }

    private void viewShortestRoutes() {
        DatabaseConnect conn = new DatabaseConnect();
        conn.viewShortestRoutes(studentID);
        
    }

    private void viewTimetable() {
        DatabaseConnect conn = new DatabaseConnect();
        conn.viewStudentTimetable(studentID);
    }

    private void leaveEvent()
    {
        DatabaseConnect conn = new DatabaseConnect();
        Scanner input = new Scanner(System.in);
        System.out.println("Here is a list of the existing events, press the corresponding key to remove it from your timetable for the next day: ");
        conn.getListOfEvents();
        int event = input.nextInt();
        conn.removeStudentFromEvent(studentID, event);
    }
}
