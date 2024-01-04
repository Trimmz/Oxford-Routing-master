/*
 * AdminInterface Class
 * Provides an interactive console interface for managing campus navigation system data.
 * Allows administrators to manage paths, buildings, students, events, calculate routing,
 * and perform related operations using the DatabaseConnect class.
 */

import DatabaseConnect.DatabaseConnect;

import java.time.Year;
import java.util.Scanner;

public class AdminInterface {
    public AdminInterface()
    {
        Scanner input = new Scanner(System.in);
        System.out.println("[1] Manage Paths");
        System.out.println("[2] Manage Buildings");
        System.out.println("[3] Manage Students");
        System.out.println("[4] Manage Events");
        System.out.println("[5] Calculate Routing");
        System.out.println("[6] Exit");
        int choice = input.nextInt();

        switch(choice) {
            case 1:
                managePaths();
                break;
            case 2:
                manageBuildings();
                break;
            case 3:
                manageStudents();
                break;
            case 4:
                manageEvents();
                break;
            case 5:
                calculateRouting();
                break;
            case 6:
                break;
            default:
                //TODO add loop functionality maybe
        }
    }

    private void managePaths()
    {
        Scanner input = new Scanner(System.in);
        System.out.println("[1] Add Path");
        System.out.println("[2] Remove Path");
        int choice = input.nextInt();

        int startNode, endNode, distance;
        DatabaseConnect connection = new DatabaseConnect();
        switch(choice) {
            case 1:
                System.out.print("PlaceID 1: ");
                startNode = input.nextInt();
                System.out.print("PlaceID 2: ");
                endNode = input.nextInt();
                System.out.print("Distance: ");
                distance = input.nextInt();
                connection.insertEdge(startNode, endNode, distance);
                connection.close();
                break;
            case 2:
                    System.out.print("PlaceID 1: ");
                    startNode = input.nextInt();
                    System.out.print("PlaceID 2: ");
                    endNode = input.nextInt();
                    connection.removeEdge(startNode, endNode);
                    connection.removeEdge(endNode, startNode);
                    connection.close();
                    break;
            default:
                //TODO add loop functionality maybe or exit
        }

    }

    private void manageBuildings()
    {
        Scanner input = new Scanner(System.in);
        System.out.println("[1] Add Building");
        System.out.println("[2] Remove Building");
        System.out.println("[3] Get List Of Buildings");
        int choice = input.nextInt();
        input.nextLine();
        String buildingName, description;

        DatabaseConnect connection = new DatabaseConnect();
        //todo switch once routes are populated
        switch(choice) {
            case 1:
                System.out.print("Building Name: ");
                buildingName = input.nextLine();
                System.out.print("Description: ");
                description = input.nextLine();
                connection.addBuilding(buildingName, description);
                break;
            case 2:
                System.out.println("Remove the building by pressing its corresponding number");
                connection.getListOfBuildings();
                choice = input.nextInt();
                connection.removeBuilding(choice);
                break;
            case 3:
                connection.getListOfBuildings();
            default:
                //TODO add loop functionality maybe or exit
        }
    }

    public void manageStudents()
    {
        Scanner input = new Scanner(System.in);
        System.out.println("[1] Add Student");
        System.out.println("[2] Remove Student");
        System.out.println("[3] View List Of Students");
        int choice = input.nextInt();
        input.nextLine();
        String name;
        int homeID;
        String password;
        DatabaseConnect connection = new DatabaseConnect();
        switch(choice) {
            case 1:
                System.out.print("Name (No Spaces First Letters Must Be Capitals): "); //TODO SPECIFY FULL NAME NO SPACES AND CAPS FOR START
                name = input.nextLine();
                System.out.print("HomePlaceID: ");
                homeID = input.nextInt();
                input.nextLine();
                System.out.print("Password: ");
                password = input.nextLine();
                connection.addStudent(name, homeID, password);
                break;
            case 2:
                System.out.print("Name (No Spaces First Letter Must Be Capitals): ");
                name = input.nextLine();
                System.out.print("Password: ");
                password = input.nextLine();
                connection.removeStudent(name, password);
                break;
            case 3:
                connection.getListOfStudents();
            default:
                //TODO add loop functionality maybe or exit
        }
    }

