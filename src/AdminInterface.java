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
                System.out.println("Select The IDs From This List Of The Current Places On The Map");
                connection.getListOfBuildings();
                System.out.println("");
                System.out.print("PlaceID 1: ");
                startNode = input.nextInt();
                System.out.print("PlaceID 2: ");
                endNode = input.nextInt();
                System.out.print("Distance: ");

                try{
                    distance = input.nextInt();
                    connection.insertEdge(startNode, endNode, distance);
                    connection.close();
                }catch (Exception e)
                {
                    System.out.println("The input for distance must be an integer");
                }
                break;
            case 2:
                System.out.println("Select The IDs From This List Of The Current Places On The Map");
                connection.getListOfBuildings();
                System.out.println("");
                System.out.print("PlaceID 1: ");
                startNode = input.nextInt();
                System.out.print("PlaceID 2: ");
                endNode = input.nextInt();
                if(!connection.removeEdge(startNode, endNode))
                {
                    connection.removeEdge(endNode, startNode);
                }
                connection.close();
                break;
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
                System.out.print("Username: ");
                name = input.nextLine();
                System.out.println("Select the ID of the building that the student resides in");
                connection.getListOfBuildings();
                System.out.print("HomePlaceID: ");
                homeID = input.nextInt();
                if(!connection.doesBuildingIDExist(homeID))
                {
                    System.out.println("The Building ID You Entered Did Not Exist");
                    break;
                }
                input.nextLine();
                System.out.print("Password: ");
                password = input.nextLine();
                connection.addStudent(name, homeID, password);
                break;
            case 2:
                System.out.println("Enter the username of the student profile you would like to remove");
                connection.getListOfStudents();
                System.out.print("Username: ");
                name = input.nextLine();
                System.out.print("Password: ");
                password = input.nextLine();
                connection.removeStudent(name, password);
                break;
            case 3:
                connection.getListOfStudents();
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
        String startMonth, startDay, startHour, startMinute, endMonth, endDay, endHour, endMinute;
        String startTime, endTime;
        int placeID, eventID, studentID;
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
                startMonth = input.nextLine();
                System.out.print("Day(DD): ");
                startDay = input.nextLine();
                System.out.print("Hour(HH): ");
                startHour = input.nextLine();
                System.out.print("Minute(MM): ");
                startMinute = input.nextLine();
                System.out.println();
                System.out.println("EndTime");
                endMonth = startMonth;
                endDay = startDay;
                System.out.print("Hour(HH): ");
                endHour = input.nextLine();
                System.out.print("Minute(MM): ");
                endMinute = input.nextLine();
                startTime = Year.now().getValue() + "-" + startMonth + "-" + startDay + " " + startHour + ":" + startMinute + ":00";
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
        if(busynessFactor>0)
        {
            System.out.println("Calculating routing...");
            DatabaseConnect connection = new DatabaseConnect();
            connection.resetBusyness();
            connection.deleteRoutes();
            connection.calculateRouting(busynessFactor);
        }
        else{
            System.out.println("The busyness factor must be larger than 0");
        }
    }
}