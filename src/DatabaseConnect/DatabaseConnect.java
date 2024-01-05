/*
 * DatabaseConnect Class
 * Manages SQLite database for a campus navigation system.
 * Provides methods for inserting, removing, and retrieving data,
 * calculating routes, and performing database operations.
 */

package DatabaseConnect;

import Dijkstra.Dijkstra;
import Graph.Graph;
import HashMap.HashMap;
import java.sql.*;
import LinkedList.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import Tool.StringSplitter;


public class DatabaseConnect
{
    private Connection conn = null;

    public DatabaseConnect()
    {
        try
        {
            // Load the SQLite driver
            Class.forName("org.sqlite.JDBC");

            // Specify the database to connect to
            conn = DriverManager.getConnection("jdbc:sqlite:test.db");

            // Important as I want control of when data is written
            conn.setAutoCommit(false);
        } catch (Exception e)
        {
            // Handle any exceptions during database connection setup
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    public void close() 
    {
        try
        {
            conn.close();
        } 
        catch (SQLException ex)
        {
            Logger.getLogger(DatabaseConnect.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Method to insert a connection between two places into the database
    public void insertEdge(int startNode, int endNode, int distance)
    {
        try {
            if(distance<=0)
            {
                throw new IllegalArgumentException("The Distance You Have Input Is Less Than Or Equal To 0. You Can Only Input Integer Values Greater Than 0");
            }

            Statement stmt = conn.createStatement();

            String sql = "SELECT Name FROM Place WHERE PlaceID = " + startNode + ";";
            ResultSet rs = stmt.executeQuery(sql);

            if(rs.next())
            {
                sql = "SELECT Name FROM Place WHERE PlaceID = " + endNode + ";";
                rs = stmt.executeQuery(sql);
                if(rs.next())
                {
                    // SQL query to insert an edge into the "Edge" table
                    sql = "INSERT INTO Edge (StartPlaceID, EndPlaceID, BaseDistance, Busyness) VALUES (" + startNode + ", " + endNode + ", " + distance + ", " + distance + ");";
                }else
                {
                    System.out.println("The Places Don't Exist In The Database");
                }
            }
            else{
                System.out.println("The Places Don't Exist In The Database");
            }

            stmt.executeUpdate(sql);
            conn.commit();
            stmt.close();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    // Method to remove a connection between two places into the database
    public void removeEdge(int startNode, int endNode)
    {
        try {
            Statement stmt = conn.createStatement();

            // SQL query to delete an edge from the "Edge" table
            String sql = "DELETE FROM Edge WHERE StartPlaceID = " + startNode + " AND EndPlaceID = " + endNode + ";";

            stmt.executeUpdate(sql);
            conn.commit();
            stmt.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Method to add a building to the database
    public void addBuilding(String name, String description)
    {
        try {
            Statement stmt = conn.createStatement();

            // SQL query to insert a building into the "Place" table
            String sql = "INSERT INTO Place (Name, Description) VALUES (" + "\"" + name +  "\" " + ", \"" + description + "\");";

            stmt.executeUpdate(sql);
            conn.commit();
            stmt.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Method to remove a building from the database
    public void removeBuilding(int buildingID)
    {
        try {
            Statement stmt = conn.createStatement();

            // SQL query to delete a building from the "Place" table
            String sql = "DELETE FROM Place WHERE PlaceID = " + buildingID + ";";

            stmt.executeUpdate(sql);

            sql = "DELETE FROM Edge Where StartPlaceID = " + buildingID + " OR EndPlaceID = " + buildingID + ";";

            stmt.executeUpdate(sql);
            conn.commit();
            stmt.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Method to return a list of buildings from the database
    public HashMap<Integer, String> getListOfBuildings()
    {
        HashMap<Integer, String> buildings;
        try {
            Statement stmt = conn.createStatement();

            // Execute query to get the count of buildings
            ResultSet count = stmt.executeQuery("SELECT COUNT(PlaceID) FROM Place;");

            // Initialize HashMap with an initial capacity based on the count of buildings
            buildings = new HashMap<Integer, String>(count.getInt("COUNT(PlaceID)"));

            // Execute query to get PlaceID and Name of each building
            ResultSet rs = stmt.executeQuery("SELECT PlaceID, Name, Description FROM Place;");

            // Iterate through the result set and populate the HashMap with PlaceID and Name
            while(rs.next())
            {
                System.out.println(rs.getInt("PlaceID") + " - " + rs.getString("Name") + " - " + rs.getString("Description"));
                buildings.add(rs.getInt("PlaceID"), rs.getString("Name"));
            }

            return buildings;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Method to add a student to the database
    public void addStudent(String name, int homeID, String password)
    {
        try {
            Statement stmt = conn.createStatement();

            // SQL query to insert a student into the "Student" table
            String sql = "INSERT INTO Student (StudentName, HomePlaceID, Password) VALUES (" + "\"" + name +  "\" " + ", " + homeID + ", " + "\"" + password + "\"" + ");";

            stmt.executeUpdate(sql);
            conn.commit();
            stmt.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Method to remove a student from the database
    public void removeStudent(String name, String password)
    {
        try {
            Statement stmt = conn.createStatement();

            // SQL query to delete a student from the "Student" table
            String sql = "DELETE FROM Student WHERE StudentName = \"" + name + "\" AND Password = \"" +  password + "\"; ";

            stmt.executeUpdate(sql);
            conn.commit();
            stmt.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Method to view a list of students from the database
    public void getListOfStudents()
    {
        try {
            Statement stmt = conn.createStatement();

            // Execute query to get StudentID and StudentName of each student
            ResultSet rs = stmt.executeQuery("SELECT StudentID, StudentName FROM Student;");

            // Iterate through the result set and print StudentID and StudentName
            while(rs.next())
            {
                System.out.println(rs.getString("StudentID") + " - " + rs.getString("StudentName"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Method to add an event to the database
    public void addEvent(String startTime, String endTime, int placeID, String eventName)
    {
        try {
            Statement stmt = conn.createStatement();

            // SQL query to insert an event into the "Event" table
            String sql = "INSERT INTO Event (StartTime, EndTime, PlaceID, EventName) VALUES (\""+startTime+"\", \"" + endTime + "\", " + placeID + ", \"" + eventName + "\");";

            stmt.executeUpdate(sql);
            conn.commit();
            stmt.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Method to remove an event from the database
    public void removeEvent(int eventID)
    {
        try {
            Statement stmt = conn.createStatement();

            String sql = "DELETE FROM Event WHERE EventID = " + eventID + ";";

            stmt.executeUpdate(sql);
            conn.commit();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addStudentToEvent(int studentID, int eventID)
    {
        try {
            Statement stmt = conn.createStatement();

            // SQL query to delete an event from the "Event" table
            String sql = "INSERT INTO StudentEventLinker (StudentID, EventID) VALUES (" + studentID + ", " + eventID + ");";

            stmt.executeUpdate(sql);
            conn.commit();
            stmt.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Method to remove a student from an event in the database
    public void removeStudentFromEvent(int studentID, int eventID)
    {
        try {
            Statement stmt = conn.createStatement();

            // SQL query to delete a student from an event in the "StudentEventLinker" table
            String sql = "DELETE FROM StudentEventLinker WHERE StudentID = " + studentID + " AND EventID = " +  eventID + "; ";

            stmt.executeUpdate(sql);
            conn.commit();
            stmt.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Method to view a student's timetable for tomorrow
    public void viewStudentTimetable(int studentID){//todo fix so it only shows tomorrows timetable but should also have a function to show all events to be easily modifiable
        try {
            Statement stmt = conn.createStatement();

            // SQL query to retrieve events for a specific student from the "StudentEventLinker" and "Event" tables
            String sql = "SELECT StudentEventLinker.EventID, Event.EventName, Event.StartTime FROM StudentEventLinker, Event WHERE StudentID = " + studentID +
                    " AND Event.EventID = StudentEventLinker.EventID;";
            ResultSet rs = stmt.executeQuery(sql);

            // Iterate through the result set and print EventID, EventName, and StartTime
            while (rs.next()) {
                System.out.println(rs.getString("EventID") + " - " + rs.getString("EventName") + " - " + rs.getString("StartTime"));
            }
        }
        catch(SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    // Method to delete all routes from the database
    public void deleteRoutes()
    {
        try {
            Statement stmt = conn.createStatement();

            // SQL query to delete all records from the "StudentRouteLinker" table
            String sql = "DELETE FROM StudentRouteLinker;";
            stmt.executeUpdate(sql);

            // SQL query to delete all records from the "Routes" table
            sql = "DELETE FROM Routes;";

            stmt.executeUpdate(sql);
            conn.commit();
            stmt.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Method to calculate routing for students and update route-related tables
    public void calculateRouting(int busynessFactor)
    {
        try {//TODO MOST IMPORTANT IS TO REMOVE DATA FOR EVENTS THAT HAPPENED ALREADY
            Statement stmt = conn.createStatement();

            // Query to get the total number of students
            ResultSet numberOfStudents = stmt.executeQuery("SELECT COUNT(StudentID) FROM Student;");
            int studentCount = numberOfStudents.getInt(1);

            LinkedList<Integer> listOfEvents;
            LinkedList<Integer> currentTimetable;
            int edgeID;
            LinkedList<Integer> route;
            int routeNumber = 0;
            int studentEventLinkerID;
            String sql;

            // Loop through each student
            for(int i = 1; i<=studentCount; i++)
            {
                System.out.println("student: " + i);
                listOfEvents = getStudentTimetable(i);
                currentTimetable = getListOfPlacesOnTimetable(i);

                // Loop through each event in the timetable
                for(int j = 0; j<currentTimetable.length()-1; j++)
                {
                    System.out.println("Timetable event: " + j);
                    routeNumber++;

                    // Check if not the last event in the timetable
                    if(j<currentTimetable.length()-2) {
                        // Get StudentEventLinkerID and insert into StudentRouteLinker
                        sql = "SELECT StudentEventLinkerID FROM StudentEventLinker WHERE StudentID =" + i + " AND EventID = " + listOfEvents.get(j) + ";";
                        studentEventLinkerID = stmt.executeQuery(sql).getInt(1);
                        sql = "INSERT INTO StudentRouteLinker (StudentEventLinkerID, RouteNumber) VALUES (" + studentEventLinkerID + ", " + routeNumber + ");";
                        stmt.executeUpdate(sql);
                        conn.commit();
                    }

                    // Calculate route using Dijkstra's algorithm
                    route = StringSplitter.splitStringToList(Dijkstra.Dijkstra(mapToGraph(), currentTimetable.get(j).getValue(), currentTimetable.get(j+1).getValue()));

                    // Loop through each step in the calculated route
                    for(int k = 0; k < route.length()-1; k++)
                    {
                        System.out.println("Step: " + k);

                        // Get EdgeID for the current step
                        sql = "SELECT EdgeID\n" +
                                "FROM Edge\n" +
                                "WHERE (StartPlaceID = " + route.get(k)+ " AND EndPlaceID = "+ route.get(k+1) + ")\n" +
                                "   OR (StartPlaceID = " + route.get(k+1)+ " AND EndPlaceID = "+ route.get(k) + ");";
                        edgeID = stmt.executeQuery(sql).getInt(1);

                        // Insert route information into Routes table
                        sql = "INSERT INTO Routes (EdgeID, StepNumber, RouteNumber)\n" +
                                "VALUES (" + edgeID + ", " + k + ", " + routeNumber + ");";
                        stmt.executeUpdate(sql);

                        // Update Busyness of the Edge
                        sql = "UPDATE Edge SET Busyness = Busyness + " + busynessFactor + " WHERE EdgeID = " + edgeID + ";";
                        stmt.executeUpdate(sql);

                        conn.commit();

                    }

                }
            }

        }
        catch(SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    // Method to check if a student with the given name and password exists in the database
    public boolean isStudent(String name, String password)
    {
        boolean doesStudentExist = false;

        try
        {
            Statement stmt = conn.createStatement();

            // Execute query to retrieve StudentName and Password from the "Student" table
            ResultSet rs = stmt.executeQuery("SELECT StudentName, Password FROM Student WHERE Password = \""  + password + "\" AND StudentName = \"" + name +  "\";");
            rs.getString("StudentName");

            // If the student exists, no error will be thrown and the method will return true
            doesStudentExist = true;

        } catch (SQLException e) {
            System.out.println(e);
        }
        return doesStudentExist;
    }

    // Method to retrieve a list of events from the database
    public void getListOfEvents()
    {
        try {
            Statement stmt = conn.createStatement();

            // Execute query to get EventID and EventName of each event
            ResultSet rs = stmt.executeQuery("SELECT EventID, EventName FROM Event;");

            // Iterate through the result set and print EventID and EventName
            while(rs.next())
            {
                System.out.println(rs.getString("EventID") + " - " + rs.getString("EventName"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Method to reset the busyness of edges in the database to their base distances
    public void resetBusyness()
    {
        try {
            Statement stmt = conn.createStatement();

            // SQL query to update the busyness of edges to their base distances
            stmt.executeUpdate("UPDATE Edge set Busyness = BaseDistance");

            conn.commit();
            stmt.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Method to get the StudentID for a given student name from the database
    public int getStudentID(String name)
    {
        try {
            Statement stmt = conn.createStatement();
            // Execute query to retrieve StudentID for the specified student name
            ResultSet rs = stmt.executeQuery("SELECT StudentID FROM Student WHERE StudentName = \"" + name + "\";");

            // Return the StudentID if a record is found
            return rs.getInt(1);
        } catch(SQLException e)
        {
            System.out.println(e);
        }

        // Return 0 if no record is found or an exception occurs
        return 0;
    }

    // Method to view the shortest routes for a student's events
    public void viewShortestRoutes(int studentID)
    {
        try{
            Statement stmt = conn.createStatement();

            // Query to get the home place name for the given student
            String sql = "SELECT Place.Name FROM Place, Student WHERE StudentID = " + studentID + " AND PlaceID = HomePlaceID;";
            String home = stmt.executeQuery(sql).getString(1);
            LinkedList<String> route = new LinkedList<String>();

            // Query to retrieve the shortest routes for the student's events
            sql = "SELECT\n" +
                    "    SE.EventID,\n" +
                    "    E.EdgeID,\n" +
                    "    P1.Name AS StartPlace,\n" +
                    "    P2.Name AS EndPlace\n" +
                    "FROM\n" +
                    "    StudentEventLinker AS SE\n" +
                    "JOIN\n" +
                    "    StudentRouteLinker AS SRL ON SE.StudentEventLinkerID = SRL.StudentEventLinkerID\n" +
                    "JOIN\n" +
                    "    Routes AS R ON SRL.RouteNumber = R.RouteNumber\n" +
                    "JOIN\n" +
                    "    Edge AS E ON R.EdgeID = E.EdgeID\n" +
                    "JOIN\n" +
                    "    Place AS P1 ON E.StartPlaceID = P1.PlaceID\n" +
                    "JOIN\n" +
                    "    Place AS P2 ON E.EndPlaceID = P2.PlaceID\n" +
                    "JOIN\n" +
                    "    Event AS Ev ON SE.EventID = Ev.EventID\n" +
                    "WHERE\n" +
                    "    SE.StudentID = " + studentID + "\n" +
                    "ORDER BY\n" +
                    "    Ev.StartTime, R.StepNumber;";
            ResultSet shortestRoutes = stmt.executeQuery(sql);

            String current = home;

            // Loop through the result set to construct the route
            while(shortestRoutes.next())
            {
                route.append(current);
                String placeOne = shortestRoutes.getString(3);
                String placeTwo = shortestRoutes.getString(4);

                if(placeOne.equals(current))
                {
                    current = placeTwo;
                }else if(placeTwo.equals(current)){
                    current = placeOne;
                }
            }

            route.append(current);
            System.out.println(route);

        }catch(SQLException e)
        {
            System.out.println(e);
        }
    }

    // Method to retrieve a student's timetable events for a specific date
    public LinkedList<Integer> getStudentTimetable(int studentID) //todo add data for tomorrow to test or just set it instead of now to a particular date allowing for testing
    {
        LinkedList<Integer> timetable = new LinkedList<Integer>();
        try {
            //todo check sql bc i used a join
            Statement stmt = conn.createStatement();

            // SQL query to retrieve EventID for a student's timetable events
            String sql = "SELECT StudentEventLinker.EventID\n" +
                    "FROM StudentEventLinker\n" +
                    "JOIN Event ON Event.EventID = StudentEventLinker.EventID\n" +
                    "WHERE StudentID = " + studentID + "\n" +
                    //"  AND DATE(Event.StartTime) = DATE('now', '+1 day')\n" +
                    "ORDER BY Event.StartTime ASC;";
            ResultSet rs = stmt.executeQuery(sql);

            // Iterate through the result set and add EventID to the timetable
            while (rs.next()) {
                timetable.append(rs.getInt(1));
            }
        }
        catch(SQLException e)
        {
            System.out.println(e);
        }

        return timetable;
    }

    // Method to retrieve a list of places (HomePlaceID and Event.PlaceID) on a student's timetable for a specific date (TODO: Enable date filtering for testing)
    public LinkedList<Integer> getListOfPlacesOnTimetable(int studentID) //todo add data for tomorrow to test or just set it instead of now to a particular date allowing for testing
    {
        LinkedList<Integer> timetable = new LinkedList<Integer>();
        try {
            Statement stmt = conn.createStatement();

            // Query to get the HomePlaceID for the given student
            String homeIDQuery = "SELECT HomePlaceID FROM Student WHERE StudentID = " + studentID + ";";
            ResultSet homeIDResult = stmt.executeQuery(homeIDQuery);

            // Move the cursor to the first row of the result set
            if (homeIDResult.next()) {
                int homeID = Integer.parseInt(homeIDResult.getString("HomePlaceID"));

                // Append the HomePlaceID to the timetable
                timetable.append(homeID);

                // Query to get PlaceID for events on the student's timetable
                String eventPlaceIDQuery = "SELECT Event.PlaceID FROM StudentEventLinker " +
                        "JOIN Event ON Event.EventID = StudentEventLinker.EventID " +
                        "WHERE StudentID = " + studentID + " " +
                        "ORDER BY Event.StartTime ASC;";

                ResultSet eventPlaceIDResult = stmt.executeQuery(eventPlaceIDQuery);

                // Iterate through the result set and append PlaceID to the timetable
                while (eventPlaceIDResult.next()) {
                    timetable.append(Integer.parseInt(eventPlaceIDResult.getString("PlaceID")));
                }

                // Add the home place ID again to complete the timetable
                timetable.append(homeID);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return timetable;
    }

    // Method to map the database edges to a graph
    public Graph<Integer, Integer> mapToGraph()
    {
        Graph<Integer, Integer> graph;
        try {
            Statement stmt = conn.createStatement();

            // Query to get the count of edges in the database
            ResultSet count = stmt.executeQuery("SELECT COUNT(EdgeID) FROM Edge;");

            // Initialize the graph with the count of edges and allow parallel edges
            graph = new Graph<Integer, Integer>(count.getInt("COUNT(EdgeID)"), false);

            // Query to retrieve StartPlaceID, EndPlaceID, and Busyness for each edge
            ResultSet rs = stmt.executeQuery("SELECT StartPlaceID, EndPlaceID, Busyness FROM Edge;");

            // Iterate through the result set and add edges to the graph
            while(rs.next())
            {
                graph.add(rs.getInt("StartPlaceID"), rs.getInt("EndPlaceID"), rs.getInt("Busyness"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return graph;
    }
}