    public void manageEvents()
    {
        Scanner input = new Scanner(System.in);
        System.out.println("[1] Add Event");
        System.out.println("[2] Remove Event");
        System.out.println("[3] Add Student To Event");
        System.out.println("[4] Remove Student From Event");
        System.out.println("[5] View All Events");
        System.out.println("[6] View All Events For A Student");
        int choice = input.nextInt();
        input.nextLine();
        String eventName;
        int startMonth, startDay, startHour, startMinute, endMonth, endDay, endHour, endMinute, studentID;
        String startTime, endTime;
        int placeID, eventID;
        DatabaseConnect connection = new DatabaseConnect();
        switch(choice){
            case 1:
                System.out.print("EventName: ");
                eventName = input.nextLine();
                System.out.print("PlaceID: ");
                placeID = input.nextInt();
                input.nextLine();
                System.out.println();
                System.out.println("Start Time");
                System.out.print("Month(MM): ");
                startMonth = input.nextInt();
                input.nextLine();
                System.out.print("Day(DD): ");
                startDay = input.nextInt();
                input.nextLine();
                System.out.print("Hour(HH): ");
                startHour = input.nextInt();
                input.nextLine();
                System.out.print("Minute(MM): ");
                startMinute = input.nextInt();
                input.nextLine();
                System.out.println();
                System.out.println("EndTime");
                System.out.print("Month(MM): ");
                endMonth = input.nextInt();
                input.nextLine();
                System.out.print("Day(DD): ");
                endDay = input.nextInt();
                input.nextLine();
                System.out.print("Hour(HH): ");
                endHour = input.nextInt();
                input.nextLine();
                System.out.print("Minute(MM): ");
                endMinute = input.nextInt();
                input.nextLine();
                startTime = Year.now().getValue() + "-" + startMonth + "-" + startDay + " " + startHour + ":" + startMinute + ":00";//TODO maybe don't hardcode to current year
                endTime = Year.now().getValue() + "-" + endMonth + "-" + endDay + " " + endHour + ":" + endMinute + ":00";
                connection.addEvent(startTime, endTime, placeID, eventName);
                break;
            case 2:
                System.out.println("Remove the event by pressing its corresponding number");
                connection.getListOfEvents();
                System.out.print("Event: ");
                eventID = input.nextInt();
                input.nextLine();
                connection.removeEvent(eventID);
                break;
            case 3:
                System.out.println("Pick the student by pressing their corresponding number");
                connection.getListOfStudents();
                System.out.print("Student: ");
                studentID = input.nextInt();
                System.out.println("Pick the event by pressing its corresponding number");
                connection.getListOfEvents();
                System.out.print("Event: ");
                eventID = input.nextInt();
                input.nextLine();
                connection.addStudentToEvent(studentID, eventID);
                break;
            case 4:
                System.out.println("Pick the student by pressing its corresponding number");
                connection.getListOfStudents();
                System.out.print("Student: ");
                studentID = input.nextInt();
                System.out.println("Pick the event by pressing its corresponding number");
                connection.getListOfEvents();
                System.out.print("Event: ");
                eventID = input.nextInt();
                input.nextLine();
                connection.removeStudentFromEvent(studentID, eventID);
                break;
            case 5:
                connection.getListOfEvents();
                break;
            case 6:
                System.out.println("Pick the student by pressing their corresponding number");
                connection.getListOfStudents();
                System.out.print("Student: ");
                studentID = input.nextInt();
                connection.viewStudentTimetable(studentID);
                break;
        }
    }



    public void calculateRouting()
    {
        Scanner input = new Scanner(System.in);
        System.out.print("Choose a busyness factor that will affect how busy a path is after a person is routed through it (Enter an integer greater than 0. The default is 1): ");
        int busynessFactor = input.nextInt();
        System.out.println("Calculating routing...");
        DatabaseConnect connection = new DatabaseConnect();
        //todo should remove student event links (then remove events) when it is past the date
        connection.resetBusyness();
        connection.deleteRoutes();
        connection.calculateRouting(busynessFactor);
    }
}