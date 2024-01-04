/*
 * Main Class
 * The main entry point of the campus navigation system.
 */

import DatabaseConnect.DatabaseConnect;

import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter Your Full Name [With No Spaces]: ");
        String name = input.nextLine();
        System.out.print("Enter Your Password: ");
        String password = input.nextLine();

        DatabaseConnect connection = new DatabaseConnect();
        if(connection.isStudent(name, password))
        {
            connection.close();
            System.out.println("Login Successful");
            if(Objects.equals(name, "a"))
            {
                new AdminInterface();
            }
            else{
                new StudentInterface(name);
            }
        }
        else{
            System.out.println("Login Unsuccessful (Contact An Admin For Help)");
        }
    }
}
