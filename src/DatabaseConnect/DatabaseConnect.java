/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DatabaseConnect;

/**
 *
 * @author water
 */
//import java.sql.*;
import Dijkstra.Dijkstra;
import Graph.Graph;
import HashMap.HashMap;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import LinkedList.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

import Tool.StringSplitter;


/**
 *
 * @author sqlitetutorial.net
 */
public class DatabaseConnect
{
    private Connection conn = null;

    public DatabaseConnect()
    {
        try
        {
            Class.forName("org.sqlite.JDBC");//Specify the SQLite Java driver
            conn = DriverManager.getConnection("jdbc:sqlite:test.db");//Specify the database, since relative in the main project folder
            conn.setAutoCommit(false);// Important as you want control of when data is written
            System.out.println("Opened database successfully");
        } catch (Exception e)
        {
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

    public void insertEdge(int startNode, int endNode, int distance)
    {
        try {
            Statement stmt = conn.createStatement();
            String sql = "INSERT INTO Edge (StartPlaceID, EndPlaceID, BaseDistance, Busyness) VALUES (" + startNode + ", " + endNode + ", " + distance + ", " + distance + ");";
            stmt.executeUpdate(sql);
            conn.commit();
            stmt.close();
        } catch (SQLException ignored) {}
    }

    public void removeEdge(int startNode, int endNode)
    {
        try {
            Statement stmt = conn.createStatement();
            String sql = "DELETE FROM Edge WHERE StartPlaceID = " + startNode + " AND EndPlaceID = " + endNode + ";";
            stmt.executeUpdate(sql);
            conn.commit();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addBuilding(String name, String description)
    {
        try {
            Statement stmt = conn.createStatement();
            String sql = "INSERT INTO Place (Name, Description) VALUES (" + "\"" + name +  "\" " + ", \"" + description + "\");";
            stmt.executeUpdate(sql);
            conn.commit();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeBuilding(int buildingID)
    {
        try {
            Statement stmt = conn.createStatement();
            String sql = "DELETE FROM Place WHERE PlaceID = " + buildingID + ";";
            stmt.executeUpdate(sql);
            conn.commit();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public HashMap<Integer, String> getListOfBuildings()
    {
        HashMap<Integer, String> buildings;
        try {
            Statement stmt = conn.createStatement();
            ResultSet count = stmt.executeQuery("SELECT COUNT(PlaceID) FROM Place;");
            buildings = new HashMap<Integer, String>(count.getInt("COUNT(PlaceID)"));
            ResultSet rs = stmt.executeQuery("SELECT PlaceID, Name FROM Place;");
            while(rs.next())
            {
                System.out.println(rs.getInt("PlaceID") + " - " + rs.getString("Name"));
                buildings.add(rs.getInt("PlaceID"), rs.getString("Name"));
            }
            return buildings;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addStudent(String name, int homeID, String password)
    {
        try {
            Statement stmt = conn.createStatement();
            String sql = "INSERT INTO Student (StudentName, HomePlaceID, Password) VALUES (" + "\"" + name +  "\" " + ", " + homeID + ", " + "\"" + password + "\"" + ");";
            stmt.executeUpdate(sql);
            conn.commit();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeStudent(String name, String password)
    {
        try {
            Statement stmt = conn.createStatement();
            String sql = "DELETE FROM Student WHERE StudentName = \"" + name + "\" AND Password = \"" +  password + "\"; ";
            stmt.executeUpdate(sql);
            conn.commit();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void getListOfStudents()
    {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT StudentID, StudentName FROM Student;");
            while(rs.next())
            {
                System.out.println(rs.getString("StudentID") + " - " + rs.getString("StudentName"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addEvent(String startTime, String endTime, int placeID, String eventName)
    {
        try {
            Statement stmt = conn.createStatement();
            String sql = "INSERT INTO Event (StartTime, EndTime, PlaceID, EventName) VALUES (\""+startTime+"\", \"" + endTime + "\", " + placeID + ", \"" + eventName + "\");";
            stmt.executeUpdate(sql);
            conn.commit();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

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
            String sql = "INSERT INTO StudentEventLinker (StudentID, EventID) VALUES (" + studentID + ", " + eventID + ");";
            stmt.executeUpdate(sql);
            conn.commit();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeStudentFromEvent(int studentID, int eventID)
    {
        try {
            Statement stmt = conn.createStatement();
            String sql = "DELETE FROM StudentEventLinker WHERE StudentID = " + studentID + " AND EventID = " +  eventID + "; ";
            stmt.executeUpdate(sql);
            conn.commit();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void viewStudentTimetable(int studentID){//todo fix so it only shows tommorows timetable but should also have a function to show all events to be easily modifiable
        //System.out.println(getListOfPlacesOnTimetable(studentID));
        try {
            Statement stmt = conn.createStatement();
            String sql = "SELECT StudentEventLinker.EventID, Event.EventName, Event.StartTime FROM StudentEventLinker, Event WHERE StudentID = " + studentID +
                    " AND Event.EventID = StudentEventLinker.EventID;";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                System.out.println(rs.getString("EventID") + " - " + rs.getString("EventName") + " - " + rs.getString("StartTime"));
            }
        }
        catch(SQLException e)
        {
        }
    }

    public void deleteRoutes()
    {
        try {
            Statement stmt = conn.createStatement();
            String sql = "DELETE FROM StudentRouteLinker;";
            stmt.executeUpdate(sql);
            sql = "DELETE FROM Routes;";
            stmt.executeUpdate(sql);
            conn.commit();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void calculateRouting()
    {
        long startTime = System.nanoTime();
        try {//TODO MOST IMPORTANT IS TO REMOVE DATA FOR EVENTS THAT HAPPENED ALREADY
            Statement stmt = conn.createStatement();
            ResultSet numberOfStudents = stmt.executeQuery("SELECT COUNT(StudentID) FROM Student;");
            int studentCount = numberOfStudents.getInt(1);

            LinkedList<Integer> listOfEvents;
            LinkedList<Integer> currentTimetable;
            int edgeID;
            String sql;
            LinkedList<Integer> route;
            int routeNumber = 0;

            //Graph<Integer, Integer> g = mapToGraph();


            int studentEventLinkerID;
            for(int i = 1; i<=studentCount; i++)
            {
                System.out.println("student: " + i);
                listOfEvents = getStudentTimetable(i);
                currentTimetable = getListOfPlacesOnTimetable(i);
                for(int j = 0; j<currentTimetable.length()-1; j++)
                {
                    System.out.println("Timetable event: " + j);
                    routeNumber++;
                    if(j<currentTimetable.length()-2) {
                        sql = "SELECT StudentEventLinkerID FROM StudentEventLinker WHERE StudentID =" + i + " AND EventID = " + listOfEvents.get(j) + ";";
                        studentEventLinkerID = stmt.executeQuery(sql).getInt(1);
                        sql = "INSERT INTO StudentRouteLinker (StudentEventLinkerID, RouteNumber) VALUES (" + studentEventLinkerID + ", " + routeNumber + ");";
                        stmt.executeUpdate(sql);
                        conn.commit();
                    }

                    route = StringSplitter.splitStringToList(Dijkstra.Dijkstra(mapToGraph(), currentTimetable.get(j).getValue(), currentTimetable.get(j+1).getValue()));
                    for(int k = 0; k < route.length()-1; k++)
                    {
                        System.out.println("Step: " + k);
                        sql = "SELECT EdgeID\n" +
                                "FROM Edge\n" +
                                "WHERE (StartPlaceID = " + route.get(k)+ " AND EndPlaceID = "+ route.get(k+1) + ")\n" +
                                "   OR (StartPlaceID = " + route.get(k+1)+ " AND EndPlaceID = "+ route.get(k) + ");";
                        edgeID = stmt.executeQuery(sql).getInt(1);
                        sql = "INSERT INTO Routes (EdgeID, StepNumber, RouteNumber)\n" +
                                "VALUES (" + edgeID + ", " + k + ", " + routeNumber + ");";
                        stmt.executeUpdate(sql);
                        conn.commit();
                    }

                }
            }

        }
        catch(SQLException e)
        {
            System.out.println(e);
        }

        long endTime = System.nanoTime();
        System.out.println((endTime-startTime)/1000000);
    }

    public boolean isStudent(String name, String password)
    {
        boolean doesStudentExist = false;

        try
        {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT StudentName, Password FROM Student WHERE Password = \""  + password + "\" AND StudentName = \"" + name +  "\";");
            rs.getString("StudentName");
            doesStudentExist = true;

        } catch (SQLException e)
        {
        }
        return doesStudentExist;
    }

    public void getListOfEvents()
    {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT EventID, EventName FROM Event;");
            while(rs.next())
            {
                System.out.println(rs.getString("EventID") + " - " + rs.getString("EventName"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

//    public void resetBusyness()
//    {
//        try {
//            Statement stmt = conn.createStatement();
//            stmt.executeUpdate("UPDATE Edge set Busyness = BaseDistance");
//            conn.commit();
//            stmt.close();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }

    public int getStudentID(String name)
    {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT StudentID FROM Student WHERE StudentName = \"" + name + "\";");
            return rs.getInt(1);
        }catch(SQLException e){
        }
        return 0;
    }

    public void viewShortestRoutes(int studentID)
    {
        int studentEventLinkerID;
        try{
            Statement stmt = conn.createStatement();
            String sql = "SELECT Place.Name FROM Place, Student WHERE StudentID = " + studentID + " AND PlaceID = HomePlaceID;";
            String home = stmt.executeQuery(sql).getString(1);
            LinkedList<String> route = new LinkedList<String>();

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
            ResultSet rs_1 = stmt.executeQuery(sql); //TODO think of better name than rs_1

            String current = home;

            while(rs_1.next())
            {
                route.append(current);
                String placeOne = rs_1.getString(3);
                String placeTwo = rs_1.getString(4);

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

    public LinkedList<Integer> getStudentTimetable(int studentID) //todo add data for tommorow to test or just set it instead of now to a particular date allowing for testing
    {
        LinkedList<Integer> timetable = new LinkedList<Integer>();
        try {
            //todo check sql bc i used a join
            Statement stmt = conn.createStatement();
            String sql = "SELECT StudentEventLinker.EventID\n" +
                    "FROM StudentEventLinker\n" +
                    "JOIN Event ON Event.EventID = StudentEventLinker.EventID\n" +
                    "WHERE StudentID = " + studentID + "\n" +
                    //"  AND DATE(Event.StartTime) = DATE('now', '+1 day')\n" +
                    "ORDER BY Event.StartTime ASC;";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                timetable.append(rs.getInt(1));
            }
        }
        catch(SQLException e)
        {
        }
        return timetable;
    }

    public LinkedList<Integer> getListOfPlacesOnTimetable(int studentID) //todo add data for tommorow to test or just set it instead of now to a particular date allowing for testing
    {
        LinkedList<Integer> timetable = new LinkedList<Integer>();
        try {
            Statement stmt = conn.createStatement();
            String homeIDQuery = "SELECT HomePlaceID FROM Student WHERE StudentID = " + studentID + ";";
            ResultSet homeIDResult = stmt.executeQuery(homeIDQuery);

            // Move the cursor to the first row of the result set
            if (homeIDResult.next()) {
                int homeID = Integer.parseInt(homeIDResult.getString("HomePlaceID"));
                timetable.append(homeID);

                String eventPlaceIDQuery = "SELECT Event.PlaceID FROM StudentEventLinker " +
                        "JOIN Event ON Event.EventID = StudentEventLinker.EventID " +
                        "WHERE StudentID = " + studentID + " " +
                        "ORDER BY Event.StartTime ASC;";

                ResultSet eventPlaceIDResult = stmt.executeQuery(eventPlaceIDQuery);

                while (eventPlaceIDResult.next()) {
                    timetable.append(Integer.parseInt(eventPlaceIDResult.getString("PlaceID")));
                }

                // Add the home place ID again
                timetable.append(homeID);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return timetable;
    }

    public Graph<Integer, Integer> mapToGraph()
    {
        Graph<Integer, Integer> graph;
        try {
            Statement stmt = conn.createStatement();
            ResultSet count = stmt.executeQuery("SELECT COUNT(EdgeID) FROM Edge;");
            graph = new Graph<Integer, Integer>(count.getInt("COUNT(EdgeID)"), false);
            ResultSet rs = stmt.executeQuery("SELECT StartPlaceID, EndPlaceID, Busyness FROM Edge;");
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
