/*
 * StringSplitter class for splitting a comma-separated string of integers into a linked list of integers
 */

package Tool;

import LinkedList.LinkedList;

public class StringSplitter {

    // Method to split a comma-separated string into a linked list of integers
    public static LinkedList<Integer> splitStringToList(String input) {
        LinkedList<Integer> result = new LinkedList<Integer>();

        // Check if the input string is null or empty
        if (input == null || input.isEmpty()) {
            return result;
        }

        // Split the input string by commas and convert each part to an integer
        String[] numbers = input.split(",");
        for (String number : numbers) {
            try {
                // Parse the number and add it to the linked list
                int parsedNumber = Integer.parseInt(number.trim());
                result.append(parsedNumber);
            } catch (NumberFormatException e) {
                // Handle the case where a non-integer value is encountered
                System.err.println("Invalid number format: " + number);
            }
        }

        return result;
    }
}